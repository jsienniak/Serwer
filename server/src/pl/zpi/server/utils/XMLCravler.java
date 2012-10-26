package pl.zpi.server.utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.zpi.server.db.DatabaseObj;

public class XMLCravler {
	
	public static void processDocument(Document doc){
		NodeList rootChildren = doc.getChildNodes();
		for(int i = 0; rootChildren.item(i) != null; i++){
			visitNode(rootChildren.item(i));
		}
	}
	
	public static void visitNode(Node n){
		NodeList nl = n.getChildNodes();
		for(int i = 0; nl.item(i) != null; i ++){
			visitNode(nl.item(i));
			processNode(nl.item(i));
		}
	}
	public static void processNode(Node n){
		String nodeName = n.getNodeName();
		if("class".equals(nodeName)){
			insertClass(n);
		}
	/*	System.out.println("###################\nNode name: "+n.getNodeName());
		System.out.println("Node value: "+n.getNodeValue());
		System.out.println("Node content: "+n.getTextContent());
		System.out.println("NODE ATTRS: ");
		NamedNodeMap nnm = n.getAttributes();
		if(nnm != null)
		for(int i = 0 ; nnm.item(i) != null; i ++){
			System.out.println(nnm.item(i).getNodeName());
		}
*/	}
	public static void insertClass(Node n){
		Document d = n.getOwnerDocument();
		Node p = n.getParentNode();
		p.removeChild(n);
		try {
			Class s = Class.forName(n.getTextContent());
			DatabaseObj st = (DatabaseObj) s.newInstance();
			p.appendChild(XMLToolkit.packVector(d, st.executeQuery(), "users"));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	}

}
