package excel.server.payhenum;

public enum DicType {
	 excel("excel_dictionary.xls"),
	 ui("ui_dictionary.xls")
	 ;
	
	 private String path;
	 private DicType( String path){
		this.setPath(path);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	 
}
