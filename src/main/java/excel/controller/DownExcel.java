package excel.controller;

import java.io.File;
import java.io.IOException;

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

import excel.server.PathType;

@Controller
public class DownExcel {
	
	@RequestMapping(value="/download") //匹配的是href中的download请求
    public ResponseEntity<byte[]> download(HttpServletRequest request,@RequestParam("filename") String filename,
            Model model) throws IOException{
        String downloadFilePath = PathType.DownDictionary.getPath();//从我们的文件中取
        
        File file = new File(downloadFilePath+File.separator+filename);//新建一个文件
        
        
        HttpHeaders headers = new HttpHeaders();//http头信息
        
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        
        headers.setContentDispositionFormData("attachment", downloadFileName);
        
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
        
    }
    
    @RequestMapping(value="/download2") //匹配的是href中的download请求
    public ResponseEntity<byte[]> downloadZip(HttpServletRequest request,@RequestParam("filename") String filename,
            Model model) throws IOException{
        String downloadFilePath = PathType.ZipPath.getPath();//从我们的文件中取
        
        File file = new File(downloadFilePath+File.separator+filename);//新建一个文件
        
        
        HttpHeaders headers = new HttpHeaders();//http头信息
        
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");//设置编码
        
        headers.setContentDispositionFormData("attachment", downloadFileName);
        
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
        
    }

}
