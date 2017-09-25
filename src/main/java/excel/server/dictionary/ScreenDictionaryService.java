package excel.server.dictionary;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Service;

import excel.server.PathType;
@Service
public class ScreenDictionaryService {
	private static SelectChineseList selectChineseList;
	private static CreateDictionary createDictionary;
	private static GetDictionaryMap getDictionaryMap;
	private static Map<String,String>map;
	
	public  void exec() {
		selectChineseList = new SelectChineseList();
		createDictionary = new CreateDictionary();
		getDictionaryMap = new GetDictionaryMap();
		map = getDictionaryMap.exce();
		File file = new File(PathType.SourcePath.getPath());
		getDirectory(file);
	}
	
	// µÝ¹é±éÀú
	 private static void getDirectory(File file) {
	  File flist[] = file.listFiles();
	  if (flist == null || flist.length == 0) {
	      return ;
	  }
	  for (File f : flist) {
	      if (f.isDirectory()) {
	          getDirectory(f);
	      } else if(f.isFile()) {
	    	  selectChineseList.readExcel(f.getAbsoluteFile());
	      }
	  }
	  createDictionary.writeExcel(selectChineseList.list,map);
	}
}
