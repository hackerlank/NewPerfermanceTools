package cn.systoon.qc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.HttpClientUtils;

import cn.systoon.qc.jmxhandler.HttpClientUtil;
import cn.systoon.qc.jmxhandler.JmxParserDom4jHandler;

/**
 * Servlet implementation class PerformServlet
 */
@WebServlet("/performServlet")
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PerformServlet() {
        super();
    }

    String ip = "";
    String port = "";
    String path = "";
    String parameters = "";
    String requestMethod = "";
    String vuser = "";
    String assertion = "";
    String methodName = "";
    String url = "";
    String jmxPlanTemple = "/Users/perfermance/JmeterTest/script/jmxPlanTemple.jmx";
	private boolean flag = false;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ip = request.getParameter("ip");
		port = request.getParameter("port");
		path = request.getParameter("path");
		parameters = request.getParameter("parameters");
		requestMethod = request.getParameter("requestmethod");
		vuser = request.getParameter("vuser");
		assertion = request.getParameter("assertion");
		methodName=request.getParameter("method");
		if(port == ""){
			port = "80";
		}
		url = "http://" + ip + ":" + port + path;
		
		System.out.println("ip：" + ip);
		System.out.println("port：" + port);
		System.out.println("path：" + path);
		System.out.println("parameters：" + parameters);
		System.out.println("requestMethod：" + requestMethod);
		System.out.println("vuser：" + vuser);
		System.out.println("assertion：" + assertion);
		System.out.println("methodName：" + methodName);
		System.out.println("url：" + url);
		
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(requestMethod.equals("get")){
			if(parameters != ""){
				HttpClientUtil.get(url + "?" + parameters);
			}else{
				HttpClientUtil.get(url);
			}
		}else if(requestMethod.equals("post")){
			HttpClientUtil.postJson(url, parameters);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(HttpClientUtil.getStringBuilder().toString());
		
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jmxPlan = null;
		if(path != ""){
			jmxPlan = "/Users/perfermance/JmeterTest/script/" + path.substring(1, path.length()).replace('/', '_') + "_" + vuser + ".jmx";
		}else{
			jmxPlan = System.currentTimeMillis() + ".jmx";
		}
		
		if(requestMethod.equals("get")){
			if(parameters != ""){
				flag = HttpClientUtil.get(url + "?" + parameters);
			}else{
				System.out.println("2222");
				flag = HttpClientUtil.get(url);
			}
		}else if(requestMethod.equals("post")){
			flag = HttpClientUtil.postJson(url, parameters);
		}else{
			
			System.out.println(flag);
		}
		
		
		if(!flag){
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(HttpClientUtil.getStringBuilder().toString());
		}else{
			JmxParserDom4jHandler.createJmxPlan(jmxPlanTemple, jmxPlan, ip, port, path, requestMethod, parameters, vuser, assertion);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JmxParserDom4jHandler.getStringBuilder().toString());
		}
		
	}
	
	


}
