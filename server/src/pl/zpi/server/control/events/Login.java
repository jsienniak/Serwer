package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.google.api.client.http.HttpTransport;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;
import pl.zpi.server.db.DatabaseObjImpl;
import pl.zpi.server.utils.XMLToolkit;

public class Login extends Event{
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String mode = request.getParameter("mode");
		if(mode != null && "web".equals(mode)){
			String login = request.getParameter("login");
			String pass = request.getParameter("haslo");
			DBUsers u = new DBUsers();
			Vector v = u.executeQuery("email ='"+login+"' AND password='"+pass+"'");
			if(v.size() > 0){
				HttpSession session = request.getSession(true);
			
				session.setAttribute("user", v.firstElement());
				session.setAttribute("user_id", ((DatabaseObjImpl)v.firstElement()).getId());
				
			}
			return XMLToolkit.createTextNode(doc, "status", "should redirect");
		}else{
		String token = "{ \"token\":\""+request.getParameter("token")+"}";
            String token2 = request.getParameter("token");
		System.out.println("Token is: "+token);
		//DBUsers users = new DBUsers(Integer.valueOf(request.getParameter("id")));
		
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		//sprawdzamy poprawność tokenu
		GoogleIdTokenVerifier gitv = new GoogleIdTokenVerifier(transport, jsonFactory);

            GoogleIdToken result = null;
            try {
                result = gitv.verify(token);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            //niepoprawny
	if(result == null){
		return createDefaultResponse(doc, "result", "status", "FORBIDEN","message", "Verification failed");
    }
		HttpSession session = request.getSession(true);	
		DBUsers user = new DBUsers(Integer.valueOf(request.getParameter("id")));
		
		if(!user.read()){
			return createDefaultResponse(doc, "result", "status", "ERR","message", "Database read error");
		}
		
		session.setAttribute("user", user);
		session.setAttribute("user_id", user.getId());
		

		return createDefaultResponse(doc, "result", "status", "OK","message", ""+user.getId());
	//	if(dbu.write()){
	//		return createDefaultResponse(doc, "user", "status" , "OK" , "message", "Success");
	//	}else{
	//		return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
	//	}
		}
	}

	@Override
	public String getName() {
		return "user.login";
	}
}
