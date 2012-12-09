package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;
import pl.zpi.server.utils.XMLToolkit;

public class UsersEdit extends Event {
	//name, email, add_date, groups, last_login, active
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		List <DBUsers>usersList = new LinkedList<DBUsers>();
		Enumeration e =request.getParameterNames();
		while(e.hasMoreElements()){
			String parameterName = e.nextElement().toString();
			if(parameterName.contains("id_")){
				int start = parameterName.indexOf("_");
				String elementId = parameterName.substring(start + 1);
				
				DBUsers u = new DBUsers(Integer.valueOf(request.getParameter(parameterName)));
				u.read();
				
				//tworzymy nowe access rights
				StringBuilder sb = new StringBuilder("*");
				
				for(int i = 1; i < 11; i++){
					String attr = request.getParameter(elementId+"_"+i);
					if(attr != null){
						sb.append("*"+i);
					}
				}
				sb.append("*");
				
				u.set(DBUsers.ACCESS_RIGHTS, sb.toString());
				u.write();
				
			}
		}
		return XMLToolkit.createTextNode(doc, "test", "test");
	}

	@Override
	public String getName() {
		return "user.edit";
	}
}
