package pl.zpi.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import pl.zpi.server._trash.ITokenResolverClassLoader;
import pl.zpi.server._trash.TokenReplacingReader;
import pl.zpi.server.db.DBUsers;

// Extend HttpServlet class
public class ServTest extends HttpServlet {

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

		message = "Hello World";
		appPath = getServletContext().getRealPath("/");
		try {
			Class.forName("org.postgresql.Driver").getClass();
		} catch (ClassNotFoundException e) {
			message = "No driver";
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
	
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("Test servera");
		
		FileInputStream fin = new FileInputStream(new File(appPath+"test.xml"));
		TokenReplacingReader trr = new TokenReplacingReader(new InputStreamReader(fin), new ITokenResolverClassLoader());
		/*String s = fin.read

		PipedOutputStream pout = new PipedOutputStream();
		pout.write();*/
		DBUsers obj = new DBUsers();
		
		obj.set("login", "nowy");
		obj.write();
		// Actual logic goes here.
		// Use the static TransformerFactory.newInstance() method to instantiate
		// a TransformerFactory. The javax.xml.transform.TransformerFactory
		// system property setting determines the actual class to instantiate --
		// org.apache.xalan.transformer.TransformerImpl.
		TransformerFactory tFactory = TransformerFactory.newInstance();

		// Use the TransformerFactory to instantiate a Transformer that will
		// work with
		// the stylesheet you specify. This method call also processes the
		// stylesheet
		// into a compiled Templates object.
		Transformer transformer = null;
		
		try {
			transformer = tFactory.newTransformer(new StreamSource(appPath+"test.xsl"));
		} catch (TransformerConfigurationException e1) {
			out.println(Arrays.toString(e1.getStackTrace()));
			e1.printStackTrace();
		}

		// Use the Transformer to apply the associated Templates object to an
		// XML document
		// (foo.xml) and write the output to a file (foo.out).
		try {
			if (transformer != null) {
				transformer.transform(new StreamSource(trr), new StreamResult(out));
			}
		} catch (TransformerException e) {
			out.println(Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}

	/*	System.out.println("************* The result is in birds.out *************");
		DatabaseObject da = new DatabaseObject();
		Vector v = da.getObjects();
		Iterator it = v.iterator();
		while (it.hasNext()) {
			out.println(it.next().toString() + "<br />");
		}*/

	}

	@Override
	public void destroy() {
		// do nothing.
	}
}