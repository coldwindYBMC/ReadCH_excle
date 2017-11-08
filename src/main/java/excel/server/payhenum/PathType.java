package excel.server.payhenum;

public enum PathType {
	 SourcePath("D:\\excel_web\\uploadexcel\\"), //保存上传的源文件，到特定目录
	 ExcelAllDictionary("D:\\excel_web\\excel_alldictionary\\"),//字典整体
	 UiAllDictionary("D:\\excel_web\\ui_alldictionary\\"),//字典整体
	 DownDictionary("D:\\excel_web\\downdictionary\\"),//新生成的字典
	 localExcel("D:\\excel_web\\excel\\"),//本地从svn下载的excel表
	 Translate("D:\\excel_web\\translateExcel\\"),//需要翻译的表
	 FinishTranslate("D:\\excel_web\\finishtran\\"),//完成翻译的表
	 ZipPath("D:\\excel_web\\zip\\"),//压缩翻译生成的表
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
