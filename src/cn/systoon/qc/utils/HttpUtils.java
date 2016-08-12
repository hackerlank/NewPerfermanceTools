package cn.systoon.qc.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
 
/**
 * 基于 httpclient 4.5.1版本的 https工具类
 * 
 *	@author zhengAh
 */
@SuppressWarnings("unused")
public class HttpUtils {
    private static CloseableHttpClient httpClient = null;
    private static HttpClientContext httpContext = HttpClientContext.create();
    private static final String CHARSET = "utf-8";
    private static final int timeout = 5000; //ms
	
	public static CloseableHttpClient getIgnoreSslCertificateHttpClient() throws HttpException {

		SSLContext sslContext = null;
		try {
		  sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

		    @Override
		    public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
		      throws CertificateException {

		      return true;
		    }
		  }).build();
		} catch (Exception e) {
		  throw new HttpException("can not create http client.", e);
		}
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
		  new NoopHostnameVerifier());
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
		  .<ConnectionSocketFactory> create()
		  .register("http", PlainConnectionSocketFactory.getSocketFactory())
		  .register("https", sslSocketFactory).build();
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		return HttpClientBuilder.create().setSslcontext(sslContext).setConnectionManager(connMgr)
		  .build();
		}
 

//    private static  final BasicCookieStore cookieStore  ;
    
	
	
    static {	
        //配置请求的超时设置  
        RequestConfig requestConfig = RequestConfig.custom()    
                .setConnectionRequestTimeout(timeout)  
                .setConnectTimeout(timeout)    
                .setSocketTimeout(timeout).build();    
       
			try {
				httpClient = getIgnoreSslCertificateHttpClient();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    }
 
 
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return   返回内容
     */
    public static String doGetForm(String url, Map<String,String> params, String charset){
    	String result = "";
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
      
    }
    /**
     * get 一个给好的url
     * @param url  http://xxx.com?xx=a&xx=c
     * @param params
     * @param charset
     * @return
     */
    public static String doGetStrReq(String url,String charset){
    	String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
      
    }
    /**
     * post form请求形式
     * @param url
     * @param postHeader
     * @param params
     * @param charset
     * @return
     */
    public static List<Object> doPostFormReq(String url,Map<String, String> postHeader, Map<String,String> params,String charset){
    	List<Object> result = new ArrayList<Object>();
        try {
        	 
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            for(Map.Entry<String,String> entry : postHeader.entrySet()){
             	String key = entry.getKey();
             	String value = entry.getValue();
             	httpPost.addHeader(key, value);
             }
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,charset));
            }
            
            //打印请求信息
            printPostRequestInfo(httpPost);
            
            Date start_date=new Date();
            System.out.println(start_date);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            Date end_date=new Date();
            System.out.println(end_date);
            int statusCode = response.getStatusLine().getStatusCode();
            result.add(statusCode);
            if (statusCode != 200) {
//                httpPost.abort();
//                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            	return result;
            }
            
            System.out.println("请求耗时：" + (end_date.getTime()-start_date.getTime()));
            
            HttpEntity entity = response.getEntity();
            	
            if (entity != null){
                result.add(EntityUtils.toString(entity, CHARSET));
            }
            result.add(end_date.getTime()-start_date.getTime());
            EntityUtils.consume(entity);
            response.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * post 请求为str 形式 有header
     * @param url
     * @param postHeader
     * @param strRequest
     * @param charset
     * @return
     */
    public static String doPostStringReq(String url, Map<String, String> postHeader, String strRequest, String charset){
    	String result = "";
        try {
        	HttpPost httpPost = new HttpPost(url);

            StringEntity strEntity = new StringEntity(strRequest, charset);
        
            for(Map.Entry<String,String> entry : postHeader.entrySet()){
            	String key = entry.getKey();
            	String value = entry.getValue();
            	httpPost.addHeader(key, value);
            }
            httpPost.setEntity(strEntity);
            
            //打印请求信息
            printPostRequestInfo(httpPost);
            
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                //httpPost.abort();
                return "HttpClient,error status code :" + statusCode;
                //throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * post 请求为str 没有header
     * @param url
     * @param strRequest
     * @param charset
     * @return
     */
    
    
    public static String doPostStringReq(String url, String strRequest, String charset){
    	String result = "";
        try {
        	
			
			
        	HttpPost httpPost = new HttpPost(url);
            StringEntity strEntity = new StringEntity(strRequest, charset);
          
            
            
            httpPost.setEntity(strEntity);
            
          //打印请求信息
            printPostRequestInfo(httpPost);
        

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    } 
    
    /**
     * 打印请求信息
     * @param httpPost
     */
    public static void printPostRequestInfo(HttpPost httpPost){
    	String result = null;
        
    	//打印请求首行
    	System.out.println("request request:\n" + httpPost.getRequestLine());
        //打印请求头 
        Header[] hs = httpPost.getAllHeaders();
        for(Header header:hs){
        	System.out.println(header.toString());
        }
        
        //打印请求实体
        HttpEntity requestEntity = httpPost.getEntity();
        try {
			result = EntityUtils.toString(requestEntity, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
       
        System.out.println(result);
    }
}