package com.xingcloud.dataproxy.membase.test.config;

import org.w3c.dom.Node;

import com.xingcloud.dataproxy.membase.test.util.XMLUtil;

public class PoolConfig {
	private int initConnect;
	private int minConnect;
	private int maxConnect;
	private long idleTime;
	private long maintSleep;
	
	public PoolConfig(){
		this.initConnect = DefaultConfig.DEFUALT_POOL_INIT_CONN;
		this.minConnect = DefaultConfig.DEFAULT_POOL_MIN_CONN;
		this.maxConnect = DefaultConfig.DEFAULT_POOL_MAX_CONN;
		this.idleTime = DefaultConfig.DEFAULT_POOL_IDLE_TIME;
		this.maintSleep = DefaultConfig.DEFAULT_POOL_MAINT_SLEEP;
	}
	
	public PoolConfig(int initConnect, int minConnect, int maxConnect, long idleTime, long maintSleep){
		this.initConnect = initConnect;
		this.minConnect = minConnect;
		this.maxConnect = maxConnect;
		this.idleTime = idleTime;
		this.maintSleep = maintSleep;
	}
	
	public PoolConfig(Node xmlNode) throws ConfigException{
		this();
		parseInitConnect(xmlNode);
		parseMinConnect(xmlNode);
		parseMaxConnect(xmlNode);
		parseIdleTime(xmlNode);
		parseMaintSleep(xmlNode);
	}

	public int getInitConnect() {
		return initConnect;
	}

	public int getMinConnect() {
		return minConnect;
	}

	public int getMaxConnect() {
		return maxConnect;
	}

	public long getIdleTime() {
		return idleTime;
	}

	public long getMaintSleep() {
		return maintSleep;
	}
	
	private void parseInitConnect(Node xmlNode) throws ConfigException{
		String initConnect = XMLUtil.getNodeAttribute(xmlNode, "init_conn");
		if(initConnect != null){
			try{
				this.initConnect = Integer.parseInt(initConnect);
			} catch(NumberFormatException e){
				throw new ConfigException("pool init_conn value must be a number");
			}
		}
	}
	
	private void parseMinConnect(Node xmlNode) throws ConfigException{
		String minConnect = XMLUtil.getNodeAttribute(xmlNode, "min_conn");
		if(minConnect != null){
			try{
				this.minConnect = Integer.parseInt(minConnect);
			} catch(NumberFormatException e){
				throw new ConfigException("pool min_conn value must be a number");
			}
		}
	}
	
	private void parseMaxConnect(Node xmlNode) throws ConfigException{
		String maxConnect = XMLUtil.getNodeAttribute(xmlNode, "max_conn");
		if(maxConnect != null){
			try{
				this.maxConnect = Integer.parseInt(maxConnect);
			} catch(NumberFormatException e){
				throw new ConfigException("pool max_conn value must be a number");
			}
		}
	}
	
	private void parseIdleTime(Node xmlNode) throws ConfigException{
		String idleTime = XMLUtil.getNodeAttribute(xmlNode, "idle_time");
		if(idleTime != null){
			try{
				this.idleTime = Long.parseLong(idleTime);
			} catch(NumberFormatException e){
				throw new ConfigException("pool idle_time value must be a number");
			}
		}
	}
	
	private void parseMaintSleep(Node xmlNode) throws ConfigException{
		String maintSleep = XMLUtil.getNodeAttribute(xmlNode, "maint_sleep");
		if(maintSleep != null){
			try{
				this.maintSleep = Long.parseLong(maintSleep);
			} catch(NumberFormatException e){
				throw new ConfigException("pool maint_sleep value must be a number");
			}
		}
	}
}
