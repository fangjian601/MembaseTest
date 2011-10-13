package com.xingcloud.dataproxy.membase.test.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class BucketConfig {
	private String bucketName;
	private List<NodeConfig> nodes;
	
	public BucketConfig(){
		this.bucketName = "";
		this.nodes = new ArrayList<NodeConfig>();
	}
	
	public BucketConfig(String bucketName, List<NodeConfig> nodes){
		this.bucketName = bucketName;
		this.nodes = new ArrayList<NodeConfig>(nodes);
	}
	
	public BucketConfig(Node xmlNode) throws ConfigException{
		this();
		parseBucketName(xmlNode);
		parseNodes(xmlNode);
	}
	
	public String getBucketName(){
		return bucketName;
	}
	
	public String[] getNodesArray(){
		String[] nodesArray = new String[nodes.size()];
		for(int i=0; i<nodes.size(); i++){
			nodesArray[i] = nodes.get(i).toString();
		}
		return nodesArray;
	}
	
	public Integer[] getNodesWeigthArray(){
		Integer[] nodesWeightArray = new Integer[nodes.size()];
		for(int i=0; i<nodes.size(); i++){
			nodesWeightArray[i] = nodes.get(i).getWeight();
		}
		return nodesWeightArray;
	}
	
	private void parseBucketName(Node xmlNode) throws ConfigException{
		String bucketName = XMLUtil.getNodeAttribute(xmlNode, "name");
		if(bucketName != null){
			this.bucketName = bucketName;
		}
		else{
			throw new ConfigException("bucketName field cannot be empty!");
		}
	}
	
	private void parseNodes(Node xmlNode) throws ConfigException{
		try {
			NodeList childNodes = XMLUtil.getNodesByXPath("nodes/node", xmlNode);
			if(childNodes != null && childNodes.getLength() > 0){
				for(int i=0; i<childNodes.getLength(); i++){
					Node node = childNodes.item(i);
					this.nodes.add(new NodeConfig(node));
				}
			}
		} catch (XPathExpressionException e) {
			throw new ConfigException("XPath error "+e.getMessage());
		}
	}
}
