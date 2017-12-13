package hluiproj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.server.payhenum.PathType;

@Service
public class ReadHlUIFile {
	@Autowired
	private ContentScreen contentScreen;
	@Autowired
	private MergeUiFile mergeUI;
	private static BufferedReader br = null;
	//private FileWriter writer;
	private static StringBuffer reFile;
	private OutputStreamWriter fw;

	/**
	 * ����Ϊ��λ��ȡ�ļ�,ɸѡ����
	 */
	public void readUI(File file, List<String> list) {
		init(file);
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				contentScreen.chineseScreen(line, list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	private static void init(File file) {
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));// ����һ��BufferedReader������ȡ�ļ�
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����Ϊ��λ��ȡ�ļ������ĺϲ�
	 */
	public void readUI(File file, Map<String, String> map) {
		init(file);
		initWrite(file);
		reFile = new StringBuffer();
		try {
			String line;
			while ((line = br.readLine()) != null) {
//				if(line.indexOf("ս����ʱ") != -1){
//					System.out.println("*********************");
//					System.out.println("FILE:"+file.getName());
//					System.out.println("*********************");
//				}
				reFile.append(mergeUI.exce(line, map)).append("\n");
			}
			fw.write(reFile.toString());
			//writer.write(reFile.toString());
			mergeEnd();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					reFile.setLength(0);
					br.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	private void initWrite(File file) {
		String rootPath = PathType.FinishTranslate.getPath();
		String uiFile = PathType.FinishTranslate.getPath() + file.getName();
		try {
			File f = new File(rootPath);
			if (!f.exists())
				f.mkdirs();
			File f1 = new File(uiFile);
			if (!f1.exists())
				f1.createNewFile();
			fw = new OutputStreamWriter(new FileOutputStream(uiFile), "utf-8");
			//writer = new FileWriter(uiFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void mergeEnd() {
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
