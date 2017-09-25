package excel.server;

public enum PathType {
	 SourcePath("D:\\excel_web\\uploadexcel\\"), //保存上传的源文件，到特定目录
	 AllDictionary("D:\\excel_web\\alldictionary\\"),//字典整体
	 ComplementDictionary("D:\\excel_web\\complementdictionary\\"),//需要补全的字典部分
	 localExcel("D:\\excel_web\\excel\\"),//本地从svn下载的excel表
	 Translate("D:\\excel_web\\translateExcel\\"),//需要翻译的表
	 FinishTranslate("D:\\excel_web\\finishtran\\"),//完成翻译的表
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
