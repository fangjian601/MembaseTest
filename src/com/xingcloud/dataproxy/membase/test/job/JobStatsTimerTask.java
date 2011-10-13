package com.xingcloud.dataproxy.membase.test.job;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobStatsTimerTask extends TimerTask {

	private static Logger logger = LoggerFactory.getLogger(JobStatsTimerTask.class);
	
	//private JobManager jobManager;
	private long jobLastFinishedCount;
	private List<Long> tpsList;
	AbstractJob[] jobList;
	private int period;
	
	public JobStatsTimerTask(JobManager jobManager){
		//this.jobManager = jobManager;
		this.jobLastFinishedCount = 0;
		this.tpsList = new ArrayList<Long>();
		jobList = jobManager.getJobList();
		this.period = 0;
	}
	
	@Override
	public void run() {
		if(!isAlljobFinished()){
			submitTPS();
		}
		else{
			logAllStats();
			cancel();
		}

	}
	
	private void submitTPS(){
		long jobFinishedCount = getJobFinishedCount();
		long tps = jobFinishedCount - jobLastFinishedCount;
		tpsList.add(tps);
		logger.debug("period "+period+" finished "+tps);
		jobLastFinishedCount = jobFinishedCount;
		period++;
	}
	
	private long getJobFinishedCount(){
		long sum = 0;
		for(int i=0; i<jobList.length; i++){
			AbstractJob job = jobList[i];
			sum += job.getJobFinishedCount();
		}
		return sum;
	}
	
	private boolean isAlljobFinished(){
		for(int i=0; i<jobList.length; i++){
			if(!jobList[i].isFinished())
				return false;
		}
		return true;
	}

	
	private long[] getLatencyList(){
		long[] latencyList = new long[jobList.length];
		for(int i=0; i<jobList.length; i++){
			latencyList[i] = jobList[i].getJobLatency();
		}
		return latencyList;
	}
	
	private long getAverageLatency(){
		long sum = 0;
		for(int i=0; i<jobList.length; i++){
			sum+= jobList[i].getJobLatency();
		}
		if(jobList.length > 0) return sum/jobList.length;
		return 0;
	}
	
	private long getMaxLatency(){
		long maxLatency = 0;
		for(int i=0; i<jobList.length; i++){
			if(jobList[i].getJobMaxLatency() > maxLatency){
				maxLatency = jobList[i].getJobMaxLatency();
			}
		}
		return maxLatency;
	}
	
	private long getMinLatency(){
		if(jobList.length <= 0) return 0;
		long minLatency = Long.MAX_VALUE;
		for(int i=0; i<jobList.length; i++){
			if(jobList[i].getJobMinLatency() < minLatency){
				minLatency = jobList[i].getJobMinLatency();
			}
		}
		return minLatency;
	}
	
	private long getAverageTPS(){
		long sum = 0;
		for(int i=0; i<tpsList.size(); i++){
			sum += tpsList.get(i);
		}
		if(tpsList.size() > 0) return sum/tpsList.size();
		return 0;
	}
	
	
	private long getMaxTPS(){
		long maxTPS = 0;
		for(int i=0; i<tpsList.size(); i++){
			if(tpsList.get(i) > maxTPS) maxTPS = tpsList.get(i);
		}
		return maxTPS;
	}
	
	private long getMinTPS(){
		if(tpsList.size() <= 0) return 0;
		long minTPS = Long.MAX_VALUE;
		for(int i=0; i<tpsList.size(); i++){
			if(tpsList.get(i) < minTPS) minTPS = tpsList.get(i);
		}
		return minTPS;
	}
	
	private void logAllStats(){
		logTPSStats();
		logLatencyStats();
	}
	
	private void logTPSStats(){
		StringBuffer allTPS = new StringBuffer();
		for(int i=0; i<tpsList.size(); i++){
			allTPS.append(tpsList.get(i)+" ");
		}
		logger.info("all TPS: "+allTPS.toString());
		logger.info("average TPS: "+getAverageTPS());
		logger.info("max TPS: "+getMaxTPS());
		logger.info("min TPS: "+getMinTPS());
	}
	
	private void logLatencyStats(){
		StringBuffer allThreadLatency = new StringBuffer();
		long[] latencyList = getLatencyList();
		for(int i=0; i<latencyList.length; i++){
			allThreadLatency.append(latencyList[i]+"ms ");
		}
		logger.info("all thread latency: "+allThreadLatency.toString());
		logger.info("average latency: "+getAverageLatency()+"ms");
		logger.info("max latency: "+getMaxLatency()+"ms");
		logger.info("min latency: "+getMinLatency()+"ms");
	}
}
