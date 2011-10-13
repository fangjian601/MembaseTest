package com.xingcloud.dataproxy.membase.test.random;

import java.util.Random;

public class Distribution {
	private long begin;
	private long end;
	private long count;
	private long offset;
	private Random random;
	
	public Distribution(long begin, long end, long count, long offset){
		this.begin = begin;
		this.end = end;
		this.count = count;
		this.offset = offset;
		this.random = new Random();
		checkBeginEnd();
	}

	public Distribution(long begin, long end, long count){
		this.begin = begin;
		this.end = end;
		this.count = count;
		this.offset = 0;
		this.random = new Random();
		checkBeginEnd();
	}
	
	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}

	public long getCount() {
		return count;
	}

	public long getOffset() {
		return offset;
	}
	
	public void setOffset(long offset){
		this.offset = offset;
	}
	
	public long getRandom(){
		return (long) (random.nextDouble()*(end-begin)+begin);
	}
	
	private void checkBeginEnd(){
		if(begin < 0) begin = 0;
		if(end < 0) end = 0;
		if(end < begin){
			long tmp = end;
			this.end = begin;
			this.begin = tmp;
		}
		if(begin == end){
			count = 0;
		}
	}
	
}
