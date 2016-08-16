package cn.systoon.qc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
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
 * 通过反射，实现PerfermanceServlet 最为路由，只做转发 根据不同的method参数执行不同的处理方法 loadOnStartup = 1
 * tomcat启动时执行（主要是为了执行init方法，返回整个WEB项目对象serviceList对象）
 */
@WebServlet(urlPatterns = { "/performServlet" }, loadOnStartup = 1)
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 获取参数
	// 测试接口所需相关参数
	String ip = ""; // IP地址
	String port = ""; // 端口号
	String path = ""; // 接口请求路径 获取前端参数名为 “pathText”的参数值
	String requestMethod = ""; // 请求方法（Get or Post）
	String paramType = ""; // 参数类型，1为K-V方式， 2为 Body方式
	Integer paramTypeInt = -1; // paramType 转Integer类型
	String paramCount = ""; // K-V方式 时，参数个数
	Map<String, String> paramsMap = new HashMap<String, String>(); // K-V方式
																	// 时，参数值
	String parameters = ""; // Body方式 时，参数值
	String url = ""; // 请求的URI
	// 保存测试计划需要的相关参数
	String vuser = ""; // 并发用户数
	String duration = ""; // 执行测试时间
	String testFiled = ""; // 设置断言范围
	String testType = ""; // 设置断言规则
	String assertion = ""; // 设置断言内容
	String testPlanName = ""; // 测试计划名称
	String testPlanDesc = ""; // 测试计划描述
	// 设置执行测试个共用参数，从web.xml中读取
	String jmxPlanTemple = ""; // 测试计划模板（完整路径+名称）
	String jmxPlanPath = ""; // 测试计划保存路径（根目录）
	String jmxPlanName = ""; // 测试计划名称，添加_Vuser_CurrentTime
	String jmxPlan = ""; // 可执行的测试计划，jmxPlanPath + jmxPlanName

	String jmxExcutePath = ""; // Jmete执行文件路径 ${JMETER_HOME}/bin/jmeter.sh
	String jmxJtlPath = ""; // 测试结果保存路径（根目录）
	String jmxJtlName = ""; // jmxPlanName.jmx 转换成 jmxJtlName.jtl

	String jmxLogPath = "";

	String methodName = ""; // performServlet?method=save 反射时获取的方法名称
							// 通过获取参数“method”的值获得

	public PerformServlet() {
		super();
	}

	private static Log log = LogFactory.getLog(PerformServlet.class);

	/**
	 * 通过数据库查询serviceList列表 通过servletContext.setAttribute设置参数
	 * 前端通过application.getAtrribute方法获取
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		List<ServiceList> serviceLists = serviceListDAOImpl.getAll();
		this.getServletContext().setAttribute("serviceLists", serviceLists);
		System.out.println(serviceLists);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取请求参数
		ip = request.getParameter("ip");
		port = request.getParameter("port");
		path = request.getParameter("pathText");
		requestMethod = request.getParameter("requestMethod");
		paramType = request.getParameter("paramType");
		parameters = request.getParameter("parameters");
		url = "http://" + ip + ":" + port + path;

		vuser = request.getParameter("vuser");
		duration = request.getParameter("duration");
		testFiled = request.getParameter("testFiled");
		testType = request.getParameter("testType");
		assertion = request.getParameter("assertion");
		testPlanName = request.getParameter("testPlanName");
		testPlanDesc = request.getParameter("testPlanDesc");

		jmxPlanPath = this.getServletContext().getInitParameter("jmxPlanPath");
		jmxPlanTemple = this.getServletContext().getInitParameter("jmxPlanTemple");

		methodName = request.getParameter("method");
		
		if(paramType != null){
			paramTypeInt = Integer.parseInt(paramType);
		}

		// 获取K-V方式参数值
		if (paramTypeInt == 1) {
			paramCount = request.getParameter("paramCount");
			System.out.println(paramCount);

			int count = Integer.parseInt(paramCount);
			for (int i = 0; i < count; i++) {
				String paramName = request.getParameter("paramName" + i);
				String paramValue = request.getParameter("paramValue" + i);
				if (StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(paramValue)) {
					paramsMap.put(paramName, paramValue);
				}
			}
		}

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * 处理ApiInfo表中的数据
		 */
		Map<String, String> postHeader = new HashMap<String, String>();
		String result = null;
		List<Object> resultList = null;

		// 如果接口信息做了修改，那么将新的信息更新到ApiInfo表中 需要返回ApiId。
		// 后期实现（或者不实现）
		// ApiInfo apiInfo = new ApiInfo(id, path, warnameId, paramTypeInt,
		// parameters, requestMethod);
		// Integer apiId = null; //写入parameters表中的关联apiId；

		/**
		 * 根据接口请求方法，调用不同的HttpUtils请求方法
		 */
		if (requestMethod.equals("get")) {
			if (paramTypeInt == 1) {
				if (!paramsMap.isEmpty()) {
					result = HttpUtils.doGetForm(url, paramsMap, "utf-8");
				}
			} else if (paramTypeInt == 2) {
				url = url + "?" + parameters;
				result = HttpUtils.doGetStrReq(url, "utf-8");
			}
		} else if (requestMethod.equals("post")) {
			postHeader.put("Content-Type", "application/x-www-form-urlencoded");
			if (paramTypeInt == 1) {
				if (!paramsMap.isEmpty()) {
					resultList = HttpUtils.doPostFormReq(url, postHeader, paramsMap, "utf-8");
				}
				result = resultList.toString();
			} else if (paramTypeInt == 2) {

				result = HttpUtils.doPostStringReq(url, postHeader, parameters, "utf-8");
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);

		// 抛出异常时，未处理，异常信息也没有打印到屏幕上，待处理。。。。。
	}

	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (StringUtils.isBlank(testPlanName)) {
			jmxPlanName = path.substring(1, path.length()).replace('/', '_') + "_" + vuser + "Vuser" + "_"
					+ System.currentTimeMillis() + ".jmx";
		} else {
			jmxPlanName = testPlanName + "_" + vuser + "Vuser" + "_" + System.currentTimeMillis() + ".jmx";
		}
		jmxPlan = jmxPlanPath + jmxPlanName;

		// 测试接口
		// test(request, response);
		System.out.println("*************");
		getParametersMap(request, response);

		JmxParserDom4jHandler.createJmxPlan(jmxPlanTemple, jmxPlan, ip, port, path, requestMethod, paramTypeInt,
				parameters, paramsMap, vuser, assertion, duration, testFiled, testType);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(JmxParserDom4jHandler.getStringBuilder().toString());
		
		/*
		 * 1、执行数据库程序，保持测试计划路径，名称，apiID等 到指定 的数据表中
		 * 
		 * 2、将测试计划保持到 apache服务器中，支持点击下载
		 * 
		 * 
		 */

	}

	protected void getServiceIp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void getListApiPath(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void getApiInfoById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void getParametersByApiId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("getParametersByApiId");
		String id = request.getParameter("id");
		Integer apiId = Integer.parseInt(id);
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

	protected void getParametersMap(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 获取所有参数
		 */
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap = request.getParameterMap();

		// 打印所有参数
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			System.out.print(entry.getKey() + "=");
			for (int i = 0; i < entry.getValue().length; i++) {
				System.out.println(" " + entry.getValue()[i]);
			}
		}
	}

}
