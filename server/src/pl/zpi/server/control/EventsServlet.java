package pl.zpi.server.control;

import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.extensions.java6.auth.oauth2.GooglePromptReceiver;
import com.google.api.client.googleapis.services.GoogleClient;

import pl.zpi.server.control.events.*;
import pl.zpi.server.control.modules.*;
import pl.zpi.server.utils.Config;

/**
 * Servlet pozwalający na komunikacje telefonu z serwerem
 * Wyniki wywołań akcji przedstawiane są za pomocą XML'a
 * @author mm
 * 
 */
public class EventsServlet extends HttpServlet {

	private static final long serialVersionUID = -7910478707995128960L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * Pomocnicza klasa tworząca nowy dokument XML
	 * @return nowy dokumen XML
	 */
	private Document createDocument() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return docBuilder == null ? null : docBuilder.newDocument();

	}
	/**
	 * Inicjalizacja servletu. Tutaj ładowane są wszystkie moduły oraz zdarzenia
	 */
	@Override
	public void init() throws ServletException {
		Config.getConf().setWorkingDir(getServletContext().getRealPath("/"));
		EventManager evm = EventManager.getInstance();
		EventManager.autoloadEvents();
		// moduly
		ModuleGet mg = new ModuleGet();
		ModuleSet ms = new ModuleSet();
		Module m = new WodaModule();   //0
		mg.put(m);
		ms.put(m);
		m = new RoletaModule();    //1
		mg.put(m);
		ms.put(m);
		m = new BramaModule();   //2
		mg.put(m);
		ms.put(m);
		m = new AlarmModule();   //3
		mg.put(m);
		ms.put(m);
        m = new OgrodModule();   //4
        mg.put(m);
        ms.put(m);
        m = new ModbusModule();   //5
        mg.put(m);
        ms.put(m);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmModule alarm = new AlarmModule();
                System.out.println("Załadowano.");
                boolean al = false;
                while(true){
                    if(alarm.getValue(2)>0){
                        if(!al){
                            al=true;
                            System.out.println("ALARM!!");
                            //Tutaj obsluga alarmu!!;
                        }
                    } else {
                        al = false;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

            }
        });

        t.start();


        evm.registerEvent(mg);
		evm.registerEvent(ms);
   
		try {
			Class.forName("com.mysql.jdbc.Driver").getClass();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Obsługa zapytań 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		Document response = createDocument();
		Element root = response.createElement("response");
		response.appendChild(root);

		String action = req.getParameter("action");
		if (action == null) {

			root.appendChild(createTextNode(response, "status", "err"));
			root.appendChild(createTextNode(response, "message", "No action parameter defined"));

		} else {

			EventManager em = EventManager.getInstance();
			Event e = em.getByName(action);

			if (e == null) {
				root.appendChild(createTextNode(response, "status", "err"));
				root.appendChild(createTextNode(response, "message", "No such event (" + action + ")"));

			} else {
				root.appendChild(e.processEvent(response, req));
			}

		}
		root.appendChild(createTextNode(response, "Total_time", String.valueOf(System.currentTimeMillis() - start) + " ms"));
		XML2Writer(response, resp.getWriter());
        XML2Writer(response, new PrintWriter(System.out));

    }

	/**
	 * Pomocnicza klasa wysyłająca dokument XML do writera
	 * @param doc dokument, który chcemy wysłać
	 * @param out Writer, do którego chcemy pisać
	 */
	private void XML2Writer(Document doc, PrintWriter out) {
		Transformer transformer = null;

		try {

			transformer = TransformerFactory.newInstance().newTransformer();

		} catch (TransformerFactoryConfigurationError e) {

			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StreamResult result = new StreamResult(out);
		DOMSource source = new DOMSource(doc);

		try {
			if (transformer != null) {
				transformer.transform(source, result);
			}
		} catch (TransformerException e) {

			e.printStackTrace();

		}
	}
	public boolean authenticateClient(){
		//GoogleIdToken git = new Google
		//GoogleIdTokenVerifier gitv = new GoogleIdTokenVerifier(null, null);
		//gitv.verify(asdfsfdsdfsdfdsfadfsafdsa);
		return true;
	}

}
