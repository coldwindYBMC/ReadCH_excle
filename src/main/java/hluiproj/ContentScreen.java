package hluiproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import excel.Util.Util;

/**
 * 文本筛选
 **/
@Service
public class ContentScreen {
	private boolean enterChStr;// 是否进入文本 可能出现中文的 字符串 （t=""）
	private List<String> lineList;// 仅记录当前行的中文

	/**
	 * 对hluipeoj 格式的文件进行筛选 进入 ‘t="’ 表示进入中文 对 ‘"空格’ 退出
	 * 
	 **/
	public void chineseScreen(String line, List<String> chList) {
		lineList = new ArrayList<>();
		StringBuffer chStr = new StringBuffer();

		// 对行字符筛选
		for (int i = 0; i < line.length(); i++) {
			// 退出可能出现中文的地方
			if ((enterChStr && line.charAt(i) == '\"' && i + 1 < line.length() && line.charAt(i + 1) == ' ')) {
				enterChStr = false;
				if (Util.isChinese(chStr.toString())) {
					if (!chList.contains(chStr.toString())) { // 添加到列表
						chList.add(chStr.toString());
						lineList.add(chStr.toString());
					}
				}
				chStr.setLength(0);// 清空stringbuffer缓存
			}
			//如果还处于文本内，存入stringbuffer
			if (enterChStr) {
				chStr.append(line.charAt(i));
			}

			// 进入t 文本， 判断是否有中文
			if (line.charAt(i) == ' ' && iscommon(line, i + 1)) {
				i += 3;// 跳到 " 处
				enterChStr = true;

			}
			// 进入 nt文本， 判断是否有中文
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'n' && iscommon(line, i + 2)) {
				i += 4;// 跳到 " 处
				enterChStr = true;

			}

			// 进入 st文本， 判断是否有中文
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 's' && iscommon(line, i + 2)) {
				i += 4;// 跳到 " 处
				enterChStr = true;
			}
			// 进入 ht文本， 判断是否有中文
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'h' && iscommon(line, i + 2)) {
				i += 4;// 跳到 " 处
				enterChStr = true;

			}
			// 进入 dt文本， 判断是否有中文
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'd' && iscommon(line, i + 2)) {
				i += 4;// 跳到 " 处
				enterChStr = true;
			}
		}
	}

	public boolean iscommon(String line, int k) {
		// 进入 文本， 判断是否有中文
		if (!enterChStr && line.charAt(k) == 't' && k + 1 < line.length() && line.charAt(k + 1) == '='
				&& k + 2 < line.length() && line.charAt(k + 2) == '\"') {
			return true;
		}
		return false;
	}

}
