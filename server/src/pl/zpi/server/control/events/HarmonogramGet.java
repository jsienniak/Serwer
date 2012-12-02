package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.utils.XMLToolkit;

public class HarmonogramGet extends Event {

	// action=harmonogram.set&dni=1234&g_start=12:00&g_stop=2:00&m_id=2&p=2&w_start=2&w_stop=3&active=0
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		try {
			DBHarmonogramy har = new DBHarmonogramy();
			Node root = createDefaultResponse(doc, "result", "message", "OK", "message", "");
			root.appendChild(XMLToolkit.packVector(doc, har.executeQuery(), "list"));
			return root;
		//	return createDefaultResponse(doc, "result", "status", "OK", "message", String.valueOf(har.getId()));
		} catch (NumberFormatException ex) {
			return createDefaultResponse(doc, "result", "status", "err", "message", "Invalid number exception");
		}

		// return createDefaultResponse(doc,"harm","value",""+db.add(new
		// Harmonogram(start,end,days,module,port,value_start,value_end)));
	}
	
	@Override
	public String getName() {
		return "harmonogram.get"; 
	}
}
