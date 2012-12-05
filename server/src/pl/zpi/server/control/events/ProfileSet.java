package pl.zpi.server.control.events;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBProfiles;
import pl.zpi.server.utils.XMLToolkit;

public class ProfileSet extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String name = request.getParameter("name");
		String v1 = request.getParameter("m1_v");  //woda
		String v2 = request.getParameter("m2_v");  //roleta
		String v3 = request.getParameter("m3_v");  //ogrod
		DBProfiles prof = new DBProfiles();
		prof.set(DBProfiles.name, name);
		prof.set(DBProfiles.value1, v1);
		prof.set(DBProfiles.value2, v2);
		prof.set(DBProfiles.value3, v3);
		if(prof.write()){
			return XMLToolkit.createDefaultResponse(doc, "result", "status", "OK", "message", "");
		}
		return XMLToolkit.createDefaultResponse(doc, "result", "status", "ERR", "message", "Write err");
	}

	@Override
	public String getName() {
		return "profile.set";
	}

}
