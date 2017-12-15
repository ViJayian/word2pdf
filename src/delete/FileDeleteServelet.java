package delete;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDeleteServelet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String name = new String(req.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		//删除下载文件
		File file = new File("e:\\files\\"+name);
		//TODO 删除预览文件 
		if(file.exists()){
			boolean delete = file.delete();
			if(delete){
				System.out.println("删除成功");
			}
		}else{
			System.out.println("文件不存在");
		}
	}
	
}
