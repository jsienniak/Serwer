package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;
import static pl.zpi.server.utils.XMLToolkit.createTextNode;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBDevices;
import pl.zpi.server.db.DatabaseObj;
import pl.zpi.server.utils.Config;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMUnregister extends Event {

    public GCMUnregister() {

    }

    @Override
    public Node processEvent(Document doc, HttpServletRequest request) {

        String id = request.getParameter("id");
        DBDevices d = new DBDevices();
        Vector v = d.executeQuery(" reg_id = '" + id + "'");
        if (v.size() == 0) {
            return createDefaultResponse(doc, "result", "status", "ERR", "message", "Device not found");
        }
        DatabaseObj dbo = (DatabaseObj) v.firstElement();
        dbo = new DBDevices(dbo.getInt("id_device"));
        dbo.set("reg_id", "0");
        if (dbo.write()) {
            return createDefaultResponse(doc, "result", "status", "OK", "message", "Unregister complete");
        } else {
            return createDefaultResponse(doc, "result", "status", "ERR", "message", "DB write err");

        }

    }

    @Override
    public String getName() {
        return "device.unregister";
    }
}
