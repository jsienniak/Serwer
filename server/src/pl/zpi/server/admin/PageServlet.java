package pl.zpi.server.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pl.zpi.server.db.DBUsers;
import pl.zpi.server.utils.XMLCravler;

public class PageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2904860740383334172L;
	private String message;
	public static String appPath = "";
	DBUsers o;

	@Override
	public void init() throws ServletException {
		// Do required initialization

	
		appPath = getServletContext().getRealPath("/");
		try {
			Class.forName("com.mysql.jdbc.Driver").getClass();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String requestUrl = request.getRequestURL().toString();
		int start = requestUrl.lastIndexOf("/");
		int stop = requestUrl.lastIndexOf(".");
		String docName = requestUrl.substring(start + 1, stop);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		System.out.println("XML file is: "+appPath+docName+".xml");
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(appPath+docName+".xml");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(FileNotFoundException ex){
			PrintWriter pw = response.getWriter();
			pw.print("<h2>404!</h2><h4>Nie odnaleziono pliku: ("+ex.getMessage()+")</h4>");
	
		}
		XMLCravler.processDocument(doc);

		
		String mode = request.getParameter("mode");
		
		Transformer transformer = null;
		try {
			if(mode != null){
				if("xml".equals(mode)){
					transformer = TransformerFactory.newInstance().newTransformer();
				}else{
					transformer = TransformerFactory.newInstance().newTransformer();
				}
			}else{
				transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(appPath+docName+".xsl"));
			}
			
		} catch ( TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String xmlString = result.getWriter().toString();
		out.println(xmlString);
	}

	@Override
	public void destroy() {
		// do nothing.
	}

}
