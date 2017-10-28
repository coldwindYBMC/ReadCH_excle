package excel.server.dictionary;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.server.PathType;

@Service
public class ScreenDictionaryService {
	@Autowired
	private SelectChineseList selectChineseList;
	@Autowired
	private CreateDictionary createDictionary;
	@Autowired
	private GetDictionaryMap getDictionaryMap;

	private Map<String, String> map;

	public void exec() {
		map = getDictionaryMap.exce();
		File file = new File(PathType.SourcePath.getPath());
		getDirectory(file);
	}

	// µÝ¹é±éÀú
	private void getDirectory(File file) {
		File flist[] = file.listFiles();
		if (flist == null || flist.length == 0) {
			return;
		}
		for (File f : flist) {
			if (f.isDirectory()) {
				getDirectory(f);
			} else if (f.isFile()) {
				selectChineseList.readfile(f.getAbsoluteFile());
			}
		}
		createDictionary.writeExcel(selectChineseList.getList(), map);
	}
}
