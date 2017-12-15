package upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadStreamServelet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//1、创建一个DiskFileItemFactory工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //2、创建一个文件上传解析器  
		ServletFileUpload upload = new ServletFileUpload(factory);  
		upload.setHeaderEncoding("UTF-8");
		String fname = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
		System.out.println(fname);
		String beginIndex = new String(request.getParameter("index").getBytes("iso-8859-1"), "utf-8");
		System.out.println(beginIndex);
		upload.setSizeMax(1024*1024*30);
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if(item.isFormField()){
					
				}else{
					String fileName = item.getName();
					System.out.println(fileName);
					if(fileName.equals("blob")){
						fileName = fname;
					}
					if(fileName==null || fileName.trim().equals("")){
						continue;
					}
					InputStream in = item.getInputStream();
					String saveFileName = fileName;
                    String realSavePath = "E:\\files";
//					System.out.println(saveFileName);
//					String realSavePath = "E:\\files\\"+saveFileName;
					//创建文件输出流
					FileOutputStream out = null;
					if(beginIndex.equals("0")){
						out = new FileOutputStream(realSavePath+"\\"+saveFileName,false);
					}else{
						out = new FileOutputStream(realSavePath+"\\"+saveFileName,true);
					}
					byte buffer[] = new byte[1024];
					int len = 0;
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
					}
					in.close();
					out.close();
					item.delete();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	private String makeFileName(String filename){  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }
	public static String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        //构造新的保存目录
		String dirString = dir1 + "\\" + dir2;
        String dir = savePath + "\\" + dirString;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        System.out.println(dir);
        return dirString;
    }


}
