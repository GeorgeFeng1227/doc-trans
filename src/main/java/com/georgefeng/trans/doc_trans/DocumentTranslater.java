package com.georgefeng.trans.doc_trans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

//before using the program, a google cloud private key file need to be download from google. 
public class DocumentTranslater {
	private String fPath;
	
	//Constructor
	public DocumentTranslater(String fp) {
		fPath = fp;
	}
	
	//return total time used
	public int processing() {
		
		long startTime = System.currentTimeMillis();
		
		//ask for file name
		/*
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the file full path: ");
		String fileName = sc.nextLine();
		sc.close();
		*/
		
		try {
			if (fPath.substring(fPath.length()-3).equals("doc"))
				ProcessDoc(fPath);
			else if(fPath.substring(fPath.length()-4).equals("docx"))
				ProcessDocx(fPath);
			else
				ProcessDir(fPath);
		} catch (IOException ex) {
			System.err.println("Caught IOException: " + ex.getMessage());
		}
		
		
		
		long endTime = System.currentTimeMillis();
		
		return (int)(endTime - startTime)/1000;
		//System.out.println("DONE!");
		//System.out.println("Total time used in Seconds: " + (endTime - startTime)/1000);
	}
	
	//if a folder name is entered, this function will help iterate through all the word documents in the folder.
	private void ProcessDir (String fn) throws IOException {
		File folder = new File(fn);
		if (!folder.exists()) {
			System.err.println("folder does not exist");
			return;
		}
		int fileNum = 0;
		for (File fileEntry: folder.listFiles()) {
			String fName = fileEntry.getName();
			if (fileEntry.isDirectory())
				ProcessDir(fn + "/" + fName);
			else if (fName.substring(fName.length()-1).equals("c"))
				ProcessDoc(fn + "/" + fName);
			else if(fName.substring(fName.length()-1).equals("x"))
				ProcessDocx(fn + "/" + fName);
			else
				System.out.println("This file format is currently unsupported");
			//System.out.println("File Number: " + (++fileNum) + " finished out of " + folder.listFiles().length);
		}	
	}
	//read the text from .doc file type
	private void ProcessDoc (String fn) throws IOException {
		FileInputStream fis = null;
		HWPFDocument document = null;
		WordExtractor extractor = null;
		FileOutputStream out = null;
		try {
			fis = new FileInputStream(fn);
			document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
		   
			//get original text
			String[] fileData = extractor.getParagraphText();
		   
			//write to new doc
			String resText = transAPI(fileData,0);
			document.getRange().replaceText(resText, true);
			out = new FileOutputStream(fn);
			document.write(out);
		   
		} catch(Exception ex) {
		    ex.printStackTrace();
		} finally {
			if (document != null)
				document.close();
			if (extractor != null)
				extractor.close();
			if (out != null)
				out.close();
			if (fis != null)
				fis.close();
		}
	}
	
	//read text from .docx file type
	private void ProcessDocx (String fn) throws IOException {
		FileInputStream fis = null;
		XWPFDocument documentI = null;
		XWPFDocument documentO = null;
		FileOutputStream out = null;
		try {
			fis = new FileInputStream(fn);
			documentI = new XWPFDocument(fis);
		    
			//get original text
			List<XWPFParagraph> paragraphs = documentI.getParagraphs();
			String[] fileData = new String[paragraphs.size()];
			int count = 0;
			for (XWPFParagraph p:paragraphs) {
				fileData[count] = p.getText();
				count++;
			}
		   
			//write to new doc
			String resText = transAPI(fileData,1);
			String[] resArr = resText.split("@");
			documentO = new XWPFDocument();
			
			int index = 0;
			while (paragraphs.get(index).getRuns() == null 
					|| paragraphs.get(index).getRuns().size() == 0)
				index++;
			XWPFRun runTemp = paragraphs.get(index).getRuns().get(0);
			
			for (String resStr:resArr) {
				XWPFParagraph paraO = documentO.createParagraph();
				if (paragraphs.get(0) != null) {
					paraO.setSpacingBetween(paragraphs.get(0).getSpacingBetween());
				}
				XWPFRun runO = paraO.createRun();
				runO.setText(resStr);
				if (runTemp != null) {
					runO.setFontFamily(runTemp.getFontFamily());
					runO.setFontSize(runTemp.getFontSize());
				}
			}
			out = new FileOutputStream(fn);
			documentO.write(out);
		   
		} catch(Exception ex) {
		    ex.printStackTrace();
		} finally {
			if (documentI != null)
				documentI.close();
			if (documentO != null)
				documentO.close();
			if (out != null)
				out.close();
			if (fis != null)
				fis.close();
		}
	}
	
	//call google cloud api to translate all the text, and return translation. 
	private String transAPI(String[] oStr, int type) throws Exception{
		StringBuilder accur = new StringBuilder();
		
		// Instantiates a client
	    Translate translate = TranslateOptions.getDefaultInstance().getService();

	    // The text to translate
	    String text;

	    // Translates text into English
	    Translation translation;
	    
	    String result;
	    
		for (int i = 0; i < oStr.length; i++) {
			if (oStr[i] == null || oStr[i].trim().equals(""))
				continue;
			
			text = oStr[i];
			translation =   translate.translate(
		            text,
		            TranslateOption.sourceLanguage("zh-CN"),
		            TranslateOption.targetLanguage("en"));
			
			result = translation.getTranslatedText();
			
			if (type == 0) {
				accur.append(oStr[i]); 
				accur.append(result + "\013");
			} 
			if (type == 1) {
				accur.append(oStr[i] + "@");
				accur.append(result+ "@");
			}
			
			//System.out.println((i + 1) + " / " + oStr.length);
		}
		
		return accur.toString();
	}
}
