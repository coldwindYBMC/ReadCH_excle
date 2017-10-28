package hluiproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import excel.Util.Util;

/**
 * �ı�ɸѡ
 **/
@Service
public class ContentScreen {
	private boolean enterChStr;// �Ƿ�����ı� ���ܳ������ĵ� �ַ��� ��t=""��
	private List<String> lineList;// ����¼��ǰ�е�����

	/**
	 * ��hluipeoj ��ʽ���ļ�����ɸѡ ���� ��t="�� ��ʾ�������� �� ��"�ո� �˳�
	 * 
	 **/
	public void chineseScreen(String line, List<String> chList) {
		lineList = new ArrayList<>();
		StringBuffer chStr = new StringBuffer();

		// �����ַ�ɸѡ
		for (int i = 0; i < line.length(); i++) {
			// �˳����ܳ������ĵĵط�
			if ((enterChStr && line.charAt(i) == '\"' && i + 1 < line.length() && line.charAt(i + 1) == ' ')) {
				enterChStr = false;
				if (Util.isChinese(chStr.toString())) {
					if (!chList.contains(chStr.toString())) { // ��ӵ��б�
						chList.add(chStr.toString());
						lineList.add(chStr.toString());
					}
				}
				chStr.setLength(0);// ���stringbuffer����
			}
			//����������ı��ڣ�����stringbuffer
			if (enterChStr) {
				chStr.append(line.charAt(i));
			}

			// ����t �ı��� �ж��Ƿ�������
			if (line.charAt(i) == ' ' && iscommon(line, i + 1)) {
				i += 3;// ���� " ��
				enterChStr = true;

			}
			// ���� nt�ı��� �ж��Ƿ�������
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'n' && iscommon(line, i + 2)) {
				i += 4;// ���� " ��
				enterChStr = true;

			}

			// ���� st�ı��� �ж��Ƿ�������
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 's' && iscommon(line, i + 2)) {
				i += 4;// ���� " ��
				enterChStr = true;
			}
			// ���� ht�ı��� �ж��Ƿ�������
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'h' && iscommon(line, i + 2)) {
				i += 4;// ���� " ��
				enterChStr = true;

			}
			// ���� dt�ı��� �ж��Ƿ�������
			if (line.charAt(i) == ' ' && line.charAt(i + 1) == 'd' && iscommon(line, i + 2)) {
				i += 4;// ���� " ��
				enterChStr = true;
			}
		}
	}

	public boolean iscommon(String line, int k) {
		// ���� �ı��� �ж��Ƿ�������
		if (!enterChStr && line.charAt(k) == 't' && k + 1 < line.length() && line.charAt(k + 1) == '='
				&& k + 2 < line.length() && line.charAt(k + 2) == '\"') {
			return true;
		}
		return false;
	}

}
