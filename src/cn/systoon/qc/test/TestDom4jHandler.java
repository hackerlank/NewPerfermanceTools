package cn.systoon.qc.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.systoon.qc.jmxhandler.JmxParserDom4jHandler;

public class TestDom4jHandler {

	@Test
	public void test() {
		String ip = "127.0.0.1"; // IP地址
		String port = "8081"; // 端口号
		String path = "/service/path"; // 接口请求路径 获取前端参数名为 “pathText”的参数值
		String requestMethod = "get"; // 请求方法（Get or Post）
		String paramType = "1"; // 参数类型，1为K-V方式， 2为 Body方式
		Map<String, String> paramsMap = new HashMap<String, String>(); // K-V方式
		String parameters = "body=KKKKKKKKKKKKKKKKK"; // Body方式 时，参数值
		String vuser = "20"; // 并发用户数
		String duration = "500"; // 执行测试时间
		String testFiled = "Assertion.response_Header"; // 设置断言范围
		String testType = "8"; // 设置断言规则
		String assertion = "success"; // 设置断言内容
		String jmxPlanTemple = "/Users/perfermance/JmeterTest/script/jmxPlanTemple.jmx";
		String jmxPlan = "/Users/perfermance/JmeterTest/script/testAssertionTestField.jmx";
		JmxParserDom4jHandler.createJmxPlan(jmxPlanTemple, jmxPlan, ip, port, path, requestMethod, paramType, parameters, paramsMap, vuser, assertion, duration, testFiled, testType);
	}

}

