package com.xingcloud.dataproxy.membase.test.job;

import com.xingcloud.dataproxy.membase.test.random.RandomGenerator;

public class TraversalGetJob extends AbstractJob {

	public TraversalGetJob(MembaseClientWrapper clientWrapper,
			RandomGenerator generator, long jobRangeBegin, long jobRangeEnd) {
		super(clientWrapper, generator, jobRangeBegin, jobRangeEnd);
	}

	@Override
	protected void startJob() {
		for(long i=jobRangeBegin; i<=jobRangeEnd; i++){
			long startTime = System.currentTimeMillis();
			membaseClient.get("key"+i);
			long stopTime = System.currentTimeMillis();
			submitLatency(stopTime - startTime);
			jobFinishedCount++;
		}

	}

}
