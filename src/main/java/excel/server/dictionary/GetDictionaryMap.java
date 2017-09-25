package excel.server.dictionary;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import excel.server.PathType;
/**
 * 获得字典 map表
 * 
 */
public class GetDictionaryMap {
	private Map<String,String>map;
	private Workbook workbook;
	public GetDictionaryMap(){
		
	}
	
	public Map<String,String> exce() {
		init();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if(row == null) continue;
				Cell cell = row.getCell(0);
				if(cell == null) continue;
				String key = row.getCell(0).getStringCellValue();
				
				if(row.getCell(1) != null){
					String value1 = row.getCell(1).getStringCellValue();
					map.put(key, value1);
				}
			}
			System.out.println("*******换表*********");
		}
		return map;
	}
	
	private void init(){
		map = new HashMap<String,String>();
		String sourcePath = PathType.AllDictionary.getPath() + "dictionary_excel.xls";
		System.out.println(sourcePath);
		try {
			workbook = WorkbookFactory.create(new FileInputStream(sourcePath));
		} catch ( IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}
}
