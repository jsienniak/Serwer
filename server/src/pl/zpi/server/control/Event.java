package pl.zpi.server.control;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class Event {
	public abstract Node processEvent(Document doc, HttpServletRequest request);
	public abstract String getName();
}
