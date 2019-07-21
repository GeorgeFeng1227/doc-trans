package com.georgefeng.trans.doc_trans;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocWriter {
	
	
	//constructor
	public DocWriter() {
		
	}
	
	
	public void writeDoc(String textIn, String fn) throws IOException{
		
		FileInputStream fis = null;
		HWPFDocument document = null;
		FileOutputStream out = null;
		try {
			fis = new FileInputStream(fn);
			document = new HWPFDocument(fis);
			
			//write to new doc
			document.getRange().replaceText(textIn, true);
			out = new FileOutputStream(fn);
			document.write(out);
		   
		} catch(Exception ex) {
		    ex.printStackTrace();
		} finally {
			if (document != null)
				document.close();
			if (out != null)
				out.close();
			if (fis != null)
				fis.close();
		}
	}
	
	
	public void writeDocx (String textIn, String fnIn, String fnOut) throws IOException{
		FileInputStream fis = null;
		XWPFDocument documentI = null;
		XWPFDocument documentO = null;
		FileOutputStream out = null;
		
		try {
			fis = new FileInputStream(fnIn);
			documentI = new XWPFDocument(fis);
		    
			//Getting text format and font information from original text
			List<XWPFParagraph> paragraphs = documentI.getParagraphs();
			int index = 0;
			while (paragraphs.get(index).getRuns() == null 
					|| paragraphs.get(index).getRuns().size() == 0)
				index++;
			XWPFRun runTemp = paragraphs.get(index).getRuns().get(0);
			
			
			//write to new doc
			String[] resArr = textIn.split("@split");
			documentO = new XWPFDocument();
			
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
			out = new FileOutputStream(fnOut);
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
	
	
}
