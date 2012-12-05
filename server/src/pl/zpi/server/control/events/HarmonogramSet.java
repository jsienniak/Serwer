package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.Scheduler;
import pl.zpi.server.db.DBHarmonogramy;

public class HarmonogramSet extends Event { 

	// action=harmonogram.set&dni=1234&g_start=12:00&g_stop=2:00&m_id=2&p=2&w_start=2&w_stop=3&active=0
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		try {
			DBHarmonogramy har = new DBHarmonogramy();
			  
			har.set("g_start", request.getParameter("g_start"));
			har.set("g_stop", request.getParameter("g_stop"));
			har.set("dni", request.getParameter("dni"));
			har.set("m_id",request.getParameter("m_id"));
			har.set("p",request.getParameter("p"));
			har.set("w_start",request.getParameter("w_start"));
			har.set("w_end",request.getParameter("w_end"));
			har.set("active",request.getParameter("active"));
			
			Scheduler.getInstance().checkSchedule(har);
			har.write();
			return createDefaultResponse(doc, "result", "status", "OK", "message", String.valueOf(har.getId()));
		} catch (NumberFormatException ex) {
			return createDefaultResponse(doc, "result", "status", "err", "message", "Invalid number exception");
		}

		// return createDefaultResponse(doc,"harm","value",""+db.add(new
		// Harmonogram(start,end,days,module,port,value_start,value_end)));
	}

	@Override
	public String getName() {
		return "harmonogram.set";
	}
}
