package com.xingcloud.dataproxy.membase.test.config;

public class ConfigException extends Exception {
	private static final long serialVersionUID = -3665892520678581238L;

	private String msg;
	
	public ConfigException(String msg){
		this.msg = msg;
	}
	
	public String getMessage(){
		return msg;
	}
}
