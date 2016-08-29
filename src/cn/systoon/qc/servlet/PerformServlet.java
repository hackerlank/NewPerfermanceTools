package cn.systoon.qc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.systoon.qc.shell.ExecuteShell;

import cn.systoon.qc.dao.impl.ApiInfoDAOImpl;
import cn.systoon.qc.dao.impl.ParametersDAOImpl;
import cn.systoon.qc.dao.impl.ServiceListDAOImpl;
import cn.systoon.qc.dao.impl.TestPlanDAOImpl;
import cn.systoon.qc.domain.ApiInfo;
import cn.systoon.qc.domain.Parameters;
import cn.systoon.qc.domain.ServiceList;
import cn.systoon.qc.domain.TestPlan;
import cn.systoon.qc.jmxhandler.JmxParserDom4jHandler;
import cn.systoon.qc.utils.HttpUtils;

/**
 * 通过反射，实现PerfermanceServlet 最为路由， 只做【路由】转发 根据不同的method参数执行不同的处理方法 loadOnStartup
 * = 1 tomcat启动时执行（主要是为了执行init方法，返回整个WEB项目对象serviceList对象）
 */
@WebServlet(urlPatterns = { "/performServlet" }, loadOnStartup = 1)
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 获取参数
	// 测试接口所需相关参数
	String ip = ""; // IP地址
	String port = ""; // 端口号
	String path = ""; // 接口请求路径 获取前端参数名为 “pathText”的参数值
	String apiIdStr = ""; // 请求接口的apiId在 apiInfo表中的主键
	Integer apiId = -1; // 转出Integer类型
	String requestMethod = ""; // 请求方法（Get or Post）
	String paramType = ""; // 参数类型，1为K-V方式， 2为 Body方式
	Integer paramTypeInt = -1; // paramType 转Integer类型
	String paramCount = ""; // K-V方式 时，参数个数
	Map<String, String> paramsMap = new HashMap<String, String>(); // K-V方式时，参数值
	String paramsBody = ""; // Body方式 时，参数值
	String parameters = ""; // 存储到testplan表中时的参数值
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
	String jmxPlanTypeStr = ""; // 测试计划类型（1、为单接口，2、为聚合场景计划）
	Integer jmxPlanType = -1; // 测试计划类型转为Integer类型
	String jmxPlanPath = ""; // 测试计划保存路径（根目录）
	String jmxPlanName = ""; // 测试计划名称，添加_Vuser_CurrentTime
	String jmxPlan = ""; // 可执行的测试计划，jmxPlanPath + jmxPlanName

	String jmxExcutePath = ""; // Jmete执行文件路径 ${JMETER_HOME}/bin/jmeter.sh
	String jmxJtlPath = ""; // 测试结果保存路径（根目录）
	String jmxJtlName = ""; // jmxPlanName.jmx 转换成 jmxJtlName.jtl

	String jmxLogPath = "";

	String methodName = ""; // performServlet?method=save 反射时获取的方法名称
							// 通过获取参数“method”的值获得

	Map<Integer, String> logMap = new HashMap<Integer, String>(); // 将日志执行保持到Map对象中

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

	/**
	 * 通过反射，实现【路由】主方法，进行跳转
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		methodName = request.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求参数
		ip = request.getParameter("ip");
		port = request.getParameter("port");
		path = request.getParameter("pathText");

		requestMethod = request.getParameter("requestMethod");
		paramType = request.getParameter("paramType");
		paramsBody = request.getParameter("parameters");
		url = "http://" + ip + ":" + port + path;
		if (paramType != null) {
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
			// 将K-V格式参数转为JSON格式传给parameters
			parameters = new ObjectMapper().writeValueAsString(paramsMap);
		} else if (paramTypeInt == 2) {
			parameters = paramsBody;
		}

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

				result = HttpUtils.doPostStringReq(url, postHeader, paramsBody, "utf-8");
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);

		// 抛出异常时，未处理，异常信息也没有打印到屏幕上，待处理。。。。。
	}

	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求参数
		ip = request.getParameter("ip");
		port = request.getParameter("port");
		path = request.getParameter("pathText");
		apiIdStr = request.getParameter("path");
		apiId = Integer.parseInt(apiIdStr);

		requestMethod = request.getParameter("requestMethod");
		paramType = request.getParameter("paramType");
		paramsBody = request.getParameter("parameters");
		url = "http://" + ip + ":" + port + path;
		if (paramType != null) {
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
			// 将K-V格式参数转为JSON格式传给parameters
			parameters = new ObjectMapper().writeValueAsString(paramsMap);
		} else if (paramTypeInt == 2) {
			parameters = paramsBody;
		}

		// 获取性能属性
		vuser = request.getParameter("vuser");
		duration = request.getParameter("duration");
		testFiled = request.getParameter("testFiled");
		testType = request.getParameter("testType");
		assertion = request.getParameter("assertion");
		testPlanName = request.getParameter("testPlanName");
		testPlanDesc = request.getParameter("testPlanDesc");

		jmxPlanTypeStr = request.getParameter("jmxPlanType");
		jmxPlanType = Integer.parseInt(jmxPlanTypeStr);
		jmxPlanPath = this.getServletContext().getInitParameter("jmxPlanPath");
		jmxPlanTemple = this.getServletContext().getInitParameter("jmxPlanTemple");

		long time = System.currentTimeMillis();

		if (StringUtils.isBlank(testPlanName)) {
			jmxPlanName = path.substring(1, path.length()).replace('/', '_') + "_" + vuser + "Vuser" + "_"
					+ System.currentTimeMillis() + ".jmx";
		} else {
			jmxPlanName = testPlanName + "_" + vuser + "Vuser" + "_" + time + ".jmx";
		}
		jmxPlan = jmxPlanPath + jmxPlanName;

		Date createTime = new Date(time);
		System.out.println(createTime);

		// 测试接口
		// test(request, response);
		System.out.println("*************");
		getParametersMap(request, response);

		JmxParserDom4jHandler.createJmxPlan(jmxPlanTemple, jmxPlan, ip, port, path, requestMethod, paramTypeInt,
				paramsBody, paramsMap, vuser, assertion, duration, testFiled, testType);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(JmxParserDom4jHandler.getStringBuilder().toString());

		// 1、将测试计划保持到 apache服务器中，支持点击下载
		response.getWriter().print("<p>" + "文件地址： " + "<a href=\"http://localhost/script/" + jmxPlanName
				+ "\" target=\"_blank\"  download=\"help\"  >" + jmxPlanName + "</a></p>");

		// 2、保存数据到数据库中
		TestPlan testPlan = new TestPlan(jmxPlanName, testPlanDesc, apiId, jmxPlanType, paramTypeInt, parameters,
				jmxPlan, createTime);
		new TestPlanDAOImpl().save(testPlan);

		log.info("save testPlan " + testPlan.toString());

	}

	/**
	 * 通过ID，查看serviceList表中的对应IP地址
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getServiceIp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("getServiceIp");
		String id = request.getParameter("id");
		ServiceListDAOImpl serviceListDAOImpl = new ServiceListDAOImpl();
		ServiceList servicelist = serviceListDAOImpl.getServiceById(id);

		// 将对象转为Json串
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(servicelist);
		log.info("********getServiceIp***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);

	}

	/**
	 * 通过WarnameId 查看 ApiInfo表中的 warNameId对应的 接口Path列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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

	/**
	 * 通过ApiInfo表中的ID，获取该条ApiInfo对应的所有值， 其中主要是获取ParamType和ParamId， 如果ParamType
	 * 为K-V模式 通过ParamId查找Parameters表中对应该ApiId的所有参数
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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

	/**
	 * 通过ParamId查找所有K-V格式的所有参数列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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

	/**
	 * 获取testPlan表中的测试计划，以apiId查询
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getListApiPlan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("getListApiPlan 通过 apiId 获取所有TestPlanList");
		String id = request.getParameter("id");
		System.out.println(id);
		TestPlanDAOImpl testPlanDAOImpl = new TestPlanDAOImpl();
		List<TestPlan> testPlanList = testPlanDAOImpl.getListWithApiId(id);
		System.out.println(testPlanList);

		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(testPlanList);
		log.info("********getListApiPath***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
	}

	/**
	 * testPlan表中通过Id，获取描述信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getDescWithPlanId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("getDescWithPlanId testPlan表中通过Id，获取描述信息");
		String id = request.getParameter("id");
		System.out.println(id);
		TestPlanDAOImpl testPlanDAOImpl = new TestPlanDAOImpl();
		TestPlan testPlan = testPlanDAOImpl.getDescWithPlanId(id);
		System.out.println(testPlan);

		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(testPlan);
		log.info("********getListApiPath***********" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
	}

	/**
	 * 执行测试
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("获取testPlan地址");
		String id = request.getParameter("plan");
		System.out.println(id);
		TestPlanDAOImpl testPlanDAOImpl = new TestPlanDAOImpl();
		TestPlan testPlan = testPlanDAOImpl.getJmxPlanWithPlanId(id);

		String jmxPlan = testPlan.getJmxPlan();
		String jmxPlanName = testPlan.getTestPlanName();
		String jtlResult = jmxPlanName.substring(0, jmxPlanName.lastIndexOf(".")) + ".jtl";
		String baseJmeterPath = this.getServletContext().getInitParameter("baseJmeterPath");
		String baseJtlPath = this.getServletContext().getInitParameter("baseJtlPath");

		// 执行jmeter命令(绝对路径)
		String jmeterRemoteExecute = baseJmeterPath + "jmeter.sh" + " -n -t " + jmxPlan + " -l " + baseJtlPath
				+ jtlResult;

		System.out.println(jmxPlan); // 获取测试计划绝对路径
		System.out.println(jmeterRemoteExecute); // 获取测试计划绝对路径

		/**
		 * 后端调用shell ／ 批处理文件 执行 jmeter -n (no GUI)
		 */

		Process pid = null;
		pid = new ExecuteShell().executeShell(jmeterRemoteExecute);
		logMap.clear();
		if (pid != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
			String msg = null;
			Integer i = 0;
			while ((msg = br.readLine()) != null) {
				logMap.put(i, msg);
				i++;
//				System.out.println(msg);
			}
			br.close();
			pid.destroy();
		} else {
			logMap.put(0, "进程没有启动");
		}

	}

	/**
	 * 获取logMap执行日志
	 * 只有执行run方法后，才执行此方法
	 * @param request
	 * @param response
	 */
	protected void getMapLog(HttpServletRequest request, HttpServletResponse response){
		/**
		 * 获取所有参数
		 */
		
		// 将对象转为Json串
		log.info("********getMapLog***********");
		
		String line = request.getParameter("line");
		Integer i = Integer.parseInt(line);
		int j = logMap.size();
		System.out.println(i);
		String[] strs = null;
		String result = "";
//		Map<Integer,String> logMapTemp = new HashMap<Integer,String>();
		
		if(i <= j){
			strs = new String[j-i+1];
			for(int m=0;m<strs.length;m++){       //每次返回请求行至当前日志总行数的所有记录 
//			logMapTemp.put(j+1, logMap.get(i));
				strs[m] = logMap.get(i);
				i++;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				result = objectMapper.writeValueAsString(strs);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result);
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().println(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 获取request请求的所有请求参数和参数值 已map方式保存，K-V格式保存
	 * 
	 * @param request
	 * @param response
	 */
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
