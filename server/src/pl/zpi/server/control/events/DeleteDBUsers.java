package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class DeleteDBUsers extends Event {
	//name, email, add_date, groups, last_login, active
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String id = request.getParameter("id");

		//check attributes
		if(id == null){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Request parameters missing");
		}
		
		String ee = request.getParameter("");
		DBUsers da = new DBUsers(Integer.parseInt(id));

		
		if(da.delete()){
			return createDefaultResponse(doc, "user", "status" , "OK" , "message", "success", "assigned_id", String.valueOf(da.getId()));
		}else{
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
		}
	}

	@Override
	public String getName() {
		return "user.delete";
	}
}
