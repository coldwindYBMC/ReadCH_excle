package excel.server.dictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class OptionService {
	
	public static List<File> getDirectory(File file) {
		List<File> list = new ArrayList<>();
		File flist[] = file.listFiles();
		if (flist == null || flist.length == 0) {
			return null;
		}
		for (File f : flist) {
			if (f.isDirectory()) {
				getDirectory(f);
			} else if (f.isFile()) {
				list.add(f);				
			}
		}
		return list;
	}
	
	
}
