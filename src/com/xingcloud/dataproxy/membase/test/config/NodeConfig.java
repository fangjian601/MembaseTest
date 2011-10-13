package com.xingcloud.dataproxy.membase.test.config;

import org.w3c.dom.Node;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class NodeConfig {
	private String host;
	private int port;
	private int weight;
	
	
	public NodeConfig(){
		this.host = "";
		this.port = 0;
		this.weight = DefaultConfig.DEFAULT_NODE_WEIGHT;
	}
	
	public NodeConfig(String host, int port, int weight){
		this.host = host;
		this.port = port;
		this.weight = weight;
	}
	
	public NodeConfig(Node xmlNode) throws ConfigException{
		this();
		parseHost(xmlNode);
		parsePort(xmlNode);
		parseWeight(xmlNode);
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getWeight() {
		return weight;
	}

	public String toString(){
		return host+":"+port;
	}
	
	private void parseHost(Node xmlNode) throws ConfigException{
		String host = XMLUtil.getNodeAttribute(xmlNode, "host");
		if(host != null){
			this.host = host;
		}
		else {
			throw new ConfigException("node host field cannot be empty!");
		}
	}
	
	private void parsePort(Node xmlNode) throws ConfigException{
		String port = XMLUtil.getNodeAttribute(xmlNode, "port");
		if(port != null){
			try{
				this.port = Integer.parseInt(port);
			}catch(NumberFormatException e){
				throw new ConfigException("port value must be a number!");
			}
		}
		else{
			throw new ConfigException("node port field cannot be empty!");
		}
	}
	
	private void parseWeight(Node xmlNode) throws ConfigException{
		String weight = XMLUtil.getNodeAttribute(xmlNode, "weight");
		if(weight != null){
			try{
				this.weight = Integer.parseInt(weight);
			}catch(NumberFormatException e){
				throw new ConfigException("weight value must be a number!");
			}
		}
	}
}
