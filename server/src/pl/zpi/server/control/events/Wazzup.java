package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class Wazzup extends Event{
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		//precheck
		Element resp = doc.createElement("user");
		return null;
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
