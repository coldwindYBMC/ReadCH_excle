package excel.server.payhenum;

public enum PathType {
	 SourcePath("D:\\excel_web\\uploadexcel\\"), //�����ϴ���Դ�ļ������ض�Ŀ¼
	 ExcelAllDictionary("D:\\excel_web\\excel_alldictionary\\"),//�ֵ�����
	 UiAllDictionary("D:\\excel_web\\ui_alldictionary\\"),//�ֵ�����
	 DownDictionary("D:\\excel_web\\downdictionary\\"),//�����ɵ��ֵ�
	 localExcel("D:\\excel_web\\excel\\"),//���ش�svn���ص�excel��
	 Translate("D:\\excel_web\\translateExcel\\"),//��Ҫ����ı�
	 FinishTranslate("D:\\excel_web\\finishtran\\"),//��ɷ���ı�
	 ZipPath("D:\\excel_web\\zip\\"),//ѹ���������ɵı�
	 ;
	
	 private String path;
	 private PathType( String path){
		this.setPath(path);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	 
}
