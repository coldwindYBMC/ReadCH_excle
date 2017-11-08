package excel.server.dictionary.merage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.Util.Util;
import excel.server.CreateZipService;
import excel.server.dictionary.GetDictionaryMap;
import excel.server.dictionary.OptionService;
import excel.server.payhenum.PathType;

@Service
public class MergeExcelService extends OptionService{
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
		getDirectory(file).forEach(f->{
			mergeExcel.readfile(f, map);
		});
		createZipService.compressDirectory(new File(PathType.FinishTranslate.getPath()));
	}
}
