package pl.zpi.server.utils;

import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.zpi.server.db.DatabaseObjImpl;

@SuppressWarnings("rawtypes")

public class XMLToolkit {
	public static Element createTextNode(Document d, String nodeName, String textContent) {
		Element n = d.createElement(nodeName);
		n.setTextContent(textContent);
		return n;
	}

	public static Element packVector(Document doc, Vector v, String rootName) {
		Element root = doc.createElement(rootName);

		Iterator<DatabaseObjImpl> it = v.iterator();
		while (it.hasNext()) {
			
			DatabaseObjImpl o = it.next();
			Iterator it2 = o.data.keySet().iterator();
			Element row = doc.createElement("row");
			while (it2.hasNext()) {
				
				String key = (String) it2.next();
				row.appendChild(createTextNode(doc, key, o.get(key)));
			}
			root.appendChild(row);
		}

		return root;
	}
	public static Node createDefaultResponse(Document doc, String nodeName, String... str){
		Element root = doc.createElement(nodeName);
		String key = "";
		int i = 0;
		for(String s : str){
			if(i % 2 == 0){
				key = s;
			}else{
				root.appendChild(createTextNode(doc, key, s));
			}
			i++;
		}
		return root;
	
	}
}
