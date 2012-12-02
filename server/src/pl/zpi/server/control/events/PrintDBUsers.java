package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.packVector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class PrintDBUsers extends Event {
	
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		DBUsers da = new DBUsers();
		return packVector(doc, da.executeQuery(), "users");
	}

	@Override
	public String getName() {
		return "user.list";
	}
}
