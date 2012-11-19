package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import pl.zpi.server.control.Event;
import pl.zpi.server.control.Module;
import pl.zpi.server.db.DBDevices;
import pl.zpi.server.db.DBUsers;
import pl.zpi.server.utils.Config;

public class GCMRegister extends Event {
	public boolean fullCheck = true;

	public GCMRegister() {

	}

	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String device = request.getParameter("device");
		if (device == null) {
			return createDefaultResponse(doc, "result", "status", "err", "message", "Missing parameter");
		}
		Sender sender = new Sender(Config.getConf().get("GCM_DEV_KEY"));
		Message message = new Message.Builder().build();

		Result result = null;
		try {
			result = sender.send(message, device, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result.getMessageId() != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			if (canonicalRegId != null) {
				DBDevices dev = new DBDevices();
				dev.set("Reg_id", canonicalRegId);
				dev.set("UID", device);
				dev.write();
				return createTextNode(doc, "result", "OK");
			}
		} else {
			String error = result.getErrorCodeName();
			if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				return createDefaultResponse(doc, "result", "status", "ERR", "message", "Device not registered in service");
			}
		}
		return createDefaultResponse(doc, "result", "status", "ERR", "message", "Device not registered in service2");
	}

	@Override
	public String getName() {
		return "device.register";
	}
}