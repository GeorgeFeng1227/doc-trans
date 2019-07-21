package com.georgefeng.trans.doc_trans;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocProcessor {
	
	
	//constructor 
	public DocProcessor() {
		
	}
	
	
	//read the text from .doc file type
	public String[] processDoc (String fn) throws IOException {
		FileInputStream fis = null;
		HWPFDocument document = null;
		WordExtractor extractor = null;
		String[] fileText = null;
		try {
			fis = new FileInputStream(fn);
			document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
		   
			//get original text
			fileText = extractor.getParagraphText();
		   
		} catch(Exception ex) {
		    ex.printStackTrace();
		} finally {
			if (document != null)
				document.close();
			if (extractor != null)
				extractor.close();
			if (fis != null)
				fis.close();
		}
		return fileText;
	}
	
	
	//read text from .docx file type
	public String[] processDocx (String fn) throws IOException {
		FileInputStream fis = null;
		XWPFDocument documentI = null;

		String[] fileText = null;
		try {
			fis = new FileInputStream(fn);
			documentI = new XWPFDocument(fis);
		    
			//get original text
			List<XWPFParagraph> paragraphs = documentI.getParagraphs();
			fileText = new String[paragraphs.size()];
			int count = 0;
			for (XWPFParagraph p:paragraphs) {
				fileText[count] = p.getText();
				count++;
			}
		   
		} catch(Exception ex) {
		    ex.printStackTrace();
		} finally {
			if (documentI != null)
				documentI.close();
			if (fis != null)
				fis.close();
		}
		
		return fileText;
	}
}
