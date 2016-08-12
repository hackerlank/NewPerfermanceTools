package cn.systoon.qc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.dbcp.pool.impl.GenericKeyedObjectPool.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.systoon.qc.dao.impl.ApiInfoDAOImpl;
import cn.systoon.qc.dao.impl.ParametersDAOImpl;
import cn.systoon.qc.dao.impl.ServiceListDAOImpl;
import cn.systoon.qc.domain.ApiInfo;
import cn.systoon.qc.domain.Parameters;
import cn.systoon.qc.domain.ServiceList;
import cn.systoon.qc.jmxhandler.JmxParserDom4jHandler;
import cn.systoon.qc.utils.HttpUtils;

/**
 * Servlet implementation class PerformServlet
 */
@WebServlet(urlPatterns = {"/performServlet"}, loadOnStartup = 1)
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PerformServlet() {
        super();
    }
    /**
     * 数据表 ApiInfo表中数据
     */
    String warnameId = "";
    String ip = "";
    String port = "";
    String path = "";
    String paramType = "";
    String parameters = "";  //当paramType＝2时，写入parameters值;
    String requestMethod = "";
    
    /**
     * 数据表 Parameters表中数据,需关联表一中的 ApiInfoId
     */
    String paramCount = "";
    
    /**
     * 数据表 testPlan 表中数据, 关联
     */
    String vuser = "";
    String assertion = "";
    String duration = "";
    String methodName = "";
    String descript = "";
    String testPlanName = "";
    
    String url = "";
    String jmxPlan = "";
    String jmxPlanTemple = "/Users/perfermance/JmeterTest/script/jmxPlanTemple.jmx";
    String jmxPlanPath = "/Users/perfermance/JmeterTest/script/";
	private boolean flag = false;
	
	private static Log log = LogFactory.getLog(PerformServlet.class);

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		List<ServiceList> serviceLists = serviceListDAOImpl.getAll();
		this.getServletContext().setAttribute("serviceLists", serviceLists);
		System.out.println(serviceLists);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		vuser = request.getParameter("vuser");
		assertion = request.getParameter("assertion");
		duration = request.getParameter("duration");
		testPlanName = request.getParameter("testplanname");
		descript = request.getParameter("testplandesc");
		methodName=request.getParameter("method");
		
		
		ip = request.getParameter("ip");
		port = request.getParameter("port");
		if(StringUtils.isBlank(port)){
			port = "80";
		}
		url = "http://" + ip + ":" + port + path;
	
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	protected Boolean test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean flag = false;
		
		/**
		 * 处理ApiInfo表中的数据
		 */
		Integer id = null;
		Integer paramTypeInt = null;
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String> postHeader = new HashMap<String,String>();
		List<Parameters> paramList = new ArrayList<Parameters>();
		String result = null;
		List<Object> resultList = null;
		
		//warnameId = request.getParameter("project");
		path = request.getParameter("pathText");
		requestMethod = request.getParameter("requestmethod");
		paramType = request.getParameter("paramType");
		
		System.out.println("###############");
		System.out.println("paramType ==> " + paramType);
		System.out.println("requestMethod ==> " + requestMethod);
		System.out.println("path ==> " + path);
		url = "http://" + ip + ":" + port + path;
		
		//parameters = request.getParameter("parameters");
		if(paramType != null){
			paramTypeInt = Integer.parseInt(paramType);
		}
		

		
		//如果接口信息做了修改，那么将新的信息更新到ApiInfo表中 需要返回ApiId。
		//后期实现（或者不实现）
		//ApiInfo apiInfo = new ApiInfo(id, path, warnameId, paramTypeInt, parameters, requestMethod);
		//Integer apiId = null; //写入parameters表中的关联apiId；
//		
//		/**
//		 * 处理parameters表中的数据
//		 * 当paramType ＝ 1时表示 key－value模式
//		 */
		if(paramTypeInt == 1){
			System.out.println("*****key-value*****");
			paramCount = request.getParameter("paramCount");
			System.out.println(paramCount);
			
			int count = Integer.parseInt(paramCount);
			for(int i = 0; i < count;i++){
				String paramName = request.getParameter("paramName" + i);
				String paramValue = request.getParameter("paramValue" + i);
				
				//将params数据写入到param表中
				//paramList.add(new Parameters(apiId, paramName, paramValue));
				
				if(StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(paramValue)){
					params.put(paramName, paramValue);
				}
				
			}
			System.out.println(params);
		}else if(paramTypeInt == 2){
			parameters = request.getParameter("parameters");
			System.out.println(parameters);
		}
		

