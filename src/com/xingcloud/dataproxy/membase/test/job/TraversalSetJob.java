package com.xingcloud.dataproxy.membase.test.job;

import com.xingcloud.dataproxy.membase.test.random.RandomGenerator;

public class TraversalSetJob extends AbstractJob {

	public TraversalSetJob(MembaseClientWrapper clientWrapper,
			RandomGenerator generator, long jobRangeBegin, long jobRangeEnd) {
		super(clientWrapper, generator, jobRangeBegin, jobRangeEnd);
	}

	@Override
	protected void startJob() {
		for(long i=jobRangeBegin; i<=jobRangeEnd; i++){
			long startTime = System.currentTimeMillis();
			membaseClient.set("key"+i, generateString(generator.generate()));
			long stopTime = System.currentTimeMillis();
			submitLatency(stopTime - startTime);
			jobFinishedCount++;
		}

	}

}
