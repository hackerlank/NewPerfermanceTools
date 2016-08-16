package cn.systoon.qc.jmxhandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class JmxParserDom4jHandler {

	private static StringBuilder stringBuilder = new StringBuilder();

	public static StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	/**
	 * 
	 * @param jmxPlanTemple
	 *            模板文件完整路径（文件路径 + 文件名称）
	 * @param jmxPlan
	 *            要生成的测试计划完整测试计划（测试计划文件路径 + 测试计划文件名称）
	 * @param ip
	 *            要替换的IP地址
	 * @param port
	 *            要替换的端口
	 * @param path
	 *            要替换的接口路径
	 * @param requestMethod
	 *            要替换的接口请求方法
	 * @param paramTypeInt
	 *            参数类型(KV,or Body)
	 * @param paramsMap
	 *            K-V 参数值
	 * @param parameters
	 *            Body参数值
	 * @param vuser
	 *            并发用户数
	 * @param assertion
	 *            断言内容
	 * @param duration
	 *            持续时间
	 * @param testFiled
	 *            断言范围
	 * @param testType
	 *            断言规则
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void createJmxPlan(String jmxPlanTemple, String jmxPlan, String ip, String port, String path,
			String requestMethod, Integer paramTypeInt, String parameters, Map<String, String> paramsMap, String vuser,
			String assertion, String duration, String testFiled, String testType) {

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
					stringBuilder.append("<p>" + "设置 并发用户数: \"" + vuserEle.getText() + "\"</p>");

				}
			}

			// 1、替换 持续时间 duration
			List<Element> durationList = doc.selectNodes("/jmeterTestPlan/hashTree/hashTree/ThreadGroup/stringProp");
			Iterator<Element> durationListIter = durationList.iterator();
			while (durationListIter.hasNext()) {
				Element durationEle = durationListIter.next();
				if (durationEle.attribute("name").getValue().equals("ThreadGroup.duration")) {
					durationEle.setText(duration);
					stringBuilder.append("<p>" + "设置 持续执行时间: \"" + durationEle.getText() + "\"</p>");

				}
			}

			/**
			 * 需通过paramType判断参数类型，配置不同的参数方式，KV or BodyType方式
			 * 
			 * 
			 */
			// 替换 请求 参数"
			// paramTypeInt == 2 BodyType 参数方式
			if (paramTypeInt == 2) {
				List<Element> httpRequestArgsList = doc.selectNodes(
						"/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/elementProp/collectionProp/elementProp/stringProp");
				Iterator<Element> httpRequestArgsIter = httpRequestArgsList.iterator();
				while (httpRequestArgsIter.hasNext()) {
					Element args = httpRequestArgsIter.next();
					if (args.attribute("name").getValue().equals("Argument.value")) {
						args.setText(parameters);
						stringBuilder.append("<p>" + "设置 请求参数\"" + args.getText() + "\"</p>");
					}
				}

			} else if (paramTypeInt == 1) {
				//移除BodyType 节点
				Element root = doc.getRootElement();
				Node httpRequestBodyType = root.selectSingleNode("/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/boolProp") ;//得到了元素boolProp 
				httpRequestBodyType.getParent().remove(httpRequestBodyType); //移除整个节点
				
				//添加属性
				Element httpRequestElementPro = (Element) doc.selectSingleNode("/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/elementProp");
				httpRequestElementPro.addAttribute("guiclass", "HTTPArgumentsPanel");
				httpRequestElementPro.addAttribute("testclass", "Arguments");
				httpRequestElementPro.addAttribute("testname", "User Defined Variables");
				httpRequestElementPro.addAttribute("enabled", "true");
				
				//添加参数节点
				Element httpRequestArgs = (Element) doc.selectSingleNode("/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/elementProp/collectionProp");
				removeChildNodes(httpRequestArgs);  //删除模板中的默认参数
			    System.out.println("remove 结果" + httpRequestArgs);
				
				for(Map.Entry<String, String> entry:paramsMap.entrySet()){
					httpRequestArgs.add(addParamNodes(entry.getKey(), entry.getValue()));
				}
				
			}

			// 替换 http server 参数
			List<Element> httpServerList = doc
					.selectNodes("/jmeterTestPlan/hashTree/hashTree/hashTree/HTTPSamplerProxy/stringProp");
			Iterator<Element> httpServerListIter = httpServerList.iterator();
			while (httpServerListIter.hasNext()) {
				Element httpServer = httpServerListIter.next();
				switch (httpServer.attribute("name").getValue()) {

				case "HTTPSampler.domain":
					httpServer.setText(ip);
					stringBuilder.append("<p>" + "设置 IP地址: \"" + httpServer.getText() + "\"</p>");
					break;

				case "HTTPSampler.port":
					httpServer.setText(port);
					stringBuilder.append("<p>" + "设置 请求端口号: \"" + httpServer.getText() + "\"</p>");
					break;

				case "HTTPSampler.path":
					httpServer.setText(path);
					stringBuilder.append("<p>" + "设置 接口路径: \"" + httpServer.getText() + "\"</p>");
					break;

				case "HTTPSampler.method":
					httpServer.setText(requestMethod.toUpperCase());
					stringBuilder.append("<p>" + "设置 接口请求方法: \"" + httpServer.getText() + "\"</p>");
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
				stringBuilder.append("<p>" + "设置 断言内容: \"" + assertionEle.getText() + "\"</p>");
			}

			// 替换 断言范围 testFiled 参数"
			List<Element> httpRequestAssertionTestFiledList = doc
					.selectNodes("/jmeterTestPlan/hashTree/hashTree/hashTree/ResponseAssertion/stringProp");
			Iterator<Element> httpRequestAssertionTestFiledIter = httpRequestAssertionTestFiledList.iterator();
			while (httpRequestAssertionTestFiledIter.hasNext()) {
				Element assertionEle = httpRequestAssertionTestFiledIter.next();
				assertionEle.setText(testFiled);
				stringBuilder.append("<p>" + "设置 断言范围: \"" + assertionEle.getText() + "\"</p>");
			}

			// 替换 断言规则"
			List<Element> httpRequestAssertionTestTypeList = doc
					.selectNodes("/jmeterTestPlan/hashTree/hashTree/hashTree/ResponseAssertion/intProp");
			Iterator<Element> httpRequestAssertionTestTypeIter = httpRequestAssertionTestTypeList.iterator();
			while (httpRequestAssertionTestTypeIter.hasNext()) {
				Element assertionEle = httpRequestAssertionTestTypeIter.next();
				assertionEle.setText(testType);
				stringBuilder.append("<p>" + "设置 断言规则: \"" + assertionEle.getText() + "\"</p>");
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			format.setExpandEmptyElements(true);
			format.setTrimText(false);
			format.setIndent(false); // 设置是否缩进
			format.setIndent(" "); // 以空格方式实现缩进
			format.setNewlines(false); // 设置是否换行
			format.setSuppressDeclaration(true); 
			format.setNewLineAfterDeclaration(false); //去掉空行
			
			FileWriter fileWriter = new FileWriter(new File(jmxPlan));
			XMLWriter xmlWriter = new XMLWriter(fileWriter, format);
			xmlWriter.write(doc);
			stringBuilder.append("<p>" + "jmxplan 计划文件已生成完毕。" + "</p>");
			stringBuilder.append("<p>" + "文件地址： " + "<a href=\"" + new File(jmxPlan).getAbsolutePath() + "\">"
					+ new File(jmxPlan).getAbsolutePath() + "</p>");
			xmlWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	@SuppressWarnings("unchecked")
	public static void updateNode(Document doc, String xpath, String value) {
		List<Element> list = doc.selectNodes(xpath);
		Iterator<Element> iterator = list.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			element.setText(value);
			stringBuilder.append("<p>" + "设置 断言规则: \"" + element.getText() + "\"</p>");
		}
	}

	public static void deleNodes(List<Element> list) {
		Iterator<Element> iterator = list.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			element.detach();
		}

	}

	@SuppressWarnings("unchecked")
	public static void removeChildNodes(Element element) {
		//Element root = doc.getRootElement();
		//List nodes = root.elements("menu");// 取得某节点下名为"menu"的所有字节点 
		List<Element> nodes = element.elements();
		for (Object obj : nodes) {
			element = (Element) obj;
			element.detach();
		}

	}
	
	public static Element addParamNodes(String name,String value){
		/**
		 * <elementProp name="id" elementType="HTTPArgument">
			<boolProp name="HTTPArgument.always_encode">false</boolProp>
			<stringProp name="Argument.value">1</stringProp>
			<stringProp name="Argument.metadata">=</stringProp>
			<boolProp name="HTTPArgument.use_equals">true</boolProp>
			<stringProp name="Argument.name">id</stringProp>
		</elementProp>
		 */
		//创建节点
		
		// 创建一个父节点 elementProp 元素  
        Element elementProp = DocumentHelper.createElement("elementProp");  
        // include equals
        Element boolPropIncludeEquals = DocumentHelper.createElement("boolProp");  
        // 编码方式   
        Element boolPropEncode = DocumentHelper.createElement("boolProp");  
        // 参数名称  
        Element stringPropName = DocumentHelper.createElement("stringProp");  
        // 参数值  
        Element stringPropValue = DocumentHelper.createElement("stringProp");  
        // metadata  
        Element stringPropMetadata = DocumentHelper.createElement("stringProp"); 
        
        
        boolPropIncludeEquals.addAttribute("name", "HTTPArgument.use_equals");  //添加属性
        boolPropIncludeEquals.setText("true");                                    //添加文本值
        
        boolPropEncode.addAttribute("name", "HTTPArgument.always_encode");  //添加属性
        boolPropEncode.setText("false");                                    //添加文本值
        
        stringPropName.addAttribute("name", "Argument.name");   		 //添加属性
        stringPropName.setText(name);                                    //添加文本值
        
        stringPropValue.addAttribute("name", "Argument.value");   		 //添加属性
        stringPropValue.setText(value);                                  //添加文本值
        
        stringPropMetadata.addAttribute("name", "Argument.metadata");   		 //添加属性
        stringPropMetadata.setText("=");                                  //添加文本值
        
        //为父节点添加属性
        elementProp.addAttribute("name", name);
        elementProp.addAttribute("elementType", "HTTPArgument");
        elementProp.add(stringPropName);
        elementProp.add(stringPropValue);
        elementProp.add(stringPropMetadata);
        elementProp.add(boolPropEncode);
        elementProp.add(boolPropIncludeEquals);
        
        return elementProp;
        
	}

}
