package test;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToExcel {
//	private Workbook workbook;
	public void write(){
		init();
			
		
	}
	
	private void init() {
		String xlsFile ="C://Users//Administrator//Desktop//222.xlsx";
		try {
			Main.workbook.write(new FileOutputStream(xlsFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
