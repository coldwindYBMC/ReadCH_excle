package excel.controller;

import java.io.IOException;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import excel.server.ExcelMain;
import excel.server.dictionary.CreateDictionary;
import excel.server.dictionary.GetDictionaryMap;
import excel.server.payhenum.FormType;

@Controller
public class SelectExcel {
	@Autowired
	private ExcelMain excelMain;
	@Autowired
	private GetDictionaryMap getDictionaryMap;
	@Autowired
	private CreateDictionary createDictionary;

	@RequestMapping("/excel")
	public String excel(Model model) {
		return "excelselect";
	}

	@RequestMapping("/ui")
	public String ui(Model model) {
		return "uiselect";
	}

	@RequestMapping(value = "upload")
	public synchronized String UploadSourceFile(MultipartHttpServletRequest request, String version)
			throws IOException {

		Iterator<String> fileNames = request.getFileNames();
		getDictionaryMap.setVersion(version);
		createDictionary.setVersion(version);

		boolean isSuccess = excelMain.exec(fileNames, request, FormType.Source); // 处理,生成需要翻译的字段
		if (isSuccess) {
			switch (version) {
			case "excel":
				return "downdictionaryExcel";
			case "ui":
				return "downdictionaryUI";
			default:
				return "error";
			}
		} else {
			return "error";
		}
	}

	@ResponseBody
	@RequestMapping(value = "uploaddic")
	public synchronized String UploadDictionaryFile(MultipartHttpServletRequest request, String version)
			throws IOException {
		Iterator<String> fileNames = request.getFileNames();
		boolean isSuccess = false;
		if (version.equals("excel")) {
			isSuccess = excelMain.exec(fileNames, request, FormType.Excel_Dictionarty); // 上传字典表
		} else if (version.equals("ui")) {
			isSuccess = excelMain.exec(fileNames, request, FormType.UI_Dictionarty); // 上传字典表
		}
		if (isSuccess) {
			return "success!,Make sure the name of the dictionary file is : ****dictionary.xls";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "uploadtran")
	public String UploadTranFile(MultipartHttpServletRequest request, String version) throws IOException {
		Iterator<String> fileNames = request.getFileNames();
		getDictionaryMap.setVersion(version);
		boolean isSuccess = excelMain.exec(fileNames, request, FormType.Translate); // 处理翻译文件
		if (isSuccess) {
			return "downzip";
		} else {
			return "error";
		}
	}
}
