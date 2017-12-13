package excel.server;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import excel.Util.Util;
import excel.server.payhenum.PathType;

/**
 * 保存上传的文件，到特定目录
 * 
 * **/
@Service
public class UploadFileService {
	private final String[]  allowTypes = {".xls",".hluiproj"};
	public boolean upLoad(Iterator<String> fileNames, MultipartHttpServletRequest request, PathType path){
		
		// 检查该路径对应的目录是否存在. 如果不存在则创建目录
		File dir = new File(path.getPath());
		Util.deleteDir(dir);
		
		while (fileNames.hasNext()) {
			// 把fileNames集合中的值打出来
			//MultiValueMap<String, MultipartFile>
			String fileName = fileNames.next();
			System.out.println("####:"+fileName);
			/*
			 * request.getFiles(fileName)方法即通过fileName这个Key, 得到对应的文件 集合列表.
			 * 只是在这个Map中, 文件被包装成MultipartFile类型
			 */
			List<MultipartFile> fileList = request.getFiles(fileName);

			if (fileList.size() > 0) {
				// 遍历文件列表
				Iterator<MultipartFile> fileIte = fileList.iterator();

				while (fileIte.hasNext()) {
					// 获得每一个文件
					MultipartFile multipartFile = fileIte.next();
					// 获得原文件名
					String originalFilename = multipartFile.getOriginalFilename();
					//是否是允许的后缀文件
					if(!isValid(originalFilename, allowTypes)){
						System.out.println("该文件不符合规范："+originalFilename);
						continue;
					}
					// 设置保存路径.
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String filePath = path.getPath() + originalFilename;
					System.out.println("filePath: " + filePath);
					// 保存文件
					File dest = new File(filePath);
					if (!(dest.exists())) {
						/*
						 * MultipartFile提供了void transferTo(File dest)方法,
						 * 将获取到的文件以File形式传输至指定路径.
						 */
						try {
							multipartFile.transferTo(dest);
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						/*
						 * 如果需对文件进行其他操作, MultipartFile也提供了 InputStream
						 * getInputStream()方法获取文件的输入流
						 * 
						 * 例如下面的语句即为通过 org.apache.commons.io.FileUtils提供的 void
						 * 
						 * copyInputStreamToFile(InputStream source, File
						 * destination) 方法, 获取输入流后将其保存至指定路径
						 */
						// FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
						// dest);
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	 public static boolean isValid(String contentType, String... allowTypes) {  
	        if (null == contentType || "".equals(contentType)) {  
	            return false;  
	        }  
	        for (String type : allowTypes) {  
	            if (contentType.endsWith(type)) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    }  
	  
	
}
