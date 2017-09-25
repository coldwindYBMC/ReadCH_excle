package excel.server;

public enum PathType {
	 SourcePath("D:\\excel_web\\uploadexcel\\"), //�����ϴ���Դ�ļ������ض�Ŀ¼
	 AllDictionary("D:\\excel_web\\alldictionary\\"),//�ֵ�����
	 ComplementDictionary("D:\\excel_web\\complementdictionary\\"),//��Ҫ��ȫ���ֵ䲿��
	 localExcel("D:\\excel_web\\excel\\"),//���ش�svn���ص�excel��
	 Translate("D:\\excel_web\\translateExcel\\"),//��Ҫ����ı�
	 FinishTranslate("D:\\excel_web\\finishtran\\"),//��ɷ���ı�
	 ZipPath("D:\\excel_web\\zip\\"),
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
