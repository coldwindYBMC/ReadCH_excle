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
 * �����ϴ����ļ������ض�Ŀ¼
 * 
 * **/
@Service
public class UploadFileService {
	private final String[]  allowTypes = {".xls",".hluiproj"};
	public boolean upLoad(Iterator<String> fileNames, MultipartHttpServletRequest request, PathType path){
		
		// ����·����Ӧ��Ŀ¼�Ƿ����. ����������򴴽�Ŀ¼
		File dir = new File(path.getPath());
		Util.deleteDir(dir);
		
		while (fileNames.hasNext()) {
			// ��fileNames�����е�ֵ�����
			//MultiValueMap<String, MultipartFile>
			String fileName = fileNames.next();
			System.out.println("####:"+fileName);
			/*
			 * request.getFiles(fileName)������ͨ��fileName���Key, �õ���Ӧ���ļ� �����б�.
			 * ֻ�������Map��, �ļ�����װ��MultipartFile����
			 */
			List<MultipartFile> fileList = request.getFiles(fileName);

			if (fileList.size() > 0) {
				// �����ļ��б�
				Iterator<MultipartFile> fileIte = fileList.iterator();

				while (fileIte.hasNext()) {
					// ���ÿһ���ļ�
					MultipartFile multipartFile = fileIte.next();
					// ���ԭ�ļ���
					String originalFilename = multipartFile.getOriginalFilename();
					//�Ƿ�������ĺ�׺�ļ�
					if(!isValid(originalFilename, allowTypes)){
						System.out.println("���ļ������Ϲ淶��"+originalFilename);
						continue;
					}
					// ���ñ���·��.
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String filePath = path.getPath() + originalFilename;
					System.out.println("filePath: " + filePath);
					// �����ļ�
					File dest = new File(filePath);
					if (!(dest.exists())) {
						/*
						 * MultipartFile�ṩ��void transferTo(File dest)����,
						 * ����ȡ�����ļ���File��ʽ������ָ��·��.
						 */
						try {
							multipartFile.transferTo(dest);
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						/*
						 * �������ļ�������������, MultipartFileҲ�ṩ�� InputStream
						 * getInputStream()������ȡ�ļ���������
						 * 
						 * �����������伴Ϊͨ�� org.apache.commons.io.FileUtils�ṩ�� void
						 * 
						 * copyInputStreamToFile(InputStream source, File
						 * destination) ����, ��ȡ���������䱣����ָ��·��
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
