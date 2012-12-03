package pl.zpi.server.control.events;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.Scheduler;
import pl.zpi.server.utils.XMLToolkit;

public class SchedulerStart  extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		Scheduler sch = new Scheduler();
		return XMLToolkit.createDefaultResponse(doc, "result", "status", "ERR", "message", "Not yet implemented");
	}

	@Override
	public String getName() {
		return "scheduler.start";
	}
}
