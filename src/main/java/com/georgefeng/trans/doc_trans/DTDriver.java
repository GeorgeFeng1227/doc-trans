package com.georgefeng.trans.doc_trans;

import java.io.File;
import java.io.IOException;

public class DTDriver {
	private DocProcessor dp;
	private DocWriter dw;
	private Translator tl;
	TextBuilder tb;
	
	
	//Constructor
	public DTDriver() {
		dp = new DocProcessor();
		dw = new DocWriter();
		tl = new Translator();
		tb = new TextBuilder();
	}
	
	
public int mainSystem(String fpIn, String fpOut, int mode) {
		
		long startTime = System.currentTimeMillis();
		int docType = 0;
		
		try {
			if (fpIn.substring(fpIn.length()-3).equals("doc"))
				docType = 0;
			else if(fpIn.substring(fpIn.length()-4).equals("docx"))
				docType = 1;
			else
				ProcessDir(fpIn);
			
			docTrans(fpIn, fpOut, mode, docType);
			
		} catch (IOException ex) {
			System.err.println("Caught IOException: " + ex.getMessage());
		}
		
		long endTime = System.currentTimeMillis();
		
		return (int)(endTime - startTime)/1000;
		//System.out.println("DONE!");
		//System.out.println("Total time used in Seconds: " + (endTime - startTime)/1000);
	}
	
	//if a folder name is entered, this function will help iterate through all the word documents in the folder.
	//currently only support mode 1
	private void ProcessDir (String fn) throws IOException {
		File folder = new File(fn);
		
		if (!folder.exists()) {
			System.err.println("folder does not exist");
			return;
		}
		
		//int fileNum = 0;
		
		for (File fileEntry: folder.listFiles()) {
			String fName = fileEntry.getName();
			if (fileEntry.isDirectory())
				ProcessDir(fn + "/" + fName);
			else if (fName.substring(fName.length()-3).equals("doc"))
				docTrans(fn + "/" + fName, fn + "/" + fName, 1, 0);
			else if(fName.substring(fName.length()-4).equals("docx"))
				docTrans(fn + "/" + fName, fn + "/" + fName, 1, 1);
			else
				System.out.println("This file format is currently unsupported");
			//System.out.println("File Number: " + (++fileNum) + " finished out of " + folder.listFiles().length);
		}	
	}
	
	
	private void docTrans(String fpIn, String fpOut, int mode, int type) throws IOException{
		String[] oText;
		String[] tText;
		String bText;
		
		switch(type) {
			case 0:
				oText = dp.processDoc(fpIn);
				tText = tl.transAPI(oText);
				tb.setOrText(oText);
				tb.setTrText(tText);
				bText = textBuilding(mode,type);
				dw.writeDoc(bText, fpOut);
				break;
			
			case 1:
				oText = dp.processDocx(fpIn);
				tText = tl.transAPI(oText);
				tb.setOrText(oText);
				tb.setTrText(tText);
				bText = textBuilding(mode, type);
				dw.writeDocx(bText, fpIn, fpOut);
				break;

		}

	}
	
	
	private String textBuilding(int mode, int type) {
		String result = "";
		
		switch(mode) {
			case 1:
				result = tb.AlterBuiler(type);
				break;
			case 2:
				result = tb.TrAfterOrBuilder(type);
				break;
			case 3:
				result = tb.TrOnlyBuilder(type);
				break;
		}
		
		return result;
	}

}
