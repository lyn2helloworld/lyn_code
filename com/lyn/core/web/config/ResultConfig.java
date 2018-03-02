package com.lyn.core.web.config;

public class ResultConfig {
	private String viewname;
	private String type;
	private String path;
	
	
	public ResultConfig(String viewname, String type, String path) {
		super();
		this.viewname = viewname;
		this.type = type;
		this.path = path;
	}
	public String getViewname() {
		return viewname;
	}
	public void setViewname(String viewname) {
		this.viewname = viewname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "ResultConfig [viewname=" + viewname + ", type=" + type
				+ ", path=" + path + "]";
	}
	
	
	
}
