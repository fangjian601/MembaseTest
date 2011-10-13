package com.xingcloud.dataproxy.membase.test.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class MembaseTestConfig {
	
	private static final String CONFIG_PATH="membase.xml";
	
	private static MembaseTestConfig singleton = null;
	
	private List<BucketConfig> buckets;
	private PoolConfig pool;
	private SocketConfig socket;
	private ClientConfig client;
	
	private MembaseTestConfig() throws ConfigException{
		Document doc = getDocument();
		parseBuckets(doc);
		parsePool(doc);
		parseSocket(doc);
		parseClient(doc);
	}
	
	private void parseBuckets(Document doc) throws ConfigException{
		try {
			NodeList buckets = XMLUtil.getNodesByXPath("/membase/buckets/bucket", doc);
			if(buckets == null || buckets.getLength() <= 0){
				throw new ConfigException("no bucket configuration!");
			}
			this.buckets = new ArrayList<BucketConfig>();
			for(int i=0; i<buckets.getLength(); i++){
				this.buckets.add(new BucketConfig(buckets.item(i)));
			}
		} catch (XPathExpressionException e) {
			throw new ConfigException(e.getMessage());
		}
	}
	
	private void parsePool(Document doc) throws ConfigException{
		try{
			NodeList pools = XMLUtil.getNodesByXPath("/membase/pool", doc);
			if(pools.getLength() > 0){
				this.pool = new PoolConfig(pools.item(0));
			}
		} catch (XPathExpressionException e) {
			throw new ConfigException(e.getMessage());
		}
	}
	
	private void parseSocket(Document doc) throws ConfigException{
		try{
			NodeList sockets = XMLUtil.getNodesByXPath("/membase/socket", doc);
			if(sockets.getLength() > 0){
				this.socket = new SocketConfig(sockets.item(0));
			}
		} catch (XPathExpressionException e) {
			throw new ConfigException(e.getMessage());
		}
	}
	
	private void parseClient(Document doc) throws ConfigException{
		try{
			NodeList clients = XMLUtil.getNodesByXPath("/membase/client", doc);
			if(clients.getLength() > 0){
				this.client = new ClientConfig(clients.item(0));
			}
		} catch (XPathExpressionException e) {
			throw new ConfigException(e.getMessage());
		}
	}
	
	private Document getDocument() throws ConfigException{
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream configStream = MembaseTestConfig.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
			return dBuilder.parse(configStream);
		} catch(Exception e){
			throw new ConfigException(e.getMessage());
		}
	}
	
	public static MembaseTestConfig getInstance() throws ConfigException{
		if(singleton == null){
			singleton = new MembaseTestConfig();
		}
		return singleton;
	}
	
	public BucketConfig getBucketConfig(String name){
		for(int i=0; i<buckets.size(); i++){
			BucketConfig bucketConfig = buckets.get(i);
			if(bucketConfig.getBucketName().equalsIgnoreCase(name)){
				return bucketConfig;
			}
		}
		return null;
	}

	public PoolConfig getPoolConfig() {
		return pool;
	}

	public SocketConfig getSocketConfig() {
		return socket;
	}

	public ClientConfig getClientConfig() {
		return client;
	}
	
	
}
