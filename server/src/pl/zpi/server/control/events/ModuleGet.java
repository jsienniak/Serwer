package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.Module;

public class ModuleGet extends Event {
	public boolean fullCheck = true;
	public List<Module> modules;

	public ModuleGet() {
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

			String mID = request.getParameter("id");
			String pNum = request.getParameter("port_num");
			if (mID == null || pNum == null) {
				return createDefaultResponse(doc, "module", "status", "ERR", "message", "parameter missing");
			}
			int moduleID = Integer.parseInt(mID);
			int portNum = Integer.parseInt(pNum);
			try{
				int result = modules.get(moduleID).getValue(portNum);
				return createDefaultResponse(doc, "module", "status", "OK", "message", "Value get","value",String.valueOf(result));
			}
			catch(ArrayIndexOutOfBoundsException ex){
				return createDefaultResponse(doc, "module", "status", "ERR", "message", "No such port number ("+ex.getMessage()+")");
			}
		
			
		} catch (Exception ex) {
			return createDefaultResponse(doc, "module", "status", "ERR", "message", "Not a number or so");
		}
	}

	@Override
	public String getName() {
		return "module.get";
	}
}
