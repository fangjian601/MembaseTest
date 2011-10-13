package com.xingcloud.dataproxy.membase.test.job;

import com.danga.MemCached.MemCachedClient;
import com.xingcloud.dataproxy.membase.test.random.RandomGenerator;

public abstract class AbstractJob extends Thread{
	protected MembaseClientWrapper clientWrapper;
	protected RandomGenerator generator;
	protected MemCachedClient membaseClient;
	protected long jobRangeBegin;
	protected long jobRangeEnd;
	protected long jobFinishedCount;
	protected long jobLatency;
	protected long jobMaxLatency;
	protected long jobMinLatency;
	protected boolean finished;
	
	public AbstractJob(MembaseClientWrapper clientWrapper, RandomGenerator generator, 
			long jobRangeBegin, long jobRangeEnd){
		super();
		this.clientWrapper = clientWrapper;
		this.generator = generator;
		this.membaseClient = clientWrapper.getMembaseClient();
		this.jobRangeBegin = jobRangeBegin;
		this.jobRangeEnd = jobRangeEnd;
		this.jobFinishedCount = 0;
		this.jobMinLatency = 0;
		this.jobMaxLatency = 0;
		this.finished = false;
	}
	
	@Override
	public void run(){
		long startTime = System.currentTimeMillis();
		startJob();
		long stopTime = System.currentTimeMillis();
		long period = stopTime - startTime;
		if(jobRangeEnd - jobRangeBegin > 0){
			jobLatency = period/(jobRangeEnd-jobRangeBegin);
		}
		this.finished = true;
	}
	
	public long getJobFinishedCount(){
		return jobFinishedCount;
	}
	
	public long getJobLatency() {
		return jobLatency;
	}

	public long getJobMaxLatency() {
		return jobMaxLatency;
	}

	public long getJobMinLatency() {
		return jobMinLatency;
	}

	public boolean isFinished(){
		return finished;
	}
	
	protected String generateString(long length){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<length; i++){
			sb.append("a");
		}
		return sb.toString();
	}
	
	protected void submitLatency(long latency){
		if(latency > jobMaxLatency) jobMaxLatency = latency;
		if(latency < jobMinLatency) jobMinLatency = latency;
	}
	
	protected abstract void startJob();
}
