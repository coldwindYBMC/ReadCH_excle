package excel.server.payhenum;

public enum FormType {
	Source(1),
	Excel_Dictionarty(2),
	Translate(3),
	UI_Dictionarty(2),
	;
	 
    private int code;   
    
    private FormType(int code) {   
           this.setCode(code);   
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}   
	
}
