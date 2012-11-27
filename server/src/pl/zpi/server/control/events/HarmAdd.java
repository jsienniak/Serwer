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
 * Time: 00:11
 * To change this template use File | Settings | File Templates.
 */
public class HarmAdd extends Event {

    DBHarmonogramy  db = DBHarmonogramy.getInstance();

    @Override
    public Node processEvent(Document doc, HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        int days = Integer.parseInt(request.getParameter("days"));
        int module = Integer.parseInt(request.getParameter("module"));
        int port = Integer.parseInt(request.getParameter("port"));
        String value_start = request.getParameter("value_start");
        String value_end = request.getParameter("value_end");

        return createDefaultResponse(doc,"harm","value",""+db.add(new Harmonogram(start,end,days,module,port,value_start,value_end)));
    }

    @Override
    public String getName() {
        return "harm.add";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
