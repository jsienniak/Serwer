package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBDevices;
import pl.zpi.server.db.DatabaseObj;
import pl.zpi.server.db.DatabaseObjImpl;

public class Ping extends Event{
	
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		Node r = doc.createElement("ping");
		r.appendChild(createTextNode(doc, "status", "success"));
		r.appendChild(createTextNode(doc, "message","PONG"));
		DBDevices dev = new DBDevices();
        Vector<DatabaseObj> devices = dev.executeQuery();
        List<String> ID  = new ArrayList<String>();
        for(DatabaseObj ob: devices){
            ID.add(ob.get("reg_id"));
            System.out.println(ob.getStringNotNull("reg_id"));
        }
		return r;
	}

	@Override
	public String getName() {
		return "m.ping";
	}

}
