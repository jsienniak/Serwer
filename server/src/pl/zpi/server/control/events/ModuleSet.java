package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.Module;
import pl.zpi.server.db.DBUsers;

public class ModuleSet extends Event {
	public boolean fullCheck = true;
	public List<Module> modules;

	public ModuleSet() {
		modules = new ArrayList<Module>();
	}

	public void setup(List m) {
		modules = m;
	}

	public void put(Module mod) {
		modules.add(mod);
	}

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		try {
			String value = request.getParameter("value");
			String mID = request.getParameter("id");
			String pNum = request.getParameter("port_num");
			if (value == null || mID == null || pNum == null) {
				return createDefaultResponse(doc, "module", "status", "ERR", "message", "parameter missing");
			}
			int moduleID = Integer.parseInt(mID);
			int portNum = Integer.parseInt(pNum);
			boolean result = modules.get(moduleID).setValue(portNum, Integer.parseInt(value));
			if (result) {
				return createDefaultResponse(doc, "module", "status", "OK", "message", "Value set");
			}
			return createDefaultResponse(doc, "module", "status", "ERR", "message", "Out of range");
		} catch (Exception ex) {
			return createDefaultResponse(doc, "module", "status", "ERR", "message", "Not a number or so "+ex.getMessage());
		}
	}

	@Override
	public String getName() {
		return "module.set";
	}
}
