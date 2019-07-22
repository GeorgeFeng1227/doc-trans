package com.georgefeng.trans.doc_trans;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;


public class DTDriver extends SwingWorker<Void, Void> {
	private DocProcessor dp;
	private DocWriter dw;;
	private TextBuilder tb;
	
	private String fpIn;
	private String fpOut;
	private int mode;
	
	private int prog;
	private JLayeredPane jlp;
	private JPanel jp;
	private JLabel jl;
	private int tt;
	
	
	//Constructor
	public DTDriver() {
		super();
		dp = new DocProcessor();
		dw = new DocWriter();
		tb = new TextBuilder();
	}
	
	
	//creating a working thread
	@Override
    public Void doInBackground() {
		long startTime = System.currentTimeMillis();
		int docType = 0;
		setProgress(0);
		
		try {
			if (fpIn.substring(fpIn.length()-3).equals("doc"))
				docType = 0;
			else if(fpIn.substring(fpIn.length()-4).equals("docx"))
				docType = 1;
			else
				ProcessDir();
			
			docTrans(fpIn, fpOut, mode, docType);
			
		} catch (IOException ex) {
			System.err.println("Caught IOException: " + ex.getMessage());
		}
		
		long endTime = System.currentTimeMillis();
		
		tt =  (int)(endTime - startTime)/1000;
		//System.out.println("DONE!");
		//System.out.println("Total time used in Seconds: " + (endTime - startTime)/1000);
		return null;
    }
	
	
	//perform after this thread is finished
	@Override
    public void done() {
		jl.setText(tt + " s");
		jlp.removeAll();
		jlp.add(jp);
		jlp.repaint();
		jlp.revalidate();
    }
	
	
	//Set necessary parameters
	public void setParameters(String fpi, String fpo, int m) {
		fpIn = fpi;
		fpOut = fpo;
		mode = m;
	}
	
	//get necessary swing components from frame1
	public void setComponents(JLayeredPane JLP, JPanel JP, JLabel JL) {
		jlp = JLP;
		jp = JP;
		jl = JL;
	}
	
	
	//if a folder name is entered, this function will help iterate through all the word documents in the folder.
	//HAVE NOT CREATE GUI FOR THIS FUNCTION YET
	private void ProcessDir () throws IOException {
		File folder = new File(fpIn);
		
		if (!folder.exists()) {
			System.err.println("folder does not exist");
			return;
		}
		
		//int fileNum = 0;
		
		for (File fileEntry: folder.listFiles()) {
			String fName = fileEntry.getName();
			if (fName.substring(fName.length()-3).equals("doc"))
				docTrans(fpIn + "/" + fName, fpIn + "/" + fName, 1, 0);
			else if(fName.substring(fName.length()-4).equals("docx"))
				docTrans(fpIn + "/" + fName, fpIn + "/" + fName, 1, 1);
			else
				System.out.println("This file format is currently unsupported");
			//System.out.println("File Number: " + (++fileNum) + " finished out of " + folder.listFiles().length);
		}	
	}
	
	
	//Main Structure
	private void docTrans(String fpi, String fpo, int m, int type) throws IOException{
		String[] oText;
		String[] tText;
		String bText;
		
		switch(type) {
			case 0:
				oText = dp.processDoc(fpi);
				setProgress(5);
				//test
				//System.out.println(getProgress());
				
				tText = transAPI(oText);
				tb.setOrText(oText);
				tb.setTrText(tText);
				bText = textBuilding(m,type);
				dw.writeDoc(bText, fpo);
				setProgress(100);
				//test
				//System.out.println(getProgress());
				
				break;
			
			case 1:
				oText = dp.processDocx(fpi);
				setProgress(5);
				//test
				//System.out.println(getProgress());
				
				tText = transAPI(oText);
				tb.setOrText(oText);
				tb.setTrText(tText);
				bText = textBuilding(m, type);
				dw.writeDocx(bText, fpi, fpo);
				setProgress(100);
				//test
				//System.out.println(getProgress());
				
				break;

		}

	}
	
	
	//Building the output text according to each mode
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
	
	
	//Translating the original text
	private String[] transAPI(String[] oStr) {
		
		//test
		//System.out.println("Translating.....");
		
		int arrLen = oStr.length;
		String[] result = new String[arrLen];
		
		// Instantiates a client
	    Translate translate = TranslateOptions.getDefaultInstance().getService();

	    // The text to translate
	    String text;

	    // Translates text into English
	    Translation translation;
	    
	  
		for (int i = 0; i < arrLen; i++) {
			if (oStr[i] == null || oStr[i].trim().equals(""))
				continue;
			
			text = oStr[i];
			translation = translate.translate(
		            text,
		            TranslateOption.sourceLanguage("zh-CN"),
		            TranslateOption.targetLanguage("en"));
			
			result[i] = translation.getTranslatedText();
			
			//calculate progress
			double curr = i + 1;
			prog = (int)((curr / arrLen) * 90);
			
			setProgress(prog + 5);
			
			//test
			//System.out.println(getProgress());
		}
		
		return result;
	}

}
