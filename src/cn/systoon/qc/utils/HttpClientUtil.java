package cn.systoon.qc.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private static Boolean flag = false; //请求结果标志位，用于“保存”时验证接口请求结果
	
	private static StringBuilder stringBuilder = new StringBuilder();  //生成返回信息
	public static StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	/** 
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
     * 请求参数是Json串格式
     */ 
    public static boolean postJson(String url,String params) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        try {  
	        StringEntity s = new StringEntity(params);
	        s.setContentEncoding("UTF-8");
	        s.setContentType("application/json");//发送json数据需要设置contentType
            httppost.setEntity(s);  
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append("<p>" + "请求地址：" + "</p>" + "<br>");
			stringBuilder.append("<p>" + httppost.getURI() + "</p>" + "<br>");
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					stringBuilder.append("<p>" + "服务响应："  + "</p>" + "<br>");
					stringBuilder.append("<p>" + response.getStatusLine() + "</p>" );
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					stringBuilder.append("<br>");
					stringBuilder.append("<p>" + "Response content: <br>"  + "</p>" + "<textarea id=\"content\">" + EntityUtils.toString(entity, "UTF-8") + "</textarea>");
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					if(response.getStatusLine().getStatusCode() >= 200 || response.getStatusLine().getStatusCode() < 304){
						flag = true;
					}else{
						flag = false;
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			flag = false;
			stringBuilder.append("--------------------------------------" + "<br>");
			stringBuilder.append("<p>" + "请求失败" + "</p>" + "<br>");
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;  
    }

	/**
	 * 发送 get请求
	 * @return 
	 */
	public static boolean get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append("<p>" + "请求地址 " + httpget.getURI() + "</p>");
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					stringBuilder.append("<p>" + "服务响应：" + "</p>" + "<br>");
					stringBuilder.append("<p>" + response.getStatusLine() + "</p>");
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					stringBuilder.append("<br>");
					stringBuilder.append("<p>" + "Response content: <br>"  + "</p>" + "<textarea id=\"content\">" + EntityUtils.toString(entity, "UTF-8") + "</textarea>");
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					if(response.getStatusLine().getStatusCode() >= 200 || response.getStatusLine().getStatusCode() < 304){
						flag = true;
					}else{
						flag = false;
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			flag = false;
			stringBuilder.append("--------------------------------------" + "<br>");
			stringBuilder.append("<p>" + "请求失败" + "</p>" + "<br>");
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}