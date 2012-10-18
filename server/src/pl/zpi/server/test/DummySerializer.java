package pl.zpi.server.test;

import java.util.Iterator;
import java.util.Vector;

import org.apache.xerces.impl.XML11NamespaceBinder;

import pl.zpi.server.db.DBUsers;


public class DummySerializer {

	public static void main(String args[]){
		System.out.println(getNode());
	}
	public static String getNode(){
		//XML
		String result = "<node>";
		DBUsers da = new DBUsers();
		Vector v = da.executeQuery();
		Iterator<DBUsers> it = v.iterator();
		while(it.hasNext()){
			result += "<row>";
			DBUsers o = it.next();
			Iterator it2 = o.data.keySet().iterator();
			while(it2.hasNext()){
				String key  = (String) it2.next();
				result += "<"+key+">";
				result += o.get(key);
				result +="</"+key+">";
			}
			
			result += "</row>";
		}
		result += "</node>";
		return result;
	}
}
