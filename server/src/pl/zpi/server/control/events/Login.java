package pl.zpi.server.control.events;

import static pl.zpi.server.utils.XMLToolkit.createDefaultResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import pl.zpi.server.control.Event;
import pl.zpi.server.db.DBUsers;

public class Login extends Event{
	@Override
	public Node processEvent(Document doc, HttpServletRequest request) {
		String token = "{ \"token\":\""+request.getParameter("token")+"}";
		System.out.println("Token is: "+token);
		//DBUsers users = new DBUsers(Integer.valueOf(request.getParameter("id")));
		
		//HttpTransport transport = new NetHttpTransport();
		//JsonFactory jsonFactory = new JacksonFactory();
		
		//sprawdzamy poprawność tokenu
	//	GoogleIdTokenVerifier gitv = new GoogleIdTokenVerifier(transport, jsonFactory);	

	//	GoogleIdToken result = gitv.verify(token);
		
		//niepoprawny
	//	if(result == null){
	//		return createDefaultResponse(doc, "result", "status", "FORBIDEN","message", "Verification failed");
		HttpSession session = request.getSession(true);	
		DBUsers user = new DBUsers(Integer.valueOf(request.getParameter("id")));
		
		if(!user.read()){
			return createDefaultResponse(doc, "result", "status", "ERR","message", "Database read error");
		}
		
		session.setAttribute("user", user);
		session.setAttribute("user_id", user.getId());
		

		return createDefaultResponse(doc, "result", "status", "OK","message", "User logged");
	//	if(dbu.write()){
	//		return createDefaultResponse(doc, "user", "status" , "OK" , "message", "Success");
	//	}else{
	//		return createDefaultResponse(doc, "user", "status" , "ERR" , "message", "Database connection error");
	//	}

	}

	@Override
	public String getName() {
		return "user.login";
	}
}
