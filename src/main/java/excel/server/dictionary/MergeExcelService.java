package excel.server.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.stereotype.Service;

import excel.Util.Util;
import excel.server.CreateZipService;
import excel.server.PathType;

@Service
public class MergeExcelService {
	
	private static MergeExcel mergeExcel;
	private static CreateZipService createZipService;
	private static GetDictionaryMap dictionaryMap;
	private static Map<String,String>map;

	public void exce() throws FileNotFoundException {
		mergeExcel = new MergeExcel();
		createZipService = new CreateZipService(PathType.ZipPath.getPath()+"translate.zip");
		dictionaryMap = new GetDictionaryMap();
		map = dictionaryMap.exce();
		File finishFile = new File(PathType.FinishTranslate.getPath());
		Util.deleteDir(finishFile);  //清空,完成翻译的表
		File file = new File(PathType.Translate.getPath());
		getDirectory(file);
		createZipService.compressDirectory(new File(PathType.FinishTranslate.getPath()));
	}
	
	// 递归遍历
		 private static void getDirectory(File file) {
		  File flist[] = file.listFiles();
		  if (flist == null || flist.length == 0) {
		      return ;
		  }
		  for (File f : flist) {
		      if (f.isDirectory()) {
		          getDirectory(f);
		      } else if(f.isFile()) {	    	 
		    	 mergeExcel.readExcel(f, map);
		      }
		  }
		}

}
