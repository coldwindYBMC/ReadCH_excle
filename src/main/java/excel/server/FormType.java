package excel.server;

public enum FormType {
	Source(1),
	Dictionarty(2),
	Translate(3),
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
