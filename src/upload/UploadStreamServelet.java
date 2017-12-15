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
		
		//1������һ��DiskFileItemFactory����  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //2������һ���ļ��ϴ�������  
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
					//�����ļ������
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
        //Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
        return UUID.randomUUID().toString() + "_" + filename;
    }
	public static String makePath(String filename,String savePath){
        //�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        //�����µı���Ŀ¼
		String dirString = dir1 + "\\" + dir2;
        String dir = savePath + "\\" + dirString;  //upload\2\3  upload\3\5
        //File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
        File file = new File(dir);
        //���Ŀ¼������
        if(!file.exists()){
            //����Ŀ¼
            file.mkdirs();
        }
        System.out.println(dir);
        return dirString;
    }


}
