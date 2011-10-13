package com.xingcloud.dataproxy.membase.test.config;

import org.w3c.dom.Node;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class SocketConfig {
	private long timeout;
	private long connectTimeout;
	
	public SocketConfig(){
		this.timeout = DefaultConfig.DEFAULT_SOCKET_TIMEOUT;
		this.connectTimeout = DefaultConfig.DEFAULT_SOCKET_CONN_TIMEOUT;
	}
	
	public SocketConfig(long timeout, long connectTimeout){
		this.timeout = timeout;
		this.connectTimeout = connectTimeout;
	}
	
	public SocketConfig(Node xmlNode) throws ConfigException{
		this();
		parseTimeout(xmlNode);
		parseConnectTimeout(xmlNode);
	}

	public long getTimeout() {
		return timeout;
	}

	public long getConnectTimeout() {
		return connectTimeout;
	}

	private void parseTimeout(Node xmlNode) throws ConfigException{
		String timeout = XMLUtil.getNodeAttribute(xmlNode, "timeout");
		if(timeout != null){
			try{
				this.timeout = Long.parseLong(timeout);
			} catch(NumberFormatException e){
				throw new ConfigException("socket timeout value must be a number");
			}
		}
	}
	
	private void parseConnectTimeout(Node xmlNode) throws ConfigException{
		String connectTimeout = XMLUtil.getNodeAttribute(xmlNode, "conn_timeout");
		if(connectTimeout != null){
			try{
				this.connectTimeout = Long.parseLong(connectTimeout);
			} catch(NumberFormatException e){
				throw new ConfigException("socket conn_timeout value must be a number");
			}
		}
	}
}
