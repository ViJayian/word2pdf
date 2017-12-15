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
		//֧�ֿ������
		resp.setHeader("Access-Control-Allow-Origin", "*");
		//1������һ��DiskFileItemFactory����  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //2������һ���ļ��ϴ�������  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        //����ϴ��ļ�������������  
        upload.setHeaderEncoding("UTF-8");   
        factory.setSizeThreshold(1024 * 500);//�����ڴ���ٽ�ֵΪ500K  
        File linshi = new File("E:\\alinshi");//������500K��ʱ�򣬴浽һ����ʱ�ļ�����  
        if(!linshi.exists()){
        	linshi.mkdir();
        }
        factory.setRepository(linshi);  
        upload.setSizeMax(1024 * 1024 * 5);//�����ϴ����ļ��ܵĴ�С���ܳ���5M  
        try {  
            // 1. �õ� FileItem �ļ��� items  
            List<FileItem> items = upload.parseRequest(req);  
  
            // 2. ���� items:  
            for (FileItem item : items) {  
//                 ����һ��һ��ı���, ��ӡ��Ϣ  
                if (item.isFormField()) {  
                    String name = item.getFieldName();  
                    String value = item.getString("utf-8");  
                    System.out.println(name + ": " + value);  
                }  
//                 �����ļ�������ļ����浽 e:\\files Ŀ¼��.  
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
                    fileName = path + "\\"+ fileName;//�ļ������ϴ���λ��  
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
//       //����Ŀ¼�ṹ���㷨
//       int dir1=filename.hashCode() & 0x0f;//һ��Ŀ¼��
//       int dir2=filename.hashCode()>>4 & 0x0f;//����Ŀ¼��
//       path=path+"\\"+ dir1+"\\"+dir2+"\\";
//       File f=new File(path);
//       if(!f.exists()){
//           f.mkdirs();
//       }
//       return path;
//    }
}
