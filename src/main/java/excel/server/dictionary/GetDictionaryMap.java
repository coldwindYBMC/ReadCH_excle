package excel.server.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import excel.server.payhenum.PathType;

/**
 * 获得字典 map表
 * 
 */
@Service
public class GetDictionaryMap {
	private Map<String, String> map;
	private Workbook workbook;
	private String version;


	@SuppressWarnings("deprecation")
	public Map<String, String> exce() {
		init();
		map.clear();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null)
					continue;
				Cell cell = row.getCell(0);
				if (cell == null)
					continue;
				row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
				String key = row.getCell(0).getStringCellValue();
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
					String value1 = row.getCell(1).getStringCellValue();
					map.put(key, value1);
				}
				if (row.getCell(1) == null) {
					map.put(key, "");
				}
			}
			System.out.println("*******换表*********");
		}
		return map;
	}

	private void init() {
		map = new HashMap<String, String>();
		String sourcePath = checkVersion() ;

		File sourceFile = new File(sourcePath);
		String dictionaryFile = getDictionary(sourceFile);
		System.out.println(dictionaryFile);
		try {
			workbook = WorkbookFactory.create(new FileInputStream(dictionaryFile));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	private String getDictionary(File sourceFile) {
		File flist[] = sourceFile.listFiles();
		if (flist == null || flist.length == 0) {
			System.out.println("字典为空");
		}
		for (File f : flist) {
			if (f.isDirectory()) {
				System.out.println("字典路径存在文件夹");
			} else if (f.isFile()) {
				f.getName().endsWith("dictionary.xls");
				return f.getAbsoluteFile().toString();
			}
		}
		System.out.println("不存在字典");
		return null;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private String checkVersion() {
		if (this.version.equals("excel")) {
			return PathType.ExcelAllDictionary.getPath();
		} else if (this.version.equals("ui")) {
			return PathType.UiAllDictionary.getPath();
		} else {
			return null;
		}
	}
}
