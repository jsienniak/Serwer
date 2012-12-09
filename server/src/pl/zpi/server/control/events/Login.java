package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionListener;

import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.api.client.googleapis.json.JsonCParser;

import pl.zpi.server.control.Event;

public class Login extends Event{
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		//precheck
		
	//	JSONParser jsp = new JSONParser();
		
		Element resp = doc.createElement("user");
		return createDefaultResponse(doc, "result", "status", "ERR","message", "Not yet implemented");
	//	if(dbu.write()){
	//		return createDefaultResponse(doc, "user", "status" , "OK" , "message", "Success");
	//	}else{
	//		return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
	//	}

	}

	@Override
	public String getName() {
		return "you.wazzup";
	}
}
