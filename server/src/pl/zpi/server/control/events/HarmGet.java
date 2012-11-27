package pl.zpi.server.control.events;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.db.Harmonogram;
import static pl.zpi.server.utils.XMLToolkit.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 22.11.12
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
public class HarmGet extends Event {

    DBHarmonogramy db = DBHarmonogramy.getInstance();

    @Override
    public Node processEvent(Document doc, HttpServletRequest request) {
        int key = Integer.parseInt(request.getParameter("id"));
        Harmonogram h = db.get(key);
        return createDefaultResponse(doc,"harm")  ; //TODO: dokonczyc
    }

    @Override
    public String getName() {
        return "harm.get";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
