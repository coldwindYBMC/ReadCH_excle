package excel.server.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.Util.Util;
import excel.server.CreateZipService;
import excel.server.PathType;

@Service
public class MergeExcelService {
	@Autowired
	private MergeExcel mergeExcel;
	@Autowired
	private GetDictionaryMap dictionaryMap;
	private static CreateZipService createZipService;
	private static Map<String, String> map;

	public void exce() throws FileNotFoundException {
		createZipService = new CreateZipService(PathType.ZipPath.getPath() + "translate.zip");
		map = dictionaryMap.exce();
		File finishFile = new File(PathType.FinishTranslate.getPath());
		Util.deleteDir(finishFile); // 清空,完成翻译的表
		File file = new File(PathType.Translate.getPath());
		getDirectory(file);// 搜索文件翻译
		createZipService.compressDirectory(new File(PathType.FinishTranslate.getPath()));
	}

	// 递归遍历
	private void getDirectory(File file) {
		File flist[] = file.listFiles();
		if (flist == null || flist.length == 0) {
			return;
		}
		for (File f : flist) {
			if (f.isDirectory()) {
				getDirectory(f);
			} else if (f.isFile()) {
				mergeExcel.readfile(f, map);
			}
		}
	}

}
