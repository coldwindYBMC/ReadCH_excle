package excel.server.dictionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Service;

import excel.server.PathType;

/**
 * 
 * 创建字典
 * */
@Service
public class CreateDictionary {
	private HSSFWorkbook wb;
	private Sheet sheet;
	private CellStyle comCellStyle;
	private static int rowCount = 1;
	private static int  SHEET_MAX_COUNT = 50000;
	private static int  COLNUM_WIDTH = 10000;
	private int sheetNum = 0;
	public CreateDictionary() {
		init();
	}

	@SuppressWarnings({ "deprecation" })
	public void writeExcel( List<String>list, Map<String,String>map) {		
		
		Font comfont = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
		Font fieldfont = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
		fieldfont .setColor(HSSFColor.GREEN.index);
		
		Row row0 = sheet.createRow(0);
		initSheet(row0, comfont);
		
		for(String value :list){
			if(map.get(value) != null){  //去除已经有的字典内容
				continue;
			}
			if(iscreateNewSheet()){
				initSheet(row0, fieldfont);
			}
			Row row = sheet.createRow(rowCount++);
			createCell(comCellStyle, wb, row, 0, value, comfont);
		}
		
		for(String Chkey : map.keySet()){
			if(iscreateNewSheet()){
				initSheet(row0, fieldfont);
			}
			Row row = sheet.createRow(rowCount++);
			createCell(comCellStyle, wb, row, 0, Chkey, comfont); 		//原字典表中文
			createCell(comCellStyle, wb, row, 1, map.get(Chkey), comfont);//原字典表语言1
		}
		
		writeToExcel();
		
	}	
	
	/**
	 * 写入Excel
	 * */
	public void writeToExcel() {
		OutputStream out = null;
		String rootPath = PathType.DownDictionary.getPath();
		String xlsFile = rootPath + "new_dictionary_excel.xls";
		try {
			File f = new File(rootPath);
			if (!f.exists()) f.mkdirs();
			File f1 = new File(xlsFile);
			if (!f1.exists()) f1.createNewFile();
			out = new FileOutputStream(xlsFile);
			wb.write(out); //将excel写入流	
			out.close();
			wb.close();
			rowCount = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 需要创建新的sheet
	 * */
	public boolean iscreateNewSheet(){
		if (rowCount >= SHEET_MAX_COUNT) {
			setSheet(wb.createSheet());
			sheetNum++;
			rowCount = 1;  
			wb.setSheetName(sheetNum,"sheet"+sheetNum);
			return true;
		}
		return false;
	}
	
	/**
	 * 创建单元格并设置样式,值
	 * 
	 * @param wb
	 * @param row
	 * @param column
	 * @param
	 * @param
	 * @param value
	 */
	@SuppressWarnings("deprecation")
	public static void createCell(CellStyle cellStyle, Workbook wb, Row row, int column, String value, Font font) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 设置字体
	 * 
	 * @param wb
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic, short hight) {
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	private void init(){
		this.wb = new HSSFWorkbook();
		this.sheet = wb.createSheet();
		this.comCellStyle = wb.createCellStyle();
		wb.createCellStyle();
	}
	
	private void initSheet(Row row,Font comfont){
		sheet.setColumnWidth((short) 0, (short) COLNUM_WIDTH);
		sheet.setColumnWidth((short) 1, (short) COLNUM_WIDTH);
		sheet.setColumnWidth((short) 2, (short) COLNUM_WIDTH);
	
		createCell(comCellStyle, wb, row, 0, "Chinese",comfont); 
		createCell(comCellStyle, wb, row, 1, "language1",comfont); 
		createCell(comCellStyle, wb, row, 2, "language2",comfont); 
		
	}
}
