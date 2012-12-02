package pl.zpi.server.control.events;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.utils.XMLToolkit;

public class ProfileSet extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
			return XMLToolkit.createDefaultResponse(doc, "result", "status", "ERR", "message", "Not yet implemented");
	}

	@Override
	public String getName() {
		return "profile.set";
	}

}
