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
import excel.server.FormType;
import excel.server.dictionary.GetDictionaryMap;

@Controller
public class SelectExcel {
	@Autowired
	private ExcelMain excelMain;
	@Autowired
	private GetDictionaryMap getDictionaryMap;
	@RequestMapping("/excel")
	public String excel(Model model) {
		return "excelselect";
	}
	@RequestMapping("/ui")
	public String ui(Model model) {
		return "uiselect";
	}
	@RequestMapping(value = "upload")
	public synchronized String UploadSourceFile(MultipartHttpServletRequest request) throws IOException {
		
		Iterator<String> fileNames = request.getFileNames();
		boolean isSuccess =  excelMain.exec(fileNames,request,FormType.Source); //处理,生成需要翻译的字段
		if(isSuccess){
			return "downdictionary";
		} else{
			return "error";
		}
	}
	@ResponseBody
	@RequestMapping(value = "uploaddic")
	public synchronized String UploadDictionaryFile(MultipartHttpServletRequest request) throws IOException {
		Iterator<String> fileNames = request.getFileNames();
		boolean isSuccess =  excelMain.exec(fileNames,request,FormType.Dictionarty); //上传字典表
		if(isSuccess){
			getDictionaryMap.exce();
			return "success!,Make sure the name of the dictionary file is : dictionary_excel.xls";
		} else{
			return "error";
		}
	}
	
	@RequestMapping(value = "uploadtran")
	public String UploadTranFile(MultipartHttpServletRequest request) throws IOException {
		Iterator<String> fileNames = request.getFileNames();
		boolean isSuccess =  excelMain.exec(fileNames,request,FormType.Translate); //处理翻译文件
		if(isSuccess){
			return "downzip";
		} else{
			return "error";
		}
	}
}
