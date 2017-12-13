package excel.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import excel.server.dictionary.OptionService;
import excel.server.payhenum.PathType;

@Controller
public class DownExcel {
	
	@RequestMapping(value="/download") //ƥ�����href�е�download����
    public ResponseEntity<byte[]> download(HttpServletRequest request,@RequestParam("filename") String filename,
            Model model) throws IOException{
        String downloadFilePath = PathType.DownDictionary.getPath();//�����ǵ��ļ���ȡ
        
        File file = new File(downloadFilePath+File.separator+filename);//�½�һ���ļ�
        
        
        HttpHeaders headers = new HttpHeaders();//httpͷ��Ϣ
        
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//���ñ���
        
        headers.setContentDispositionFormData("attachment", downloadFileName);
        
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        //MediaType:������ý������  contentType�����������е�ý��������Ϣ
        

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
        
    }
    
    @RequestMapping(value="/download2") //ƥ�����href�е�download����
    public ResponseEntity<byte[]> downloadZip(HttpServletRequest request,@RequestParam("filename") String filename,
            Model model) throws IOException{
        String downloadFilePath = PathType.ZipPath.getPath();//�����ǵ��ļ���ȡ
        
        File file = new File(downloadFilePath+File.separator+filename);//�½�һ���ļ�
        
        
        HttpHeaders headers = new HttpHeaders();//httpͷ��Ϣ
        
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//���ñ���
        
        headers.setContentDispositionFormData("attachment", downloadFileName);
        
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        //MediaType:������ý������  contentType�����������е�ý��������Ϣ
        
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
        
    }
    @RequestMapping(value="/downdir") //ƥ�����href�е�download����
    public ResponseEntity<byte[]> downloaDirZip(HttpServletRequest request,String version) throws IOException{
    	 String downloadFilePath,filename;
    	if(version.equals("ui")){
    		downloadFilePath  = PathType.UiAllDictionary.getPath();//�����ǵ��ļ���ȡ
    		File f = new File(downloadFilePath);
    		filename = OptionService.getDirectory(f).get(0).getName();
    		
    	} else{
    		downloadFilePath  = PathType.ExcelAllDictionary.getPath();//�����ǵ��ļ���ȡ
    		File f = new File(downloadFilePath);
    		filename = OptionService.getDirectory(f).get(0).getName();
    	}
     
        
        File file = new File(downloadFilePath+File.separator+filename);//�½�һ���ļ�
        
        
        HttpHeaders headers = new HttpHeaders();//httpͷ��Ϣ
        
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//���ñ���
        
        headers.setContentDispositionFormData("attachment", downloadFileName);
        
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        //MediaType:������ý������  contentType�����������е�ý��������Ϣ
        
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
        
    }
}
