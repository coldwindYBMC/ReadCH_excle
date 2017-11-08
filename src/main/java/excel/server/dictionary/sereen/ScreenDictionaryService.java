package excel.server.dictionary.sereen;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import excel.server.dictionary.CreateDictionary;
import excel.server.dictionary.GetDictionaryMap;
import excel.server.dictionary.OptionService;
import excel.server.payhenum.PathType;

@Service
public class ScreenDictionaryService extends OptionService{
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
		getDirectory(file).forEach(f->{
			selectChineseList.readfile(f.getAbsoluteFile());
		});
		createDictionary.writeExcel(selectChineseList.getList(), map);
		selectChineseList.cleanList();
	}
}






