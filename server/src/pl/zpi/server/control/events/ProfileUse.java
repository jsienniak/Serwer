package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.modules.OgrodModule;
import pl.zpi.server.control.modules.RoletaModule;
import pl.zpi.server.control.modules.WodaModule;
import pl.zpi.server.db.DBProfiles;
import pl.zpi.server.modbus.Comm;

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
            (new WodaModule()).setValue(0,prof.get(DBProfiles.value1));
            (new RoletaModule()).setValue(0,prof.get(DBProfiles.value2));
            (new OgrodModule()).setValue(0, prof.get(DBProfiles.value3));
			return createDefaultResponse(doc, "result", "status", "OK", "message", "");

	}
	@Override
	public String getName() {
		return "profile.use";
	}

}
