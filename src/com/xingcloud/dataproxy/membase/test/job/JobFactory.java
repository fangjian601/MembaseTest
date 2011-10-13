package com.xingcloud.dataproxy.membase.test.job;

import com.xingcloud.dataproxy.membase.test.random.RandomGenerator;

public class JobFactory {
	public static AbstractJob getInstance(String jobName, MembaseClientWrapper clientWrapper,
			RandomGenerator generator, long jobRangeBegin, long jobRangeEnd) throws IllegalArgumentException{
		if(jobName.equalsIgnoreCase("traversal_set")){
			return new TraversalSetJob(clientWrapper, generator, jobRangeBegin, jobRangeEnd);
		}
		if(jobName.equalsIgnoreCase("traversal_get")){
			return new TraversalGetJob(clientWrapper, generator, jobRangeBegin, jobRangeEnd);
		}
		throw new IllegalArgumentException("job "+jobName+" not exist!");
	}
}
