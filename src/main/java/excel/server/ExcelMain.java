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
 * ����ҵ��Main
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
			boolean issuccess1 = uploadFileService.upLoad(fileNames, request, PathType.SourcePath); // ����Դ�ļ��ϴ�
			if (issuccess1) {
				screenDictionary.exec();
				return true;
			} else {
				return false;
			}

		case Excel_Dictionarty:
			return uploadFileService.upLoad(fileNames, request, PathType.ExcelAllDictionary); // �����ֵ��ϴ�
		
		case UI_Dictionarty:
			return uploadFileService.upLoad(fileNames, request, PathType.UiAllDictionary); // �����ֵ��ϴ�
			
		case Translate:
			boolean issuccess3 = uploadFileService.upLoad(fileNames, request, PathType.Translate); // ������Ҫ����ı��ϴ�
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
