package cn.systoon.qc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PerformServlet
 */
@WebServlet("/performServlet")
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PerformServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName=request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test");
		PrintWriter out = response.getWriter();
		out.print("hello ,this is test");
	}
	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save");
		PrintWriter out = response.getWriter();
		out.print("hello ,this is save");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
