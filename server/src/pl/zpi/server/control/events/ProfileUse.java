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
import pl.zpi.server.db.DatabaseObj;
import pl.zpi.server.db.DatabaseObjImpl;
import pl.zpi.server.modbus.Comm;

public class ProfileUse extends Event {

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {

			String id = request.getParameter("id");
			DBProfiles har = new DBProfiles();
			Vector v = har.executeQuery("id_profile" + "='"+id+"'");
			if(v.size() == 0){
				return createDefaultResponse(doc, "result", "status", "ERR", "message", "No such profile ("+id+")");
			}
            if(!(v.get(0) instanceof DBProfiles)){
                System.out.println("nidyrydy");
            }
			DatabaseObj prof = (DatabaseObj) v.get(0);
            try {
            (new WodaModule()).setValue(0,prof.get(DBProfiles.value1));
            (new RoletaModule()).setValue(0,prof.get(DBProfiles.value2));
            (new OgrodModule()).setValue(0, prof.get(DBProfiles.value3));
            } catch(Exception e){
                e.printStackTrace();
                return createDefaultResponse(doc, "result", "status", "ERR", "message", "No PLC controller");
            }
			return createDefaultResponse(doc, "result", "status", "OK", "message", "");

	}
	@Override
	public String getName() {
		return "profile.use";
	}

}
