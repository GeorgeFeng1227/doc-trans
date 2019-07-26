package com.georgefeng.trans.doc_trans;

//NOT USING IT IN MAIN PROGRAM ANYMORE
//SAME FUNCTION IS INTEGRETED IN DTDriver
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class Translator {
	//private String[] content;
	//private int docType;
	
	//Constructor
	public Translator () {
		//content = c;
		//docType = dt;
	}
	
	//Call Google api to translate the array of text
	//going to add language parameter in the future. 
	public String[] transAPI(String[] oStr) {
		
		//test
		System.out.println("Translating.....");
		
		
		String[] result = new String[oStr.length];
		
		// Instantiates a client
	    Translate translate = TranslateOptions.getDefaultInstance().getService();

	    // The text to translate
	    String text;

	    // Translates text into English
	    Translation translation;
	    
	    
		for (int i = 0; i < oStr.length; i++) {
			if (oStr[i] == null || oStr[i].trim().equals(""))
				continue;
			
			text = oStr[i];
			translation = translate.translate(
		            text,
		            TranslateOption.sourceLanguage("zh-CN"),
		            TranslateOption.targetLanguage("en"));
			
			result[i] = translation.getTranslatedText();
		}
		
		return result;
	}
	
	//System.out.println((i + 1) + " / " + oStr.length);

}
