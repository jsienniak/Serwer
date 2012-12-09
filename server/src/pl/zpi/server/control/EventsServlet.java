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
import javax.servlet.http.HttpSession;
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
import com.google.api.client.http.HttpRequest;

import pl.zpi.server._trash.DummyModule;
import pl.zpi.server.control.events.*;
import pl.zpi.server.control.modules.*;
import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.db.DBUsers;
import pl.zpi.server.db.DatabaseObjImpl;
import pl.zpi.server.utils.Config;
import pl.zpi.server.utils.XMLToolkit;

/**
 * Servlet pozwalający na komunikacje telefonu z serwerem Wyniki wywołań akcji
 * przedstawiane są za pomocą XML'a
 * 
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
	 * 
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
		//Proszę nie zmieniać tej kolejności. Ważne!!!
		Module m = new WodaModule(); // 0
		mg.put(m);
		ms.put(m);
		m = new RoletaModule(); // 1
		mg.put(m);
		ms.put(m);
		m = new BramaModule(); // 2
		mg.put(m);
		ms.put(m);
		m = new AlarmModule(); // 3
		mg.put(m);
		ms.put(m);
		m = new OgrodModule(); // 4
		mg.put(m);
		ms.put(m);
		m = new ModbusModule(); // 5
		mg.put(m);
		ms.put(m);
		m = new DummyModule();
		mg.put(m);
		ms.put(m);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				AlarmModule alarm = new AlarmModule();
				System.out.println("Załadowano.");
				boolean al = false;
				while (true) {
					if (alarm.getValue(2) > 0) {
						if (!al) {
							al = true;
							System.out.println("ALARM!!");
							// Tutaj obsluga alarmu!!;
						}
					} else {
						al = false;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace(); // To change body of catch
												// statement use File | Settings
												// | File Templates.
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

			root.appendChild(createTextNode(response, "status", "ERR"));
			root.appendChild(createTextNode(response, "message", "No action parameter defined"));

		} else {

			// not logged
			if (!"user.login".equals(action) && getLoggedUser(req) < 0) {
				root.appendChild(XMLToolkit.createDefaultResponse(response, "result", "status", "NOT_LOGGED", "message", "User not logged"));
			} else {
				EventManager em = EventManager.getInstance();
				Event e = em.getByName(action);

				if (e == null) {
					root.appendChild(createTextNode(response, "status", "ERR"));
					root.appendChild(createTextNode(response, "message", "No such event (" + action + ")"));

				} else {
					// sprawdzamy dostep
					boolean denied = false;
					if (action.contains("module")) {
						HttpSession session = req.getSession();
						if (action.contains("get")) {
							denied = isDenied(session.getAttribute("user"), req.getParameter("id"), false);
							if (denied) {
								root.appendChild(XMLToolkit.createDefaultResponse(response, "result", "status", "FORBIDDEN", "message", "Brak uprawnień do czytania modułu "));
							}
						} else {//SET
							denied = isDenied(session.getAttribute("user"), req.getParameter("id"), true);
							if (denied) {
								root.appendChild(XMLToolkit.createDefaultResponse(response, "result", "status", "FORBIDDEN", "message", "Brak uprawnień do ustawienia modułu "));
							}
						}
					}
					if (!denied) {
						root.appendChild(e.processEvent(response, req));
						if (req.getParameter("mode") != null && "web".equals(req.getParameter("mode"))) {
							HttpSession session = req.getSession();
							if (session != null && session.getAttribute("user") != null) {
								resp.sendRedirect("/access.html");
							} else {
								resp.sendRedirect("/login.html");
							}
						}
					}
				}
			}
		}
		root.appendChild(createTextNode(response, "Total_time", String.valueOf(System.currentTimeMillis() - start) + " ms"));
		XML2Writer(response, resp.getWriter());
	}

	private boolean isDenied(Object attribute, String parameter, boolean write) {
		return isDenied((DatabaseObjImpl) attribute, Integer.valueOf(parameter), write);

	}

	public int getLoggedUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session == null) {
			return -1;
		}
		DatabaseObjImpl userId = (DatabaseObjImpl) session.getAttribute("user");
		if (userId == null) {
			return -1;
		}

		if (!userId.read()) {
			return -1;
		}

		return userId.getId();

	}

	/**
	 * Pomocnicza klasa wysyłająca dokument XML do writera
	 * 
	 * @param doc
	 *            dokument, który chcemy wysłać
	 * @param out
	 *            Writer, do którego chcemy pisać
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

	public boolean authenticateClient() {
		// GoogleIdToken git = new Google
		// GoogleIdTokenVerifier gitv = new GoogleIdTokenVerifier(null, null);
		// gitv.verify(asdfsfdsdfsdfdsfadfsafdsa);
		return true;
	}

	public boolean isDenied(DatabaseObjImpl user, int moduleCode, boolean write) {
		user.read();
		String access = user.getStringNotNull(DBUsers.ACCESS_RIGHTS);
		int permNumber = write ? moduleCode * 2 : moduleCode  * 2 + 1;
		return access.contains("*" + permNumber + "*");
	}
}
