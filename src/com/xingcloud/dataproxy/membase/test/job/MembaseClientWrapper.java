package com.xingcloud.dataproxy.membase.test.job;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.xingcloud.dataproxy.membase.test.MembaseTest;
import com.xingcloud.dataproxy.membase.test.config.BucketConfig;
import com.xingcloud.dataproxy.membase.test.config.ConfigException;
import com.xingcloud.dataproxy.membase.test.config.MembaseTestConfig;
import com.xingcloud.dataproxy.membase.test.config.PoolConfig;
import com.xingcloud.dataproxy.membase.test.config.SocketConfig;

public class MembaseClientWrapper {
	
	private static MembaseClientWrapper singleton = null;
	
	private SockIOPool membasePool;
	private MembaseTestConfig membaseConfig;
	
	private MembaseClientWrapper() throws ConfigException, IllegalArgumentException{
		membasePool = SockIOPool.getInstance();
		membaseConfig = MembaseTestConfig.getInstance();
		init(MembaseTest.bucketName);
	}
	
	private void initBucket(MembaseTestConfig config, String bucketName) throws IllegalArgumentException{
		BucketConfig bucketConfig = config.getBucketConfig(bucketName);
		if(bucketConfig != null){
			membasePool.setServers(bucketConfig.getNodesArray());
			membasePool.setWeights(bucketConfig.getNodesWeigthArray());
		}
		else{
			throw new IllegalArgumentException("no such bucket "+bucketName);
		}
	}
	
	private void initPool(MembaseTestConfig config){
		PoolConfig poolConfig = config.getPoolConfig();
		membasePool.setInitConn(poolConfig.getInitConnect());
		membasePool.setMinConn(poolConfig.getMinConnect());
		membasePool.setMaxConn(poolConfig.getMaxConnect());
		membasePool.setMaxIdle(poolConfig.getIdleTime());
		membasePool.setMaintSleep(poolConfig.getMaintSleep());
	}
	
	private void initSocket(MembaseTestConfig config){
		SocketConfig socketConfig = config.getSocketConfig();
		membasePool.setSocketTO((int) socketConfig.getTimeout());
		membasePool.setSocketConnectTO((int) socketConfig.getConnectTimeout());
	}
	
	private void init(String bucketName) throws IllegalArgumentException{
		initBucket(membaseConfig, bucketName);
		initPool(membaseConfig);
		initSocket(membaseConfig);
		membasePool.initialize();
	}
	
	public static MembaseClientWrapper getInstance() throws ConfigException{
		if(singleton == null){
			singleton = new MembaseClientWrapper();
		}
		return singleton;
	}
	
	public MemCachedClient getMembaseClient(){
		MemCachedClient membaseClient = new MemCachedClient();
		return membaseClient;
	}
}
