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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.JProgressBar;
import java.awt.Color;

public class Frame1 {
	//Components Initialization
	private JFrame frmDocumentTranslator;
	private JButton btnChooser;
	private JButton button;
	private JFileChooser fc;
	private JFileChooser fcf;
	private JTextField txtEOF;
	private JTextField txtEIF;
	private JComboBox cbMode;
	private JCheckBox chckbxROF;
	private JLabel lblwarning;
	private JLayeredPane layeredPane;
	private JPanel panelDO;
	private JPanel panelTrans;
	private JLabel lblNewLabel;
	private JPanel panelHome;
	private JProgressBar progressBar;
	private JPanel panelEnd;
	private JLabel lblTime;
	private JPanel panelFolder;
	private JLabel lblWF;
	private JTextField txtEIFF;
	private JComboBox cbModeF;
	private JProgressBar progressBarFF;
	public JProgressBar progressBarF;
	
	//Variables Initialization
	private String fnIN; 
	private String fnOUT;
	private int mode;
	private JPanel panelTransF;
	private JLabel lblTransSign;
	private JButton btnHome_1;
	private JButton btnHome_2;
	private JLabel lblwarningTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EnvSetup.updateEnv("GOOGLE_APPLICATION_CREDENTIALS", "/Users/Fenggq/Desktop/NL Translation-c9bcb0c1474a.json");
		} catch (ReflectiveOperationException e1) {
			e1.printStackTrace();
		}
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
		frmDocumentTranslator.setResizable(false);
		frmDocumentTranslator.setTitle("Document Translator");
		frmDocumentTranslator.setBounds(100, 100, 450, 300);
		frmDocumentTranslator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDocumentTranslator.getContentPane().setLayout(null);
		
		//file chooser for Document Only
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new DocFilter());
        fc.setAcceptAllFileFilterUsed(false);
        
        //file chooser for folder mode
        fcf = new JFileChooser();
        fcf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 450, 276);
		frmDocumentTranslator.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		
		//Home Panel Components
		panelHome = new JPanel();
		layeredPane.add(panelHome, "name_786537179580661");
		panelHome.setLayout(null);
		
		JButton btnDocumentOnlyMode = new JButton("Document Only");
		btnDocumentOnlyMode.setBounds(134, 87, 171, 29);
		panelHome.add(btnDocumentOnlyMode);
		
		JButton btnFolderMode = new JButton("Folder");
		btnFolderMode.setBounds(134, 133, 171, 29);
		panelHome.add(btnFolderMode);
		
		
		//Document Only Panel Components
		panelDO = new JPanel();
		layeredPane.add(panelDO, "name_786632745888193");
		panelDO.setLayout(null);
		
		//submit button
		JButton btnST = new JButton("Start Translating");
		btnST.setBounds(277, 241, 167, 29);
		panelDO.add(btnST);
		
		//Input path chooser button
		btnChooser = new JButton("Choose");
		btnChooser.setBounds(314, 50, 117, 29);
		panelDO.add(btnChooser);
		
		//Output path chooser button
		button = new JButton("Choose");
		button.setBounds(314, 124, 117, 29);
		panelDO.add(button);
		
		//check box for "rewrite original file
		chckbxROF = new JCheckBox("Rewrite original file");
		chckbxROF.setBounds(26, 101, 160, 23);
		panelDO.add(chckbxROF);
		
		//Text field for input path
		txtEIF = new JTextField();
		txtEIF.setBounds(26, 50, 292, 29);
		panelDO.add(txtEIF);
		txtEIF.setText("Enter Input file path and name");
		txtEIF.setColumns(10);
		
		//Text field for output path
		txtEOF = new JTextField();
		txtEOF.setBounds(26, 124, 292, 29);
		panelDO.add(txtEOF);
		txtEOF.setText("Enter output file path and name");
		txtEOF.setColumns(10);
		
		//the combo box for choosing different writing mode
		cbMode = new JComboBox();
		cbMode.setBounds(167, 181, 175, 27);
		panelDO.add(cbMode);
		cbMode.setModel(new DefaultComboBoxModel(new String[] {"Alternating between Translated Text and Orignial Text Paragraph by Paragraph", "Put Translated Text After Orignal Text", "Keep only Translated Text"}));
		
		JLabel lblMode = new JLabel("Mode");
		lblMode.setBounds(125, 186, 45, 16);
		panelDO.add(lblMode);
		
		//warning text
		lblwarning = new JLabel("");
		lblwarning.setForeground(Color.RED);
		lblwarning.setBounds(26, 243, 241, 16);
		panelDO.add(lblwarning);
		lblwarning.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		//title label
		JLabel lblDOsign = new JLabel("Document Only");
		lblDOsign.setBounds(6, 6, 117, 16);
		panelDO.add(lblDOsign);
		
		//return home
		btnHome_1 = new JButton("Home");
		btnHome_1.setBounds(327, 1, 117, 29);
		panelDO.add(btnHome_1);
		
		
		//Translating Panel Components
		panelTrans = new JPanel();
		layeredPane.add(panelTrans, "name_786636070162057");
		panelTrans.setLayout(null);
		
		lblNewLabel = new JLabel("Translating...");
		lblNewLabel.setBounds(182, 20, 83, 16);
		panelTrans.add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(64, 108, 325, 31);
		panelTrans.add(progressBar);
		
		
		//Ending panel Components
		panelEnd = new JPanel();
		layeredPane.add(panelEnd, "name_787262052223054");
		panelEnd.setLayout(null);
		
		JLabel lblDone = new JLabel("Done! Time used:");
		lblDone.setBounds(87, 108, 119, 16);
		panelEnd.add(lblDone);
		
		lblTime = new JLabel("");
		lblTime.setBounds(218, 108, 200, 16);
		panelEnd.add(lblTime);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(327, 241, 117, 29);
		panelEnd.add(btnExit);
		
		JButton btnHome = new JButton("Home");
		btnHome.setBounds(211, 241, 117, 29);
		panelEnd.add(btnHome);
		
		//Folder Panel
		panelFolder = new JPanel();
		layeredPane.add(panelFolder, "name_873427267812499");
		panelFolder.setLayout(null);
		
		//Start Translating button
		JButton buttonSTF = new JButton("Start Translating");
		buttonSTF.setBounds(277, 241, 167, 29);
		panelFolder.add(buttonSTF);
		
		//Choose file path button
		JButton buttonCF = new JButton("Choose");
		buttonCF.setBounds(313, 103, 117, 29);
		panelFolder.add(buttonCF);
		
		//Enter file path text field
		txtEIFF = new JTextField();
		txtEIFF.setText("Enter Input file path and name");
		txtEIFF.setColumns(10);
		txtEIFF.setBounds(26, 102, 292, 29);
		panelFolder.add(txtEIFF);
		
		//Mode choosing combo box
		cbModeF = new JComboBox();
		cbModeF.setBounds(167, 181, 175, 27);
		cbModeF.setModel(new DefaultComboBoxModel(new String[] {"Alternating between Translated Text and Orignial Text Paragraph by Paragraph", "Put Translated Text After Orignal Text", "Keep only Translated Text"}));
		panelFolder.add(cbModeF);
		
		JLabel lblModeF = new JLabel("Mode");
		lblModeF.setBounds(125, 186, 45, 16);
		panelFolder.add(lblModeF);
		
		//warning
		lblWF = new JLabel("");
		lblWF.setForeground(Color.RED);
		lblWF.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWF.setBounds(26, 243, 241, 16);
		panelFolder.add(lblWF);
		
		//title label
		JLabel lblFsign = new JLabel("Folder");
		lblFsign.setBounds(6, 6, 117, 16);
		panelFolder.add(lblFsign);
		
		//reminder label
		JLabel lblRM = new JLabel("Currently only support rewrite original files");
		lblRM.setHorizontalAlignment(SwingConstants.CENTER);
		lblRM.setBounds(85, 72, 284, 16);
		panelFolder.add(lblRM);
		
		//return home
		btnHome_2 = new JButton("Home");
		btnHome_2.setBounds(327, 1, 117, 29);
		panelFolder.add(btnHome_2);
		
		//Folder Tranlating Panel
		panelTransF = new JPanel();
		layeredPane.add(panelTransF, "name_875151038858936");
		panelTransF.setLayout(null);
		
		lblTransSign = new JLabel("Translating...");
		lblTransSign.setBounds(184, 24, 83, 16);
		panelTransF.add(lblTransSign);
		
		//Progress Bar of single file
		progressBarFF = new JProgressBar();
		progressBarFF.setBounds(63, 100, 325, 31);
		panelTransF.add(progressBarFF);
		
		//Progress Bar of the whole folder
		progressBarF = new JProgressBar();
		progressBarF.setBounds(63, 164, 325, 31);
		panelTransF.add(progressBarF);
		
		lblwarningTF = new JLabel("");
		lblwarningTF.setForeground(Color.RED);
		lblwarningTF.setHorizontalAlignment(SwingConstants.CENTER);
		lblwarningTF.setBounds(6, 223, 438, 16);
		panelTransF.add(lblwarningTF);
		
		
		//Action Listeners / Property Change Listeners
		ActionListener returnHome = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panelHome);
				txtEIF.setText("Enter input file path and name");
				txtEOF.setText("Enter output file path and name");
				txtEIFF.setText("Enter input file path and name");
				lblwarning.setText("");
				lblWF.setText("");
				chckbxROF.setSelected(false);
				fnIN = "";
				fnOUT = "";
			}
		};
		
		//Document only panel
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
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(txtEOF);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fnOUT = fc.getSelectedFile().getPath();
					txtEOF.setText(fnOUT);
				}
			}
		});
		btnChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(txtEIF);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fnIN = fc.getSelectedFile().getPath();
					txtEIF.setText(fnIN);
					
				}
			}
		});
		btnST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = cbMode.getSelectedIndex() + 1;
				fnIN = txtEIF.getText();
				fnOUT = txtEOF.getText();
				if (!fnIN.equals("Enter input file path and name") && !fnOUT.equals("Enter output file path and name")) {
					lblwarning.setText("");
					
					DTDriver dd = new DTDriver();
					dd.setParameters(fnIN, fnOUT, mode);
					dd.setComponents(layeredPane, panelEnd, lblTime);
					dd.addPropertyChangeListener(new PropertyChangeListener() {
						 public void propertyChange(PropertyChangeEvent evt) {
							 if ("progress" == evt.getPropertyName()) {
								 int progress = (Integer) evt.getNewValue();
								 progressBar.setValue(progress);
							 } 
						 }
					});
					dd.execute();
					
					switchPanels(panelTrans);
				} else {
					lblwarning.setText("Please Filling Both Input and Output path!");
				}
			}
		});
		btnHome_2.addActionListener(returnHome);
		
		//Home panel
		btnDocumentOnlyMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panelDO);
			}
		});
		btnFolderMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panelFolder);
			}
		});
		
		//Folder Mode Panel
		buttonCF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fcf.showOpenDialog(txtEIFF);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fnIN = fcf.getSelectedFile().getPath();
					txtEIFF.setText(fnIN);
					
				}
			}
		});
		buttonSTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = cbModeF.getSelectedIndex() + 1;
				fnIN = txtEIFF.getText();
				fnOUT = fnIN;
				if (!fnIN.equals("Enter input file path and name")) {
					lblwarning.setText("");
					
					DTDriver dd = new DTDriver();
					dd.setParameters(fnIN, fnOUT, mode);
					dd.setComponents(layeredPane, panelEnd, lblTime);
					dd.setFolderProgressBar(progressBarF);
					dd.setTextwarning(lblwarningTF);
					dd.addPropertyChangeListener(new PropertyChangeListener() {
						 public void propertyChange(PropertyChangeEvent evt) {
							 if ("progress" == evt.getPropertyName()) {
								 int progress = (Integer) evt.getNewValue();
								 progressBarFF.setValue(progress);
							 }
						 }
					});
					dd.execute();
					
					switchPanels(panelTransF);
				} else {
					lblWF.setText("Please Filling the input path!");
				}
			}
		});
		btnHome_1.addActionListener(returnHome);
		
		//Ending panel
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnHome.addActionListener(returnHome);
	}
	
	
	//switching between different panels
	public void switchPanels(JPanel p) {
		layeredPane.removeAll();
		layeredPane.add(p);
		layeredPane.repaint();
		layeredPane.revalidate();
	}   
}
