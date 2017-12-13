package excel.server.dictionary.merage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.Util.Util;
import excel.server.payhenum.PathType;
import hluiproj.ReadHlUIFile;

@Service
public class MergeExcel {
	@Autowired
	private ReadHlUIFile  mergeUI;
	private FileInputStream inputStream;
	private FileOutputStream outputStream;
	private Workbook workbook;

	
	public void readfile(File file, Map<String,String> map){
		//��ȡUI�ļ�
		if(file.getName().endsWith(".hluiproj")){
			mergeUI.readUI(file,map);
		} else{
			readExcel(file,map);//��ȡexcel�ļ�
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	private void readExcel(File file, Map<String,String> map) {
		if (file.getName().matches("^.+\\.(?i)((xls)|(xlsx))$")) {// �ж��Ƿ�excel�ĵ�		
			System.out.println("����:" + file.getName());
			boolean is03Excel = file.getName().matches("^.+\\.(?i)(xls)$");
			try {
				inputStream = new FileInputStream(file);
				workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
				Sheet sheet = workbook.getSheetAt(0);// ֻ����0��
				Row row0 = sheet.getRow(0);
				for (int i = 0; i <= sheet.getLastRowNum(); i++) { 												
					Row row = sheet.getRow(i);// ��	
					if (i == 1 && Util.existsSecondline(sheet)) {
						continue;
					}
					if (row == null) {   //���������ˣ�API��ȡ����������ȷ
						continue;		
					}
			
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) continue;
						String value = null;
						if(cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA){
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							value = cell.getStringCellValue();
						} else{
							value = cell.getCellFormula();
						}
						if (Util.isChinese(value)) {
							if (row0.getCell(k) == null) {                   //����Ϊ��,���
								continue;
							}
							String title = row0.getCell(k).getStringCellValue();
							if(Util.isChinese(title) || Util.isFirstLower(title)){ //���һЩtitleΪ���ֵı�ע
								continue;
							}
							if(map.get(value) != null){
								cell.setCellValue(map.get(value));
							}
						}
					}
						
				}	
				MergeEnd(file.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private void MergeEnd(String fileName){
		String rootPath = PathType.FinishTranslate.getPath();
		String xlsFile = PathType.FinishTranslate.getPath() + fileName;
		try {
			File f = new File(rootPath);
			if (!f.exists()) f.mkdirs();
			File f1 = new File(xlsFile);
			if (!f1.exists()) f1.createNewFile();
			outputStream = new FileOutputStream(xlsFile);
			workbook.write(outputStream);
			outputStream.close();
			inputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
