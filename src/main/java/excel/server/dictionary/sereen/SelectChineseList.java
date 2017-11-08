package excel.server.dictionary.sereen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
import hluiproj.ReadHlUIFile;

/***
 * ɸѡ���ļ�������
 **/
@Service
public class SelectChineseList {
	@Autowired
	private ReadHlUIFile readUI;
	private List<String>list;
	private FileInputStream inputStream;
	private Workbook workbook;

	public SelectChineseList() {
		init();
	}

	public void readfile(File file){
		//��ȡUI�ļ�
		if(file.getName().endsWith(".hluiproj")){
			readUI.readUI(file,list);
		} else{
			readExcel(file);//��ȡexcel�ļ�
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	public void readExcel(File file) {
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
						selectEnd();
						System.out.println("��ȡ����row is null:" + file.getName());
						return;
					}
			
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) continue;
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						
						String value = cell.getStringCellValue();
						if (Util.isChinese(value)) {
							if (row0.getCell(k) == null) {                   //����Ϊ��,���
								continue;
							}
							String title = row0.getCell(k).getStringCellValue();
							if(Util.isChinese(title) || Util.isFirstLower(title)){ //���һЩtitleΪ���ֵı�ע
								continue;
							}
							if(!list.contains(value)){ //��ӵ��б�
								list.add(value);
							}
							
						}
					}
						
				}
				selectEnd();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	// ������ѯ
	private void selectEnd() {
		try {
			workbook.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getList() {
		if(list == null){
			System.out.println(" SelectChineseList.class: getList is null");
		}
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	private void init(){
		list = new ArrayList<String>();
	}

	public void cleanList(){
		this.list.clear();
	}

}
