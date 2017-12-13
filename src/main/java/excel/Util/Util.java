package excel.Util;

import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public class Util {

	/**
	 * �ж�һ���ַ��Ƿ�������
	 */
	public static boolean isChinese(char c) {
		return c >= 0x4E00 && c <= 0x9FA5;// �����ֽ����ж�
	}

	/**
	 * �ж�һ���ַ����Ƿ�������
	 */
	public static boolean isChinese(String str) {
		if (str == null)
			return false;
		for (char c : str.toCharArray()) {
			if (isChinese(c))
				return true;// ��һ�������ַ��ͷ���
		}
		return false;
	}
	/**
	 * �ж�excel��һ���Ƿ���ע��
	 *  cell1���ں��� && cell2 �����ں��� ����
	 *	ǿ��  cell1 == ID��������
	 *
	 */
	@SuppressWarnings("deprecation")
	public static boolean existsSecondline(Sheet sheet) {
		if (sheet.getRow(2) == null ||sheet.getRow(1) == null)  return false;//û��ǰ���У�������
		Cell cell1 = sheet.getRow(1).getCell(0);
		Cell cell2 = sheet.getRow(2).getCell(0);
		if(cell1 == null && cell2 != null) return true;
		if (cell2 == null || cell1 == null) return false;//��Ԫ��Ϊ��	
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		String value = cell1.getStringCellValue(); 
		if(value.equals("Id" ) || value.equals("id") || value.equals("ID") || value.equals("iD")){
			return true;
		}		
		if(cell1.getStringCellValue().equals(" ")){
			return true;
		}
		if (Util.isChinese(cell1.getStringCellValue()) && !Util.isChinese(cell2.getStringCellValue()))
			return true;
	
		return false;
	}
	
	
	/** 
	 * Java�ļ����� ��ȡ������չ�����ļ��� 
	 */  
	    public static String getFileNameNoEx(String filename) {  
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');  		// . ���ڵ�λ�� 
	            if ((dot >-1) && (dot < (filename.length()))) {   
	                return filename.substring(0, dot);   
	            }   
	        }   
	        return filename;   
	    }   
	    
	public static boolean isFirstLower(String value) {
		if (value.isEmpty()) {
			System.out.println("isFirstLower���ַ���Ϊ�գ��޴�Сд��return true");
			return true;
		}
		char chr = value.charAt(0);
		if (Character.isLowerCase(chr)) {
			return true;
		} else {
			return false;
		}

	}
	/**
     * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
     * @param dir ��Ҫɾ�����ļ�Ŀ¼
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //�ݹ�ɾ��Ŀ¼�е���Ŀ¼��
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Ŀ¼��ʱΪ�գ�����ɾ��
        return dir.delete();
    }

	

}
