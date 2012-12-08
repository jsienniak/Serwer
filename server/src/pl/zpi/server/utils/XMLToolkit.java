package pl.zpi.server.utils;

import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.zpi.server.db.DatabaseObjImpl;

@SuppressWarnings("rawtypes")
/**
 * Klasa pomocnicza. Zawiera metody przydatne przy przetwarzaniu dokumentów XML
 * @author mm
 *
 */
public class XMLToolkit {
	/**
	 * Tworzy węzeł tekstowy XML o podanej nazwie 
	 * @param d dokument XML
	 * @param nodeName nazwa węzła
	 * @param textContent treść umieszczana w węźle
	 * @return
	 */
	public static Element createTextNode(Document d, String nodeName, String textContent) {
		Element n = d.createElement(nodeName);
		n.setTextContent(textContent);
		return n;
	}
	/**
	 * Tworzy drzewo XML z podanego wektora obiektów DatabaseObjImpl
	 * @param doc dokument XML
	 * @param v wektor obiektów
	 * @param rootName nazwa korzenia drzewa
	 * @return drzewo XML
	 */
	public static Element packVector(Document doc, Vector v, String rootName) {
		Element root = doc.createElement(rootName);

		Iterator<DatabaseObjImpl> it = v.iterator();
		while (it.hasNext()) {
			
			DatabaseObjImpl o = it.next();
			Iterator it2 = o.data.keySet().iterator();
			Element row = doc.createElement(o.tableName);
			while (it2.hasNext()) {
				String key = (String) it2.next();
				row.appendChild(createTextNode(doc, key, o.get(key)));
			}
			root.setAttribute("count", String.valueOf(v.size()));
			root.appendChild(row);
		}

		return root;
	}
	/**
	 * Klasa tworząca domyślną odpowiedź XML. 
	 * @param doc dokument XML
	 * @param nodeName nazwa korzenia drzewa odpowiedzi
	 * @param str kolejne elementy odpowiedzi. Zapisywane są w konwencji: nazwa węzła - wartość...
	 * @return drzewo XML zawierające opakowane elementy
	 */
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
