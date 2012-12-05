package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBProfiles;

public class ProfileUse extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {

			String name = request.getParameter("name");
			DBProfiles har = new DBProfiles();
			Vector v = har.executeQuery(DBProfiles.name + "='"+name+"'");
			if(v.size() == 0){
				return createDefaultResponse(doc, "result", "status", "err", "message", "No such profile ("+name+")");
			}
			DBProfiles prof = (DBProfiles) v.get(0);
			//TODO ustawianie na sterowniku
			return createDefaultResponse(doc, "result", "status", "OK", "message", "");

	}
	@Override
	public String getName() {
		return "profile.use";
	}

}