//		
//		System.out.println(requestMethod);
//		
//		
		if(requestMethod.equals("get")){
			if(paramTypeInt == 1){
				if(!params.isEmpty()){
					result = HttpUtils.doGetForm(url, params, "utf-8");
				}
			}else if(paramTypeInt == 2){
				url = url + "?" + parameters;
				result = HttpUtils.doGetStrReq(url, "utf-8");
			}
		}else if(requestMethod.equals("post")){
			//postHeader.put("Content-Type", "application/json");
			postHeader.put("Content-Type", "application/x-www-form-urlencoded");
			if(paramTypeInt == 1){
				if(!params.isEmpty()){
					resultList = HttpUtils.doPostFormReq(url, postHeader,params, "utf-8");
				}
				parameters = params.toString();
				result = resultList.toString();
			}else if(paramTypeInt == 2){
				
				System.out.println("*********&&&&&&&&&********");
				result = HttpUtils.doPostStringReq(url, postHeader, parameters, "utf-8");
			}
		}
		
		
		
		if(StringUtils.isNotEmpty(result)){
			flag = true;
		}else{
			flag = false;
		}
		response.setCharacterEncoding("UTF-8");
//		response.getWriter().println("请求信息：" + "<br>");
//		response.getWriter().println("请求url：" + url);
//		response.getWriter().println("请求参数：");
//		response.getWriter().println("请求parameter：" + parameters);
//		response.getWriter().println("");
		response.getWriter().println("请求结果：");
		response.getWriter().println(result);
		
		//抛出异常时，未处理，异常信息也没有打印到屏幕上，待处理。。。。。
		return flag;
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(StringUtils.isBlank(testPlanName)){
			if(StringUtils.isNotBlank(path)){
				testPlanName = jmxPlanPath + path.substring(1, path.length()).replace('/', '_') + "_" + vuser;
				jmxPlan =testPlanName + ".jmx";
			}else{
				testPlanName = jmxPlanPath + System.currentTimeMillis();
				jmxPlan =testPlanName + ".jmx";
			}
		}else{
			jmxPlan = jmxPlanPath + testPlanName + ".jmx";
		}
		

		
		
		if(!test(request,response)){
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("接口请求返回异常，请检查。");
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
		System.out.println("getServiceIp");
		String id = request.getParameter("id");
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		ServiceList servicelist = serviceListDAOImpl.getServiceById(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(servicelist);
		log.info("********getServiceIp***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
		
		
	}
	
	protected void getListApiPath(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getListApiPath");
		String id = request.getParameter("id");
		ApiInfoDAOImpl apiInfoDAOImpl = new ApiInfoDAOImpl();
		List<ApiInfo> apiInfoList = apiInfoDAOImpl.getListWithWarnameId(id);
		System.out.println(apiInfoList);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(apiInfoList);
		log.info("********getListApiPath***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
	}
	
	protected void getApiInfoById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getApiInfoById");
		String id = request.getParameter("id");
		System.out.println(id);
		ApiInfoDAOImpl apiInfoDAOImpl = new ApiInfoDAOImpl();
		ApiInfo apiInfo = apiInfoDAOImpl.get(id);
		System.out.println(apiInfo);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(apiInfo);
		log.info("********getListApiPath***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
	}
	
	protected void getParametersByApiId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getParametersByApiId");
		String id = request.getParameter("id");
		Integer apiId = Integer.valueOf(id).intValue();
		System.out.println(apiId);
		ParametersDAOImpl parametersDAOImpl = new ParametersDAOImpl();
		List<Parameters> parametersList = parametersDAOImpl.getListWithApiId(apiId);
		System.out.println(parametersList);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(parametersList);
		log.info("********getParametersByApiId***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
	}
	
	


}
