package pl.zpi.server.test;
import java.io.IOException;
import java.io.StringWriter;

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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pl.zpi.server.control.EventManager;
import pl.zpi.server.utils.Config;
import pl.zpi.server.utils.XMLCravler;




public class SQLConnectionTest {
	public static void main(String[] args) throws IOException{
		Config.getConf();
		System.out.println(Config.getConf().get("EVENTS_PATH"));
		EventManager.getInstance().autoloadEvents();
		/*	DatabaseObject dbo;*/
		
	/*	Iterator it =  dbo.getObjects("id_user > 1").iterator();
		while(it.hasNext()){
			System.out.println(((DatabaseObject)it.next()).data);
		}*/
/*		dbo = new DatabaseObject(1);	
		dbo.read();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse("test.xml");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XMLCravler.processDocument(doc);

		
		
		
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
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
		System.out.println(xmlString);
		

		
		try {
			FileInputStream fin = new FileInputStream(new File("webapps/test.xml"));
			TokenReplacingReader trr = new TokenReplacingReader(new InputStreamReader(fin), new ITokenResolverClassLoader());
			BufferedReader st = new BufferedReader(trr);
			String line = st.readLine();
			while(line != null){
				System.out.println(line);
				line = st.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Brak pliku");
			e.printStackTrace();
		}
		
		

		dbo = new DatabaseObject();
		dbo.set("login", "nowy login");

		dbo.set("datetimet", "2012-11-10 23:11:33");
		dbo.set("Liczba", "23");
		dbo.write();
		
			System.out.println(dbo);
		int id = dbo.getId();
		
		dbo.set("login", "gfgf");
		dbo.write();
		System.out.println(dbo.getId());
*/	}
	
	
}
