package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {
	public static Workbook workbook;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		init();
		String value = null ;
		double a;
		Sheet sheet = workbook.getSheetAt(0);
		for(int rowNum = 0;  rowNum  < sheet.getLastRowNum();  rowNum++){
			Row row = sheet.getRow(rowNum);
			for(int cellNum = 0 ;cellNum < row.getLastCellNum();cellNum++){
				Cell cell = row.getCell(cellNum);
				switch(cell.getCellType()){
				case HSSFCell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula();
					break;
				default:
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					 value = cell.getStringCellValue();
				}
				System.out.println(value);
			}
		}
		WriteToExcel w = new WriteToExcel();
		w.write();
	}
	private static void init() {
		String s = "C://Users//Administrator//Desktop//111.xlsx";
		File f = new File(s);
		try {
			workbook = WorkbookFactory.create(new FileInputStream(f));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
