package com.xingcloud.dataproxy.membase.test.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
	public static String getNodeAttribute(Node node, String name){
		if(node.hasAttributes()){
			Node attrNode = node.getAttributes().getNamedItem(name);
			if(attrNode != null)return attrNode.getNodeValue();
		}
		return null;
	}
	
	public static String getChildNodeText(String tagName, Element parentNode){
		NodeList nodeList = parentNode.getElementsByTagName(tagName);
		if(nodeList.getLength() > 0){
			Node node = nodeList.item(0);
			return getNodeText(node);
		}
		return null;
	}
	
	public static String getNodeText(Node node){
		if(node != null){
			NodeList textNodeList = node.getChildNodes();
			if(textNodeList.getLength() > 0){
				Node firstTextNode = textNodeList.item(0);
				if(firstTextNode.getNodeType() == Node.TEXT_NODE) 
					return firstTextNode.getNodeValue();
			}
		}
		return null;
	}
	
	public static Node getChildNode(Node parent, String nodeName){
		NodeList childNodeList = parent.getChildNodes();
		for(int i=0; i<childNodeList.getLength(); i++){
			Node node = childNodeList.item(i);
			if(node.getNodeName().equalsIgnoreCase(nodeName)){
				return node;
			}
		}
		return null;
	}
	
	public static NodeList getNodesByXPath(String path, Node parentNode) throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		return (NodeList)xpath.evaluate(path, parentNode, XPathConstants.NODESET);
	}
}
