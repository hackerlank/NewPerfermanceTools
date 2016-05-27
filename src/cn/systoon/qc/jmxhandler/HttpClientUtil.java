package cn.systoon.qc.jmxhandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientUtil {

	private static StringBuilder stringBuilder = new StringBuilder();
	public static StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	private static Boolean flag = false;
 
	/**
	 * post方式提交表单（模拟用户登录请求）
	 */
	public static boolean postForm(String url,List<NameValuePair> formparams) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
//		formparams = new ArrayList<NameValuePair>();
//		formparams.add(new BasicNameValuePair("username", "admin"));
//		formparams.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if(response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 304){
						flag = true;
					}else{
						flag = false;
					}
					stringBuilder.append(response.getStatusLine());
					stringBuilder.append("--------------------------------------");
					stringBuilder.append("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					stringBuilder.append("--------------------------------------");
				}
			} finally {
				response.close();
			}
	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
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
			stringBuilder.append("请求接口：" + "<br>");
			stringBuilder.append(httppost.getURI());
			stringBuilder.append("<br>");
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					stringBuilder.append("服务响应：" + "<br>");
					stringBuilder.append(response.getStatusLine());
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					stringBuilder.append("<br>");
					stringBuilder.append("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					stringBuilder.append("<br>");
					stringBuilder.append("--------------------------------------");
					if(response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 304){
						flag = true;
						stringBuilder.append("<br>" + "请求成功。");
					}else{
						flag = false;
						stringBuilder.append("<br>" + "请求失败。");
					}
				}
			} finally {
				response.close();
			}
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        System.out.println(stringBuilder.toString());
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
			stringBuilder.append("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if(response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 304){
						flag = true;
					}else{
						flag = false;
					}
					stringBuilder.append(response.getStatusLine());
					stringBuilder.append("--------------------------------------");
					stringBuilder.append("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					stringBuilder.append("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
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