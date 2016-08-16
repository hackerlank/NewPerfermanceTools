package cn.systoon.qc.jmxhandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class JmxParserDom4jHandler {

	private static StringBuilder stringBuilder = new StringBuilder();
	public static StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	/**
	 * 
	 * @param jmxPlanTemple   模板文件完整路径（文件路径 + 文件名称）
	 * @param jmxPlan         要生成的测试计划完整测试计划（测试计划文件路径 + 测试计划文件名称）
	 * @param ip              要替换的IP地址
	 * @param port            要替换的端口
	 * @param path   	      要替换的接口路径
	 * @param requestMethod   要替换的接口请求方法 
	 * @param paramType       参数类型
	 * @param paramsMap       K-V 参数值
	 * @param parameters      Body参数值
	 * @param vuser           并发用户数
	 * @param assertion       断言内容
	 * @param duration        持续时间
	 * @param testFiled       断言范围
	 * @param testType        断言规则
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void createJmxPlan(String jmxPlanTemple, String jmxPlan, String ip, String port, String path, String requestMethod,
			String paramType,String parameters, Map<String,String> paramsMap,String vuser, String assertion,String duration,String testFiled,String testType ) {

		SAXReader saxReader = null;
		Document doc = null;
		try {

			saxReader = new SAXReader();
			doc = saxReader.read(new File(jmxPlanTemple));
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append("<p>" + "开始生成JmxPlan文件......" + "\"</p>");

			// 1、替换 并发用户数 Vuser
			List<Element> vuserList = doc.selectNodes("/jmeterTestPlan/hashTree/hashTree/ThreadGroup/stringProp");
			Iterator<Element> iter = vuserList.iterator();
			while (iter.hasNext()) {
				Element vuserEle = iter.next();
				if (vuserEle.attribute("name").getValue().equals("ThreadGroup.num_threads")) {
					vuserEle.setText(vuser);
					stringBuilder.append("<p>" + "设置 并发用户数: \"" + vuserEle.getText()  + "\"</p>");

				}
			}
			
			// 1、替换 持续时间 duration
			List<Element> durationList = doc.selectNodes("/jmeterTestPlan/hashTree/hashTree/ThreadGroup/stringProp");
			Iterator<Element> durationListIter = durationList.iterator();
			while (durationListIter.hasNext()) {
				Element durationEle = durationListIter.next();
				if (durationEle.attribute("name").getValue().equals("ThreadGroup.duration")) {
					durationEle.setText(duration);
					stringBuilder.append("<p>" + "设置 持续执行时间: \"" + durationEle.getText()  + "\"</p>");

				}
			}

			// 替换 请求 参数"
			List<Element> httpRequestArgsList = doc.selectNodes(
					"/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/elementProp/collectionProp/elementProp/stringProp");
			Iterator<Element> httpRequestArgsIter = httpRequestArgsList.iterator();
			while (httpRequestArgsIter.hasNext()) {
				Element args = httpRequestArgsIter.next();
				if (args.attribute("name").getValue().equals("Argument.value")) {
					args.setText(parameters);
					stringBuilder.append("<p>" + "设置 请求参数\"" + args.getText()  + "\"</p>");
				}
			}

			/**
			 * 需通过paramType判断参数类型，配置不同的参数方式，KV or BodyType方式
			 * ？？？？
			 * 
			 * 
			 */
			// 替换 http server 参数
			List<Element> httpServerList = doc
					.selectNodes("/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/stringProp");
			Iterator<Element> httpServerListIter = httpServerList.iterator();
			while (httpServerListIter.hasNext()) {
				Element httpServer = httpServerListIter.next();
				switch (httpServer.attribute("name").getValue()) {

				case "HTTPSampler.domain":
					httpServer.setText(ip);
					stringBuilder.append("<p>" + "设置 IP地址: \"" + httpServer.getText()  + "\"</p>");
					break;

				case "HTTPSampler.port":
					httpServer.setText(port);
					stringBuilder.append("<p>" + "设置 请求端口号: \"" + httpServer.getText()  + "\"</p>");
					break;

				case "HTTPSampler.path":
					httpServer.setText(path);
					stringBuilder.append("<p>" + "设置 接口路径: \"" + httpServer.getText()  + "\"</p>");
					break;

				case "HTTPSampler.method":
					httpServer.setText(requestMethod.toUpperCase());
					stringBuilder.append("<p>" + "设置 接口请求方法: \"" + httpServer.getText()  + "\"</p>");
					break;

				default:
					break;
				}
			}

			
	
			// 替换 断言内容"
			List<Element> httpRequestAssertionList = doc.selectNodes(
					"/jmeterTestPlan/hashTree/hashTree/hashTree/ResponseAssertion/collectionProp/stringProp");
			Iterator<Element> httpRequestAssertionIter = httpRequestAssertionList.iterator();
			while (httpRequestAssertionIter.hasNext()) {
				Element assertionEle = httpRequestAssertionIter.next();
				assertionEle.setText(assertion);
				stringBuilder.append("<p>" + "设置 断言内容: \"" + assertionEle.getText()  + "\"</p>");
			}
			
			// 替换 断言范围  testFiled 参数"
			List<Element> httpRequestAssertionTestFiledList = doc.selectNodes(
					"/jmeterTestPlan/hashTree/hashTree/hashTree/ResponseAssertion/stringProp");
			Iterator<Element> httpRequestAssertionTestFiledIter = httpRequestAssertionTestFiledList.iterator();
			while (httpRequestAssertionTestFiledIter.hasNext()) {
				Element assertionEle = httpRequestAssertionTestFiledIter.next();
				assertionEle.setText(testFiled);
				stringBuilder.append("<p>" + "设置 断言范围: \"" + assertionEle.getText()  + "\"</p>");
			}
			
			// 替换 断言规则"
			List<Element> httpRequestAssertionTestTypeList = doc.selectNodes(
					"/jmeterTestPlan/hashTree/hashTree/hashTree/ResponseAssertion/intProp");
			Iterator<Element> httpRequestAssertionTestTypeIter = httpRequestAssertionTestTypeList.iterator();
			while (httpRequestAssertionTestTypeIter.hasNext()) {
				Element assertionEle = httpRequestAssertionTestTypeIter.next();
				assertionEle.setText(testType);
				stringBuilder.append("<p>" + "设置 断言规则: \"" + assertionEle.getText()  + "\"</p>");
			}
			
			

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fileWriter = new FileWriter(new File(jmxPlan));
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(doc);
			stringBuilder.append("<p>" + "jmxplan 计划文件已生成完毕。" + "</p>");
			stringBuilder.append("<p>" + "文件地址： " + "<a href=\"" + new File(jmxPlan).getAbsolutePath() +"\">"+ new File(jmxPlan).getAbsolutePath() + "</p>");
			xmlWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

}
