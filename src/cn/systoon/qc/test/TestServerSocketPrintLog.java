package cn.systoon.qc.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.junit.Test;

public class TestServerSocketPrintLog {

	@Test
	public void testPrintLog() {
		Socket socket = null;
		InputStream is = null;
		try {
			socket = new Socket("127.0.0.1", 9090);
			is = socket.getInputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while((len = is.read(b)) != -1){
				String str = new String(b,0,len);
				System.out.println(str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
