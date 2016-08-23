package cn.systoon.qc.shell;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecuteShell {

	/**     待修改
	 * logFile 参数的处理方式，需移动到 PrintLog中
	 * 
	 * @param shellCommand
	 * @param executeShellLogFile
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public Process executeShell(String shellCommand) throws IOException {
		System.out.println("shellCommand:" + shellCommand);

		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");
		StringBuffer stringBuffer = null;
		Process pid = null;

		String args = dateFormat.format(new Date()) + "准备执行Shell命令 " + shellCommand + " \r\n";
		System.out.println(args);
		
		//执行shell命令
		try {

			String[] cmd = { "/bin/sh", "-c", shellCommand };
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			if (pid != null) {
				System.out.println("进程号：" + pid);
			} else {
				System.out.println("没有pid\r\n");
			}

		} catch (Exception ioe) {
			stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage()).append("\r\n");
		} finally {
			
		
		}
		return pid;
	}

}
