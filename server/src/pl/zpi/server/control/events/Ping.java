package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;

public class Ping extends Event{
	
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		Node r = doc.createElement("ping");
		r.appendChild(createTextNode(doc, "status", "success"));
		r.appendChild(createTextNode(doc, "message","PONG"));
		return r;
	}

	@Override
	public String getName() {
		return "module.ping";
	}

}
