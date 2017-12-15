package view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openOffice.OpenOfficeJava;

public class ViewServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		//获取请求filename
		String fileName = new String(req.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		System.out.println(fileName);
		//截掉后缀 拼接.pdf
		String pdfFileName = (fileName.substring(0, fileName.lastIndexOf("."))).hashCode()+".pdf";
		System.out.println(pdfFileName);
		//转为地址栏编码
		//输出到webapps下
		String path = "E:\\wenjie.wang\\tomcat\\apache-tomcat-7.0.53\\webapps\\generic\\fileviews\\"+pdfFileName;
		File sourceFile = new File(path);
		if(!sourceFile.exists()){
			OpenOfficeJava.officeToPDF("e:\\files\\"+fileName, path);
		}
		resp.getWriter().write(pdfFileName);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
