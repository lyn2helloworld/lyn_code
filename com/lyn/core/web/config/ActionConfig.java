package com.lyn.core.web.config;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig {
	//封装<action name="" class="" method="" >元素信息
	//每一个action元素对应一个ActionConfig对象
	private String name;
	private String classname;
	private String method;
	//action元素中多个result元素
	private Map<String, ResultConfig> resultMap = new HashMap<>();
	
	public ActionConfig(){}
	
	public ActionConfig(String name, String classname, String method) {
		this.name = name;
		this.classname = classname;
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
	public Map<String, ResultConfig> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, ResultConfig> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public String toString() {
		return "ActionConfig [name=" + name + ", classname=" + classname
				+ ", method=" + method + ", resultMap=" + resultMap + "]";
	}

	

}
