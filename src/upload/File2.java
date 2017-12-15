package upload;

import java.io.File;

public class File2 {
	static int i = 0;
	public static void main(String[] args) {
		File file = new File("e:\\files");
		String[] list = file.list();
		for (String f : list) {
			i++;
			System.out.println(f);
			int lastIndexOf = f.lastIndexOf(".");
			System.out.println(lastIndexOf);
			f = f.substring(0,lastIndexOf)+"_"+i+f.substring(lastIndexOf);
			
		}
	}
}
