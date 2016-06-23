package cn.systoon.qc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestResponseServlet
 */
@WebServlet("/testResponseServlet")
public class TestResponseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public TestResponseServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
//		response.getWriter().println("this is the second line");
//		response.getWriter().println("<br>");
//		response.getWriter().println("this is the third line");
//		response.getWriter().println("<br>");
//		response.getWriter().println("this is the first line");
//		response.getWriter().println("请求信息：" + "<br>");
//		response.getWriter().println("请求url：");
//		response.getWriter().println("请求参数：");
//		response.getWriter().println("请求parameter：");
//		response.getWriter().println("");
//		response.getWriter().println("请求结果：");
		
		response.getWriter().println(request.getParameter("a"));
		response.getWriter().println(request.getParameter("b"));
		response.getWriter().println(request.getParameter("c"));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
