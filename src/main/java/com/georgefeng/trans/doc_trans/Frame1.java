package com.georgefeng.trans.doc_trans;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class Frame1 {

	private JFrame frmDocumentTranslator;
	private JButton btnChooser;
	private JButton button;
	private String fnIN; 
	private String fnOUT;
	private JFileChooser fc;
	private JTextField txtEOF;
	private JTextField txtEIF;
	private JComboBox cbMode;
	private JCheckBox chckbxROF;
	private JLabel lblwarning;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frmDocumentTranslator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDocumentTranslator = new JFrame();
		frmDocumentTranslator.setTitle("Document Translator");
		frmDocumentTranslator.setBounds(100, 100, 450, 300);
		frmDocumentTranslator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDocumentTranslator.getContentPane().setLayout(null);
		
		//file chooser
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new DocFilter());
        fc.setAcceptAllFileFilterUsed(false);
		
		//submit button
		JButton btnST = new JButton("Start Translating");
		btnST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTDriver dd = new DTDriver();
				int mode = cbMode.getSelectedIndex() + 1;
				fnIN = txtEIF.getText();
				fnOUT = txtEOF.getText();
				if (!fnIN.equals("Enter input file path and name") && !fnOUT.equals("Enter output file path and name")) {
					lblwarning.setText("");
					int tt = dd.mainSystem(fnIN, fnOUT, mode);
				} else {
					lblwarning.setText("Please Filling Both Input and Output path!");
				}
			}
		});
		btnST.setBounds(261, 243, 167, 29);
		frmDocumentTranslator.getContentPane().add(btnST);
		
		//Input path chooser button
		btnChooser = new JButton("Choose");
		btnChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(txtEIF);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fnIN = fc.getSelectedFile().getPath();
					txtEIF.setText(fnIN);
					
				}
			}
		});
		btnChooser.setBounds(311, 44, 117, 29);
		frmDocumentTranslator.getContentPane().add(btnChooser);
		
		//Output path chooser button
		button = new JButton("Choose");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(txtEOF);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fnOUT = fc.getSelectedFile().getPath();
					txtEOF.setText(fnOUT);
				}
			}
		});
		button.setBounds(311, 104, 117, 29);
		frmDocumentTranslator.getContentPane().add(button);
		
		//check box for "rewrite original file
		chckbxROF = new JCheckBox("Rewrite original file");
		chckbxROF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxROF.isSelected()) {
					fnOUT = fnIN;
					txtEOF.setText(fnOUT);
					txtEOF.setEnabled(false);
					button.setEnabled(false);
				} else {
					txtEOF.setEnabled(true);
					button.setEnabled(true);
					txtEOF.setText("Enter output file path and name");
					fnOUT = null;
				}
				
			}
		});
		chckbxROF.setBounds(20, 84, 160, 23);
		frmDocumentTranslator.getContentPane().add(chckbxROF);
		
		//Text field for input path
		txtEIF = new JTextField();
		txtEIF.setText("Enter Input file path and name");
		txtEIF.setColumns(10);
		txtEIF.setBounds(20, 44, 292, 29);
		frmDocumentTranslator.getContentPane().add(txtEIF);
		
		//Text field for output path
		txtEOF = new JTextField();
		txtEOF.setText("Enter output file path and name");
		txtEOF.setBounds(20, 104, 292, 29);
		frmDocumentTranslator.getContentPane().add(txtEOF);
		txtEOF.setColumns(10);
		
		//the combo box for choosing different writing mode
		cbMode = new JComboBox();
		cbMode.setModel(new DefaultComboBoxModel(new String[] {"Alternating between Translated Text and Orignial Text Paragraph by Paragraph", "Put Translated Text After Orignal Text", "Keep only Translated Text"}));
		cbMode.setBounds(156, 172, 175, 27);
		frmDocumentTranslator.getContentPane().add(cbMode);
		
		JLabel lblMode = new JLabel("Mode");
		lblMode.setBounds(109, 176, 45, 16);
		frmDocumentTranslator.getContentPane().add(lblMode);
		
		//warning text
		lblwarning = new JLabel("");
		lblwarning.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblwarning.setBounds(20, 248, 241, 16);
		frmDocumentTranslator.getContentPane().add(lblwarning);
	}
}
