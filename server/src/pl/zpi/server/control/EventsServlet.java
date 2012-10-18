package pl.zpi.server.control;

import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import java.io.IOException;
import java.io.PrintWriter;

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

import pl.zpi.server.control.events.Ping;
import pl.zpi.server.control.events.PrintDBUsers;

/**
 * Servlet ktory przyjmuje polecenia i zwraca xml
 * 
 * @author mm
 * 
 */
public class EventsServlet extends HttpServlet {

	private static final long serialVersionUID = -7910478707995128960L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// POST czy GET jedna ryba
		doPost(req, resp);
	}

	public Document createDocument() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return docBuilder == null ? null : docBuilder.newDocument();

	}

	@Override
	public void init() throws ServletException {
		EventManager.getInstance().registerEvent(new Ping());
		EventManager.getInstance().registerEvent(new PrintDBUsers());
		try {
			Class.forName("org.postgresql.Driver").getClass();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		Document response = createDocument();
		Element root = response.createElement("response");
		response.appendChild(root);
		PrintWriter out = resp.getWriter();
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
	}

	public void XML2Writer(Document doc, PrintWriter out) {
		Transformer transformer = null;

		try {

			transformer = TransformerFactory.newInstance().newTransformer();

		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {

			e.printStackTrace();
		}

		// initialize StreamResult with File object to save to file
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

}
