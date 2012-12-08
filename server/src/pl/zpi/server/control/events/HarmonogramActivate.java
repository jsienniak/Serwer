package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.db.DBUsers;

public class HarmonogramActivate extends Event {
	//name, email, add_date, groups, last_login, active
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String id = request.getParameter("id");

		//check attributes
		if(id == null){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Request parameters missing");
		}
		
	
		DBHarmonogramy da = new DBHarmonogramy(Integer.parseInt(id));
		if(!da.read()){
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Schedule not found ("+id+")");
		}
		da.set(DBHarmonogramy.active, "1");
		if(da.write()){
			return createDefaultResponse(doc, "user", "status" , "OK" , "message", "success", "assigned_id", "");
		}else{
			return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
		}
	}

	@Override
	public String getName() {
		return "harmonogram.activate";
	}
}
