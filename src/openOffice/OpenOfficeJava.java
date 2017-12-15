package openOffice;

import java.io.File;
import java.io.FileNotFoundException;

import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * return -1��ʾ�Ҳ���Դ�ļ�����url.properties���ô���
 * 0 �����ɹ�
 * 1 ��ʾת��ʧ��
 * @author wenjie.wang
 *
 */
public class OpenOfficeJava {
	
	//sourceFile ʾ��: F:\\office\\source.doc destFile ʾ��: F:\\pdf\\dest.pdf 
	public static int officeToPDF(String sourceFile,String destFile){
		try {
			File inputFile = new File(sourceFile);
			if(!inputFile.exists()){
				return -1;
			}
			//���Ŀ��·�������ڣ����½�·��
			File outputFile = new File(destFile);
			if(!outputFile.getParentFile().exists()){
				outputFile.getParentFile().mkdir();
			}
			//openOffice�İ�װĿ¼
			String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
			if(OpenOffice_HOME.charAt(OpenOffice_HOME.length()-1)!='\\'){
				OpenOffice_HOME = OpenOffice_HOME + "\\";
			}
			//����openOffice����
			String command = OpenOffice_HOME +"program\\soffice.exe -headless -accept" +
					"=\"socket,host=127.0.0.1,port=8100;urp;\"";
			Process pro = Runtime.getRuntime().exec(command);
			
			SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
			connection.connect();
			
			//covert
			OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
			
			//�ر�����
			connection.disconnect();
			//�ر�openoffice����
			pro.destroy();
			System.out.println("=== ת����� ===");
			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
		
	}
}
