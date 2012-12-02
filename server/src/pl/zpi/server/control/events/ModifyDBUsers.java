package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class ModifyDBUsers extends Event {
	//name, email, add_date, groups, last_login, active
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		//precheck
		Element resp = doc.createElement("user");
		int id_user = -1;
		try{
			id_user = Integer.parseInt(request.getParameter("id_user"));
		}
		catch(NumberFormatException ex){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Wrong user id");
		}
		DBUsers dbu = new DBUsers();
		Vector v = dbu.executeQuery("id_user = "+id_user);
		if(v == null){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Wrong user id");
		}
		dbu = (DBUsers) v.iterator().next();
	

		String name = request.getParameter("name");
		if(name != null){
			dbu.set("name", name);
		}
		//check attributes
		/*if(name == null || email == null){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Request parameters missing");
		}
		
		String ee = request.getAttribute("");
		*/

		if(dbu.write()){
			return createDefaultResponse(doc, "user", "status" , "OK" , "message", "Success");
		}else{
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
		}

	}

	@Override
	public String getName() {
		return "user.modify";
	}
	
}
