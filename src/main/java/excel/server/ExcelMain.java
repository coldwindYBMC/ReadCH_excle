package excel.server;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import excel.server.dictionary.merage.MergeExcelService;
import excel.server.dictionary.sereen.ScreenDictionaryService;
import excel.server.payhenum.FormType;
import excel.server.payhenum.PathType;

/**
 * 处理业务Main
 */
@Service
public class ExcelMain {
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private ScreenDictionaryService screenDictionary;
	@Autowired
	private MergeExcelService mergeExcelService;

	public boolean exec(Iterator<String> fileNames, MultipartHttpServletRequest request, FormType type) {
		switch (type) {
		case Source:
			boolean issuccess1 = uploadFileService.upLoad(fileNames, request, PathType.SourcePath); // 处理源文件上传
			if (issuccess1) {
				screenDictionary.exec();
				return true;
			} else {
				return false;
			}

		case Excel_Dictionarty:
			return uploadFileService.upLoad(fileNames, request, PathType.ExcelAllDictionary); // 处理字典上传
		
		case UI_Dictionarty:
			return uploadFileService.upLoad(fileNames, request, PathType.UiAllDictionary); // 处理字典上传
			
		case Translate:
			boolean issuccess3 = uploadFileService.upLoad(fileNames, request, PathType.Translate); // 处理需要翻译的表上传
			if (issuccess3) {
				try {
					mergeExcelService.exce();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}

	}

}
