package download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setContentType("utf-8");
		//��ȡ�ļ�����
//		String fileName = request.getParameter("name");
		String fileName = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
		System.out.println("name: "+fileName);
		//��ȡ�ļ��ϴ�Ŀ¼
		File file = new File("e:\\files\\"+fileName);
		//�ļ�������
		if(!file.exists()){
			System.out.println("�ļ�������");
		}else{
			//������Ӧ����	application/octet-stream
//			response.setContentType("application/x-msdownload;charset=UTF-8");
//			response.setContentType("application/octet-stream");
			response.setContentType(getServletContext().getMimeType(fileName));
			//����ͷ��Ϣ
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			System.out.println(response);
			byte b[] = new byte[1024];
			int n;
			while((n=in.read(b))!=-1){
				out.write(b,0,n);
			}
			//�ر���
			out.close();
			in.close();
			
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
