package com.georgefeng.trans.doc_trans;

import java.util.List;

import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;

public class LanManage {
	
	//get list of available languages from google cloud API
	public static List<Language> getLanguageAvailable() throws TranslateException{
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		List<Language> languages = translate.listSupportedLanguages();
		return languages;
	}
	
	
	//get a array of the name of available languages
	public static String[] getLanNames() throws TranslateException{
		List<Language> lans = getLanguageAvailable();
		String[] lanNames = new String[lans.size()];
		for (int i = 0; i < lanNames.length; i++) {
			lanNames[i] = lans.get(i).getName();
		}
		return lanNames;
	}
	
	
	//get the matched code for a chosen language
	public static String getMatchCode(String name) {
		String matchCode = "";
		List<Language> lans = getLanguageAvailable();
		for (Language lan : lans) {
			if (name.equals(lan.getName())) {
				matchCode = lan.getCode();
				break;
			}
		}
		return matchCode;
	}

}
