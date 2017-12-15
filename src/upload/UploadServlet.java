package upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("utf-8");
		//支持跨域访问
		resp.setHeader("Access-Control-Allow-Origin", "*");
		//1、创建一个DiskFileItemFactory工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //2、创建一个文件上传解析器  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        //解决上传文件名的中文乱码  
        upload.setHeaderEncoding("UTF-8");   
        factory.setSizeThreshold(1024 * 500);//设置内存的临界值为500K  
        File linshi = new File("E:\\alinshi");//当超过500K的时候，存到一个临时文件夹中  
        if(!linshi.exists()){
        	linshi.mkdir();
        }
        factory.setRepository(linshi);  
        upload.setSizeMax(1024 * 1024 * 5);//设置上传的文件总的大小不能超过5M  
        try {  
            // 1. 得到 FileItem 的集合 items  
            List<FileItem> items = upload.parseRequest(req);  
  
            // 2. 遍历 items:  
            for (FileItem item : items) {  
//                 若是一个一般的表单域, 打印信息  
                if (item.isFormField()) {  
                    String name = item.getFieldName();  
                    String value = item.getString("utf-8");  
                    System.out.println(name + ": " + value);  
                }  
//                 若是文件域则把文件保存到 e:\\files 目录下.  
                else {
                    String fileName = item.getName();  
                    long sizeInBytes = item.getSize();  
                    System.out.println(fileName);  
                    System.out.println(sizeInBytes);  
  
                    InputStream in = item.getInputStream();  
                    byte[] buffer = new byte[1024];  
                    int len = 0;
                    String path = "e:\\files";
                    File file = new File(path);
                    if(!file.exists()){
                    	file.mkdir();
                    }
//                    UploadServlet us = new UploadServlet();
//                    us.generateFilePath(path, fileName);
                    fileName = path + "\\"+ fileName;//文件最终上传的位置  
//                    System.out.println(fileName);  
                    OutputStream out = new FileOutputStream(fileName);  
  
                    while ((len = in.read(buffer)) != -1) {  
                        out.write(buffer, 0, len);  
                    }  
  
                    out.close();  
                    in.close();  
                }  
            }  
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        }  
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
//	public String generateFilePath(String path,String filename){
//       //产生目录结构的算法
//       int dir1=filename.hashCode() & 0x0f;//一级目录名
//       int dir2=filename.hashCode()>>4 & 0x0f;//二级目录名
//       path=path+"\\"+ dir1+"\\"+dir2+"\\";
//       File f=new File(path);
//       if(!f.exists()){
//           f.mkdirs();
//       }
//       return path;
//    }
}
