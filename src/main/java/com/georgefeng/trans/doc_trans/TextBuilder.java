package com.georgefeng.trans.doc_trans;

public class TextBuilder {
	private String[] orText;
	private String[] trText;
	
	
	//constructor
	public TextBuilder() {
		
	}
	
	
	public TextBuilder(String[] ort, String[] trt) {
		orText = ort;
		trText = trt;
	}
	
	
	//put paragraph by paragraph alternating between original text and translated text
	public String AlterBuiler(int type) {
		StringBuilder accur = new StringBuilder();
		String nl = convertNLS(type);
		for (int i = 0; i < orText.length; i++) {
				accur.append(orText[i] + nl); 
				accur.append(trText[i] + nl);
		}
		
		return accur.toString();
	}
	
	
	//put the translated text after the original text
	public String TrAfterOrBuilder(int type) {
		StringBuilder accur = new StringBuilder();
		String nl = convertNLS(type);
		
		for (int i = 0; i < orText.length; i++) {
				accur.append(orText[i] + nl); 

		}
		
		accur.append(nl);
		
		for (int i = 0; i < trText.length; i++) { 
				accur.append(trText + nl);
		}
		
		return accur.toString();
	}
	
	//Translated text only
	public String TrOnlyBuilder (int type) {
		StringBuilder accur = new StringBuilder();
		String nl = convertNLS(type);
		for (int i = 0; i < trText.length; i++) { 
				accur.append(trText + nl);
		}
		
		return accur.toString();
	}
	
	
	// Relate the type to the correct newline symbol 
	//type 0 is doc and type 1 is docx
	private String convertNLS(int type) {
		String nl = "";
		switch(type) {
			case 0:
				nl = "\013";
				break;
			case 1:
				nl = "@split";
				break;
		}
		return nl;
	}
	
	//Getters and Setters
	public void setOrText(String[] ort) {
		orText = ort;
	}
	
	public void setTrText(String[] trt) {
		trText = trt;
	}

	public String[] getOrText() {
		return orText;
	}
	
	public String[] getTrText() {
		return trText;
	}
}
