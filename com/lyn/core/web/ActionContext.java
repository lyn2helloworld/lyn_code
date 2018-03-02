package com.lyn.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//对一次请求做封装
public class ActionContext {
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static ThreadLocal<ActionContext> threadLocal=new ThreadLocal<>();
	
	
	public ActionContext(HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	public static void setContext(ActionContext actionContext) {
		
		threadLocal.set(actionContext);
	}
	public static ActionContext getContext(){
		return threadLocal.get();
		
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
	
	

}
