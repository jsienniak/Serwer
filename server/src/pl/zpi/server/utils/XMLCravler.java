package pl.zpi.server.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		Element e = d.createElement("eee");
		p.appendChild(e);
	}

}
