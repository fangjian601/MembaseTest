package com.xingcloud.dataproxy.membase.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xingcloud.dataproxy.membase.test.config.ConfigException;
import com.xingcloud.dataproxy.membase.test.job.JobManager;

public class MembaseTest {

	private static Logger logger = LoggerFactory.getLogger(MembaseTest.class);
	
	public static final String DEFAULT_JOB = "traversal_set";
    public static final int DEFAULT_THREAD_NUMBER = 32; 
    public static final long DEFAULT_THREAD_LOOP_NUMBER = 320000;
	
	public static String bucketName = "";
	
	private static Map<String, Object> getArgumentsMap(){
        Map<String, Object> argsMap = new HashMap<String, Object>();
        argsMap.put("job", DEFAULT_JOB);
        argsMap.put("threadnumber", DEFAULT_THREAD_NUMBER);
        argsMap.put("loopnumber", new Long(DEFAULT_THREAD_LOOP_NUMBER));
        return argsMap;
	}
	
	public static void checkArguments(Map<String, Object> argsMap){
        if(!argsMap.containsKey("bucket")){
                logger.error("bucket name must be specific!");
                System.exit(1);
        }
        logger.info("arguments parse successfully, begin membase test...");
	}
	
	private static void parseArguments(Map<String,Object> argsMap, String[] args, int startIndex){
        if(args.length - startIndex > 1){
                String key = args[startIndex];
                String value = args[startIndex+1];
                if(key.equals("-j")){
                        argsMap.put("job", value);
                }
                else if(key.equals("-b")){
                        argsMap.put("bucket", value);
                }
                else if(key.equals("-c")){
                        argsMap.put("threadnumber", parseIntegerArgument(value, DEFAULT_THREAD_NUMBER));
                }
                else if(key.equals("-n")){
                        argsMap.put("loopnumber", parseLongArgument(value, DEFAULT_THREAD_LOOP_NUMBER));
                }
                parseArguments(argsMap, args, startIndex+2);
        }	
	}
	
	private static void beginTest(Map<String, Object> argsMap) throws IllegalArgumentException, ConfigException, IOException{
		MembaseTest.bucketName = (String)(argsMap.get("bucket"));
		String job = (String)(argsMap.get("job"));
		int threadNumber = (Integer)(argsMap.get("threadnumber"));
		long loopNumber = (Long)(argsMap.get("loopnumber"));
		JobManager jobManager = new JobManager(job, loopNumber, threadNumber);
		jobManager.startJob();
		
	}
	
	private static int parseIntegerArgument(String arg, int defaultValue){
        try {
                return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
                return defaultValue;
        }

	}
	
	private static long parseLongArgument(String arg, long defaultValue){
        try {
                return Long.parseLong(arg);
        } catch (NumberFormatException e) {
                return defaultValue;
        }

	}
	
	public static void main(String[] args){
		try {
			Map<String, Object> argsMap = getArgumentsMap();
            parseArguments(argsMap, args, 0);
            checkArguments(argsMap);
            beginTest(argsMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
