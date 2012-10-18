package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.*;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class AddDBUsers extends Event {
	//name, email, add_date, groups, last_login, active
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		//check attributes
		if(name == null || email == null){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Request parameters missing");
		}
		
		String ee = request.getParameter("");
		DBUsers da = new DBUsers();
		da.set("name",  name);
		da.set("email", email);
		da.set("active" ,"1");
		//FIXME konwersja dat
		da.set("create_date", new Date().toString());
		
		if(da.write()){
			return createDefaultResponse(doc, "user", "status" , "OK" , "message", "success", "assigned_id", String.valueOf(da.getId()));
		}else{
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
		}
	}

	@Override
	public String getName() {
		return "user.add";
	}
}
