package cn.systoon.qc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpClientUtils {
	public static JSONObject post1(String url,JSONObject json,Map<String, String> headers){    
        HttpClient client = new DefaultHttpClient();    
        HttpPost post = new HttpPost(url);    
        JSONObject response = null;    
       post.setHeader("Content-Type", "application/json");  
           if (headers != null) {  
               Set<String> keys = headers.keySet();  
               for (Iterator<String> i = keys.iterator(); i.hasNext();) {  
                   String key = (String) i.next();  
                   post.addHeader(key, headers.get(key));  
                     
               }  
           }  
             
          
        try {    
//          json = new JSONObject();  
//          json.put("Email", "testbuyer@buyercompany.com");;  
//          json.put("Password", "123456");  
            StringEntity s = new StringEntity(json.toString(),"utf-8");    
            s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));  
            //s.setContentType("application/json");    
            //s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));   
            post.setEntity(s);    
                 
            // HttpResponse res = client.execute(post);    
           HttpResponse httpResponse = client.execute(post);  
           InputStream inStream =     httpResponse.getEntity().getContent();  
           BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));    
           StringBuilder strber = new StringBuilder();    

           String line = null;    
           while ((line = reader.readLine()) != null)     
               strber.append(line + "\n");    
           inStream.close();    
//           Log.i("MobilpriseActivity", strber.toString());  
             if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){    
                 HttpEntity entity = httpResponse.getEntity();    
                 String charset = EntityUtils.getContentCharSet(entity);    
               //  response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));    
             }    
         } catch (Exception e) {    
             throw new RuntimeException(e);    
         }    
         return response;    
     }    
	
    /** 
     * get  
     * @param url 
     * @param headerKey 
     * @param headerVaue 
     */  
    @SuppressWarnings("deprecation")
	private void getHttp(String url,Map<String, String> headers){  
        HttpGet httpGet = new HttpGet(url);   
            
        if (headers != null) {  
            Set<String> keys = headers.keySet();  
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {  
                String key = (String) i.next();  
                httpGet.addHeader(key, headers.get(key));  
            }  
        }  
          
          
        HttpParams httpParameters = new BasicHttpParams();    
        HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);    
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);    
      
        HttpClient httpclient = new DefaultHttpClient(httpParameters);   
        try {  
            HttpResponse httpResponse = httpclient.execute(httpGet);  
            InputStream inStream =     httpResponse.getEntity().getContent();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));    
            StringBuilder strber = new StringBuilder();    
            String line = null;    
            while ((line = reader.readLine()) != null)     
                strber.append(line + "\n");    
            inStream.close();    
            Log.i("MobilpriseActivity", strber.toString());  
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {    
                Log.i("MobilpriseActivity", "success");  
            }     
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }    
    }  
    
    /** 
     *  
     * @param url the web will be connected  
     * @param headers  
     * @param parmas data will be sent 
     */  
    private void postHttp(String url,Map<String, String> headers,Map<String, String> parmas){  
        HttpPost httpPost = new HttpPost(url);    
  
        if (headers != null) {  
            Set<String> keys = headers.keySet();  
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {  
                String key = (String) i.next();  
                httpPost.addHeader(key, headers.get(key));  
            }  
        }  
        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();  
        if (parmas != null) {  
            Set<String> keys = parmas.keySet();  
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {  
                String key = (String) i.next();  
                pairs.add(new BasicNameValuePair(key, parmas.get(key)));  
            }  
        }  
  
        HttpParams httpParameters = new BasicHttpParams();    
        HttpConnectionParams.setConnectionTimeout(httpParameters,    
                timeoutConnection);    
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);    
  
        HttpClient httpclient = new DefaultHttpClient(httpParameters);   
  
  
        try {  
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8")); //   
            HttpResponse httpResponse = httpclient.execute(httpPost);  
            InputStream inStream =     httpResponse.getEntity().getContent();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));    
            StringBuilder strber = new StringBuilder();    
            String line = null;    
            while ((line = reader.readLine()) != null)     
                strber.append(line + "\n");    
            inStream.close();    
            Log.i("MobilpriseActivity", strber.toString());  
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {    
                Log.i("MobilpriseActivity", "success");  
              
  
            }    
  
  
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
    
    public static JSONObject post(String url,JSONObject json,Map<String, String> headers){    
        HttpClient client = new DefaultHttpClient();    
        HttpPost post = new HttpPost(url);    
        JSONObject response = null;    
      // post.setHeader("Content-Type", "application/json");  
           if (headers != null) {  
               Set<String> keys = headers.keySet();  
               for (Iterator<String> i = keys.iterator(); i.hasNext();) {  
                   String key = (String) i.next();  
                   post.addHeader(key, headers.get(key));  
                     
               }  
           }  
             
          
        try {    
            json = new JSONObject();  
            json.put("Email", "testbuyer@buyercompany.com");;  
            json.put("Password", "123456");  
            StringEntity s = new StringEntity(json.toString(),"utf-8");    
          //  s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));  
            s.setContentEncoding("HTTP.UTF_8");  
            //s.setContentType("application/json");    
            s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));   
            post.setEntity(s);    
                 
            // HttpResponse res = client.execute(post);    
           HttpResponse httpResponse = client.execute(post);  
           InputStream inStream =     httpResponse.getEntity().getContent();  
           BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));    
           StringBuilder strber = new StringBuilder();    
           String line = null;    
           while ((line = reader.readLine()) != null)     
               strber.append(line + "\n");    
           inStream.close();    
           Log.i("MobilpriseActivity", strber.toString());  
             if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){    
                 HttpEntity entity = httpResponse.getEntity();    
                 String charset = EntityUtils.getContentCharSet(entity);    
               //  response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));    
             }    
         } catch (Exception e) {    
             throw new RuntimeException(e);    
         }    
         return response;    
     }   
}

