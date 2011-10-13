package com.xingcloud.dataproxy.membase.test.random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator {	
	private static final String RANDOM_CONF = "random.conf";
	
	private static RandomGenerator singleton = null;
	
	private List<Distribution> distributions;
	private long randomMax;
	private Random random;
	
	private RandomGenerator() throws IllegalArgumentException, IOException{
		distributions = new ArrayList<Distribution>();
		randomMax = 0;
		buildDistributions();
		random = new Random(System.currentTimeMillis());
	}
	
	private void buildDistributions() throws IOException, IllegalArgumentException{
		String confPath = RandomGenerator.class.getClassLoader().getResource(RANDOM_CONF).getPath();
		BufferedReader breader = new BufferedReader(new FileReader(confPath));
		String line = null;
		while((line = breader.readLine()) != null){
			addDistribution(line);
		}
		setRandomMax();
	}
	
	private void addDistribution(String entry){
		String[] numbers = entry.split(" ");
		if(numbers != null && numbers.length == 3){
			try{
				Distribution distribution = new Distribution(Long.parseLong(numbers[0]),
						Long.parseLong(numbers[1]), Long.parseLong(numbers[2]));
				distributions.add(distribution);
			} catch(NumberFormatException e){
				return;
			}
		}
	}
	
	private void setRandomMax() throws IllegalArgumentException{
		if(distributions.size() <= 0){
			throw new IllegalArgumentException("random algorithm needs at least one distribution!");
		}
		randomMax = 0;
		for(int i=0; i<distributions.size(); i++){
			Distribution distribution = distributions.get(i);
			randomMax += distribution.getCount();
			distribution.setOffset(randomMax);
		}
	}
	
	private Distribution getDistribution(long flag){
		for(int i=0; i<distributions.size(); i++){
			Distribution distribution = distributions.get(i);
			if(flag <= distribution.getOffset()){
				return distribution;
			}
		}
		return distributions.get(0);
	}
	
	public static RandomGenerator getInstance() throws IllegalArgumentException, IOException{
		if(singleton == null){
			singleton = new RandomGenerator();
		}
		return singleton;
	}
	
	public long generate(){
		long flag = (long) (random.nextDouble()*randomMax);
		Distribution distribution = getDistribution(flag);
		return distribution.getRandom();
	}
}
