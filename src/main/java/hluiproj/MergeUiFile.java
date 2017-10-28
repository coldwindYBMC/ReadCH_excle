package hluiproj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MergeUiFile {
	@Autowired
	private ContentScreen contentScreen;
	
	private List<String> lineList; // 当前行的中文
	public String exce(String line, Map<String, String> map) {
		init();
		contentScreen.chineseScreen(line, lineList);
		for(String key : lineList){;
			if(map.containsKey(key)){
				line = line.replaceAll(key, map.get(key));
			}
		}
		return line;
	}
	
	private void init() {
		 lineList = new ArrayList<>();
	}

}
