package cn.systoon.qc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.systoon.qc.dao.impl.ServiceListDAOImpl;
import cn.systoon.qc.domain.ServiceList;
import cn.systoon.qc.jmxhandler.JmxParserDom4jHandler;
import cn.systoon.qc.utils.HttpClientUtil;

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
    String duration = "";
    String url = "";
    String jmxPlanTemple = "/Users/perfermance/JmeterTest/script/jmxPlanTemple.jmx";
    String jmxPlan = "";
    String jmxPlanPath = "/Users/perfermance/JmeterTest/script/";
    String descript = "";
	private boolean flag = false;
	
	private static Log log = LogFactory.getLog(PerformServlet.class);

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
		duration = request.getParameter("duration");
		jmxPlan = request.getParameter("testplanname");
		descript = request.getParameter("testplandesc");
		methodName=request.getParameter("method");
		if(StringUtils.isBlank(port)){
			port = "80";
		}
//		url = "http://" + ip + ":" + port + path;
//		
//		System.out.println("ip：" + ip);
//		System.out.println("port：" + port);
//		System.out.println("path：" + path);
//		System.out.println("parameters：" + parameters);
//		System.out.println("requestMethod：" + requestMethod);
//		System.out.println("vuser：" + vuser);
//		System.out.println("assertion：" + assertion);
//		System.out.println("duration：" + duration);
//		System.out.println("jmxPlan：" + jmxPlan);
//		System.out.println("descript：" + descript);
//		System.out.println("methodName：" + methodName);
//		System.out.println("url：" + url);
		
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(requestMethod.equals("get")){
			if(StringUtils.isNotBlank(parameters)){
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
		if(StringUtils.isBlank(jmxPlan)){
			if(StringUtils.isNotBlank(path)){
				jmxPlan = jmxPlanPath + path.substring(1, path.length()).replace('/', '_') + "_" + vuser + ".jmx";
			}else{
				jmxPlan = jmxPlanPath + System.currentTimeMillis() + ".jmx";
			}
		}else{
			jmxPlan = jmxPlanPath + jmxPlan + ".jmx";
		}
		
		if(requestMethod.equals("get")){
			if(StringUtils.isNotBlank(parameters)){
				flag = HttpClientUtil.get(url + "?" + parameters);
			}else{
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
			JmxParserDom4jHandler.createJmxPlan(jmxPlanTemple, jmxPlan, ip, port, path, requestMethod, parameters, vuser, assertion,duration);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(JmxParserDom4jHandler.getStringBuilder().toString());
		}
		
	}
	
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		List<ServiceList> serviceLists = serviceListDAOImpl.getAll();
		request.setAttribute("serviceLists", serviceLists);
		request.getRequestDispatcher("/html/edit.jsp").forward(request, response);
		
	}
	
	protected void getServiceIp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("edit");
		String id = request.getParameter("id");
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		ServiceList servicelist = serviceListDAOImpl.getServiceById(id);
		log.debug("debug: " + servicelist);
		log.error("error: " + servicelist);
		log.fatal("fatal: " + servicelist);
		log.info("info: " + servicelist);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(servicelist);
		log.info("*******************" + result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		response.getWriter().println(result);
		
		
	}
	
	


}
