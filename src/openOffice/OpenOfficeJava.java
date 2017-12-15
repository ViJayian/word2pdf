package openOffice;

import java.io.File;
import java.io.FileNotFoundException;

import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * return -1表示找不到源文件，或url.properties配置错误
 * 0 操作成功
 * 1 表示转换失败
 * @author wenjie.wang
 *
 */
public class OpenOfficeJava {
	
	//sourceFile 示例: F:\\office\\source.doc destFile 示例: F:\\pdf\\dest.pdf 
	public static int officeToPDF(String sourceFile,String destFile){
		try {
			File inputFile = new File(sourceFile);
			if(!inputFile.exists()){
				return -1;
			}
			//如果目标路径不存在，则新建路径
			File outputFile = new File(destFile);
			if(!outputFile.getParentFile().exists()){
				outputFile.getParentFile().mkdir();
			}
			//openOffice的安装目录
			String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
			if(OpenOffice_HOME.charAt(OpenOffice_HOME.length()-1)!='\\'){
				OpenOffice_HOME = OpenOffice_HOME + "\\";
			}
			//启动openOffice服务
			String command = OpenOffice_HOME +"program\\soffice.exe -headless -accept" +
					"=\"socket,host=127.0.0.1,port=8100;urp;\"";
			Process pro = Runtime.getRuntime().exec(command);
			
			SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
			connection.connect();
			
			//covert
			OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
			
			//关闭连接
			connection.disconnect();
			//关闭openoffice进程
			pro.destroy();
			System.out.println("=== 转换完成 ===");
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
