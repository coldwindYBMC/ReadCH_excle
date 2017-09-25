package excel.server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 
public class CreateZipService {  
    static final int BUFFER = 8192;  
    ZipOutputStream out = null;  
    FileOutputStream fileOutputStream ;
    CheckedOutputStream cos ;
    private File zipFile;  
    private  String basedir = "";
    public CreateZipService(String pathName) throws FileNotFoundException {  
        zipFile = new File(pathName);  
        fileOutputStream = new FileOutputStream(zipFile);
        cos = new CheckedOutputStream(fileOutputStream, new CRC32());
        out = new ZipOutputStream(cos);
    }  
//    public void compress(String... pathName) {
//        ZipOutputStream out = null;  
//        try { 
//            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
//            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
//                    new CRC32());  
//            out = new ZipOutputStream(cos);  
//            String basedir = "";
//            for (int i=0;i<pathName.length;i++){
//                compress(new File(pathName[i]), out, basedir);  
//            }
//            out.close(); 
//        } catch (Exception e) {  
//            throw new RuntimeException(e);  
//        }
//    }  
    public void compress(String srcPathName) {  
        File file = new File(srcPathName);  
        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
        try {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
            compress(file, basedir);  
            out.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
   
    private void compress(File file, String basedir) {  
        /* 判断是目录还是文件 */ 
        if (file.isDirectory()) {  
            System.out.println("压缩：" + basedir + file.getName());  
            this.compressDirectory(file);  
        } else {  
            System.out.println("压缩：" + basedir + file.getName());  
            this.compressFile(file, basedir);  
        }  
    }  
   
    /** 压缩一个目录 */ 
    public void compressDirectory(File dir) {  
        if (!dir.exists())  
            return;  

        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */ 
            compress(files[i],  basedir + dir.getName() + "/");  
        }  
        
        try {
			out.close();
			cos.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
   
    /** 压缩一个文件 */ 
    private void compressFile(File file, String basedir) {  
        if (!file.exists()) {  
            return;  
        }  
        try {  
            BufferedInputStream bis = new BufferedInputStream(  
                    new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(basedir + file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                out.write(data, 0, count);  
            }  
            bis.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
}