package com.xingcloud.dataproxy.membase.test.config;

import org.w3c.dom.Node;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class ClientConfig {
	private boolean compress;
	private long compressThresold;
	
	public ClientConfig(){
		this.compress = DefaultConfig.DEFAULT_CLIENT_COMPRESS;
		this.compressThresold = DefaultConfig.DEFAULT_CLIENT_COMPRESS_THRESOLD;
	}
	
	public ClientConfig(boolean compress, long compressThresold){
		this.compress = compress;
		this.compressThresold = compressThresold;
	}
	
	public ClientConfig(Node xmlNode) throws ConfigException{
		this();
		parseCompress(xmlNode);
		parseCompressThresold(xmlNode);
	}
	
	public boolean isCompress() {
		return compress;
	}

	public long getCompressThresold() {
		return compressThresold;
	}

	private void parseCompress(Node xmlNode) throws ConfigException{
		String compress = XMLUtil.getNodeAttribute(xmlNode, "compress");
		if(compress != null){
			if(compress.equals("1")){
				this.compress = true;
			}
			else if(compress.equals("0")){
				this.compress = false;
			}
		}
	}
	
	private void parseCompressThresold(Node xmlNode) throws ConfigException{
		String compressThresold = XMLUtil.getNodeAttribute(xmlNode, "compress_thresold");
		if(compressThresold != null){
			try{
				this.compressThresold = Long.parseLong(compressThresold);
			} catch(NumberFormatException e){
				throw new ConfigException("client compress_thresold value must be a number");
			}
		}
	}
}
