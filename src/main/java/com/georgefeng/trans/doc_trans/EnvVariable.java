package com.georgefeng.trans.doc_trans;

public class EnvVariable implements java.io.Serializable {
	private static final long serialVersionUID = -5882948736957060020L;
	
	
	//initialize variables
	private String vaName, vaValue;
	
	//Default Constructor
	public EnvVariable() {
		vaName = vaValue = "";
	}
	
	//Constructor
	public EnvVariable(String vn, String vv) {
		vaName = vn;
		vaValue = vv;
	}
	
	//getters and setters
	public String getName() {
		return vaName;
	}
	
	public String getValue() {
		return vaValue;
	}
	
	public void setName(String vn) {
		vaName = vn;
	}
	
	public void setValue(String vv) {
		vaValue = vv;
	}
}
