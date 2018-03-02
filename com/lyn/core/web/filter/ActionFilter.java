package com.lyn.core.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.lyn.core.web.ActionContext;
import com.lyn.core.web.config.ActionConfig;
import com.lyn.core.web.config.ResultConfig;



public class ActionFilter implements Filter{

	private Map<String, ActionConfig> actionMap =new HashMap<>();
	
	public void init(FilterConfig config) throws ServletException {
		Document doc= getDocument();
	NodeList nodeList= doc.getElementsByTagName("action");
	for (int i = 0; i < nodeList.getLength(); i++) {
		Element actionEl=(Element) nodeList.item(i);
		String actionName =actionEl.getAttribute("name");
		String actionClass =actionEl.getAttribute("class");
		String actionMethod =actionEl.getAttribute("method");
		
		ActionConfig actionConfig=new ActionConfig(actionName, actionClass, actionMethod);
		actionMap.put(actionName, actionConfig);
		//----------------------------------------
	NodeList  resultNodeList=actionEl.getElementsByTagName("result");
	Map<String, ResultConfig> resultMap= new HashMap<>();
		for (int j = 0; j < resultNodeList.getLength(); j++) {
			Element resultEl= (Element) resultNodeList.item(j);
			String resultViewname=resultEl.getAttribute("viewname");
			String resultType=resultEl.getAttribute("type");
			String resultPath=resultEl.getTextContent();
			
			ResultConfig resultConfig= new ResultConfig(resultViewname, resultType, resultPath);
			resultMap.put(resultViewname, resultConfig);
				
		}
		actionConfig.setResultMap(resultMap);
		System.out.println(actionMap);
		
		}
	}
	
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;	
	ActionContext actionContext =	new ActionContext(req,resp);
		ActionContext.setContext(actionContext);
		//获取请求资源的名称
		String requestUri = req.getRequestURI().substring(1);
		try {
			if (!actionMap.containsKey(requestUri)) {
				chain.doFilter(req, resp);
			}
			ActionConfig actionConfig=actionMap.get(requestUri);
		Class actionClass=Class.forName(actionConfig.getClassname());
		//创建action对象,并返回逻辑视图名称
		Object actionObject=actionClass.newInstance();
	   Method actionMethod = actionClass.getMethod(actionConfig.getMethod());
	 	String viewName =(String) actionMethod.invoke(actionObject);
	 	//获取action对象中的result元素
	 	ResultConfig resultConfig = actionConfig.getResultMap().get(viewName);
	 	String resultType = resultConfig.getType();
	 	String resultPath = resultConfig.getPath();
	 	if ("dispatcher".equals(resultType)) {
	 		
	 		req.getRequestDispatcher(resultPath).forward(req, resp);
			
		}else if ("redirect".equals(resultType)) {
			
			resp.sendRedirect(resultPath);
			
		}
	 	
		}  catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	public void destroy() {
			}
	
	private Document getDocument(){
		try {
			InputStream in=Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("actions.xml");
		return	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			}  catch (Exception e) {
				e.printStackTrace();
			}
				throw new RuntimeException("从classpath路径加载actions.xml失败！");	
			}

}
