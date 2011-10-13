package com.xingcloud.dataproxy.membase.test.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xingcloud.dataproxy.membase.test.config.ConfigException;
import com.xingcloud.dataproxy.membase.test.random.RandomGenerator;

public class JobManager {
	
	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
	
	private MembaseClientWrapper clientWrapper;
	private RandomGenerator generator;
	private String jobName;
	private List<AbstractJob> jobList;
	private long loopNumber;
	private int threadNumber;
	private Timer statsTimer;
	
	public JobManager(String jobName, long loopNumber, int threadNumber) throws ConfigException, IllegalArgumentException, IOException{
		this.clientWrapper = MembaseClientWrapper.getInstance();
		this.generator = RandomGenerator.getInstance();
		this.jobName = jobName;
		this.jobList = new ArrayList<AbstractJob>();
		this.loopNumber = loopNumber;
		this.threadNumber = threadNumber;
		this.statsTimer = new Timer();
		init();
	}
	
	private void init() throws IllegalArgumentException{
		initJobList(jobName);
	}
	
	private void initJobList(String jobName) throws IllegalArgumentException{
		long jobRange = getJobRange();
		if(jobRange == 0){
			throw new IllegalArgumentException("loopNumber should greater than threadNumber and " +
					"threadNumber cann't be 0");
		}
		long jobBegin = 0;
		long jobEnd = jobRange-1;
		for(int i=0; i<threadNumber; i++){
			jobList.add(JobFactory.getInstance(jobName, clientWrapper, generator, jobBegin, jobEnd));
			jobBegin += jobRange;
			jobEnd += jobRange;
			if(jobBegin >= loopNumber) break;
			if(jobEnd >= loopNumber) jobEnd = loopNumber-1;
		}
	}
	
	private long getJobRange(){
		if(threadNumber != 0){
			return loopNumber/threadNumber;
		}
		return 0;
	}
	
	public void startJob(){
		for(int i=0; i<jobList.size(); i++){
			jobList.get(i).start();
			logger.debug("Start "+jobName+" thread "+i);
		}
		statsTimer.schedule(new JobStatsTimerTask(this), 1000, 1000);
	}
	
	public AbstractJob[] getJobList(){
		AbstractJob[] jobArray = new AbstractJob[jobList.size()];
		jobList.toArray(jobArray);
		return jobArray;
	}
}
