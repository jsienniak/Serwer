package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBProfiles;
import pl.zpi.server.utils.XMLToolkit;

public class ProfileGet extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		try { 
			DBProfiles har = new DBProfiles();
			Node root = createDefaultResponse(doc, "result", "Status", "OK", "message", "");
			root.appendChild(XMLToolkit.packVector(doc, har.executeQuery(" user_id ="+getLoggedUserId(request)), "list"));
			return root;
		//	return createDefaultResponse(doc, "result", "status", "OK", "message", String.valueOf(har.getId()));
		} catch (NumberFormatException ex) {
			return createDefaultResponse(doc, "result", "status", "err", "message", "Invalid number exception");
		}
	}
	@Override
	public String getName() {
		return "profile.get";
	}

}
