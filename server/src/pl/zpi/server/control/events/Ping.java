package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import javax.servlet.http.HttpServletRequest;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBDevices;
import pl.zpi.server.db.DatabaseObj;
import pl.zpi.server.db.DatabaseObjImpl;
import pl.zpi.server.utils.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Ping extends Event{
	
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
        DBDevices dev = new DBDevices();
        Vector<DatabaseObj> devices = dev.executeQuery();
        List<String> ID  = new ArrayList<String>();
        for(DatabaseObj ob: devices){
            ob.read();
            ID.add(ob.get("reg_id"));
            System.out.println(ob.get("reg_id"));
        }
        Sender sender = new Sender(Config.getConf().get("GCM_DEV_KEY"));
        Message message = new Message.Builder().addData("event", "ALARM").build();
        String id = "APA91bFw1nQZxq3DYGG1Vhwmz0ryR8UUUoR7GwttSEa9AHE_HzhARZWdgxwT9xxBM3TCusm0vpEKic0KcAb7urTYMfIpkmZ5Sx6M7L-nIuZQTDrzaRJkmhPZurXMT-aPAXfvwDdG6kiB";
        try {

            MulticastResult r = sender.send(message,ID,3);
            System.out.println(r.toString());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Node r = doc.createElement("ping");
		r.appendChild(createTextNode(doc, "status", "success"));
		r.appendChild(createTextNode(doc, "message","PONG"));
		return r;
	}

	@Override
	public String getName() {
		return "m.ping";
	}

}
