/**
 * @author <a href="mailto:vapeinnn@gmail.com">Vape in NN</a> 
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameForJStego extends JFrame{		
	private JTextArea textArea;	
	private JMenuItem chooseFileToInsert;
	private JButton insertFileButton;
	private JButton insertTextButton;
	private JButton extractFileButton;
	private JButton extractTextButton;
	private JLabel guideLabel;
	private File container;
	private JLabel lowerLabel;
	private File fileToInsert;
	private BufferedImage  readImage;	
	private JPanel imagePanel;

	public FrameForJStego() throws IOException{
		setTitle("JStegoLSB");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();	
		Dimension prefSize = new Dimension((int)(dimension.width*0.66), (int)(dimension.height*0.9));
		setPreferredSize(prefSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);

		JMenuBar menuBar = new JMenuBar();
		JMenu openMenu = new JMenu("Open");
		JMenu helpMenu = new JMenu("Help");
		JLabel upperLabel = new JLabel();
		guideLabel = new JLabel();
		upperLabel.setText("Container");
		upperLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem chooseContainer = new JMenuItem("Choose Container");
		chooseContainer.addActionListener((event)->{
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
			FileFilter filter = new FileNameExtensionFilter(null, "png", "jpeg", "jpg", "bmp");
			fileChooser.setFileFilter(filter);
			fileChooser.showOpenDialog(this);
			container = fileChooser.getSelectedFile();		
			if(container!=null){
				BufferedImage  readImg=null;			
				try {		
					readImg = ImageIO.read(container);
				} catch (IOException e1) {			
				}
				if(readImg.getColorModel().getPixelSize()<24){
					guideLabel.setText("<html>Color model is not supported.<br>Choose another container to open</html>");	
					upperLabel.setIcon(null);
					upperLabel.setText("Container");
					extractFileButton.setEnabled(false);
					extractTextButton.setEnabled(false);
					insertTextButton.setEnabled(false);
					chooseFileToInsert.setEnabled(false);	
					textArea.setEnabled(false);
				}
				else {
					textArea.setEnabled(true);					
					upperLabel.setText("");
					upperLabel.setIcon(new ImageIcon(readImg));			
					fileToInsert = null;
					extractFileButton.setEnabled(true);
					extractTextButton.setEnabled(true);			
					chooseFileToInsert.setEnabled(true);			
					setText(guideLabel);					
				}
			}
			else{
				guideLabel.setText("Choose container to open");	
				upperLabel.setIcon(null);
				upperLabel.setText("Container");
				extractFileButton.setEnabled(false);
				extractTextButton.setEnabled(false);
				insertTextButton.setEnabled(false);
				chooseFileToInsert.setEnabled(false);	
				textArea.setEnabled(false);
			}
			insertFileButton.setEnabled(false);
			lowerLabel.setIcon(null);
			lowerLabel.setText("Encrypted Container");
		});
		JMenuItem aboutJStegoLSB = new JMenuItem("About...");
		aboutJStegoLSB.addActionListener((event)->{
			JTextPane aboutText = new JTextPane();
			aboutText.setEditable(false);
			Color background = guideLabel.getBackground();
			aboutText.setBackground(background);
			Dimension aboutTextDim = new Dimension(dimension.width/3, dimension.height/2);
			aboutText.setPreferredSize(aboutTextDim);			
			aboutText.setText("JStegoLSB is a program that allows you to hide data in image file containers."
					+ " It uses two bits of red, green, blue colors of each pixel. Alpha-channel is not used. "
					+ "Supported containers formats are PNG, BMP, JPEG. However the image with encrypted data will "
					+ "be saved to PNG format."
					+ "\n"
					+ "\nYou can encrypt file or just a text. To start:"
					+ "\n1. Choose a container in Open menu."
					+ "\n2. Choose a file to insert into image in Open menu or type message you want to insert."
					+ "\n3. Press \"Insert file\" button or \"Insert text\" button respectively."
					+ "\n4. Processed image will be saved to the original image parent folder."
					+ "\n"
					+ "\nTo obtain data from container:"
					+ "\n1. Choose a container in Open menu."
					+ "\n2. Press \"Extract file\" button or \"Extract text\" button respectively."
					+ "\n3. File will be saved to the container image parent folder.");
			JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
		});
		helpMenu.add(aboutJStegoLSB);
		openMenu.add(chooseContainer);
		chooseFileToInsert = new JMenuItem("Choose File to Insert");
		chooseFileToInsert.addActionListener((event)->{
			JFileChooser chooseFileToInsert = new JFileChooser(System.getProperty("user.home"));
			chooseFileToInsert.showOpenDialog(this);
			fileToInsert = chooseFileToInsert.getSelectedFile();
			if(fileToInsert!=null){							
				insertFileButton.setEnabled(true);
				insertTextButton.setEnabled(false);
				extractFileButton.setEnabled(false);
				extractTextButton.setEnabled(false);
				textArea.setEnabled(false);
			}
			else {
				insertFileButton.setEnabled(false);
				extractFileButton.setEnabled(true);
				extractTextButton.setEnabled(true);
				textArea.setEnabled(true);
			}
			setText(guideLabel);
			lowerLabel.setIcon(null);
			lowerLabel.setText("Encrypted Container");
		});
		chooseFileToInsert.setEnabled(false);
		openMenu.add(chooseFileToInsert);
		menuBar.add(openMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);	


		JScrollPane upperScroll = new JScrollPane(upperLabel);		
		lowerLabel = new JLabel();	
		lowerLabel.setText("Encrypted Container");
		lowerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JScrollPane lowerScroll = new JScrollPane(lowerLabel);	
		imagePanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new BorderLayout());
		textArea = new JTextArea(20, 15);
		textArea.setEnabled(false);
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyListener(){		
			@Override
			public void keyPressed(KeyEvent e) {			
			}
			@Override
			public void keyReleased(KeyEvent e) {			
				if (textArea.getText().length()>0&&container!=null) {
					insertTextButton.setEnabled(true);
					insertFileButton.setEnabled(false);
					extractFileButton.setEnabled(false);
					extractTextButton.setEnabled(false);
				}
				else {
					insertTextButton.setEnabled(false);
					if(fileToInsert!=null) insertFileButton.setEnabled(true);
					extractFileButton.setEnabled(true);
					extractTextButton.setEnabled(true);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {				
			}
		});
		JPanel buttonGroup = new JPanel(new GridLayout(4,1));
		insertFileButton = new JButton("Insert file");
		insertFileButton.setEnabled(false);	
		insertFileButton.addActionListener((event)->{
			try {
				encryptFile(container, fileToInsert);
			} catch (IOException e1) {

			}
		});
		insertTextButton = new JButton("Insert text");
		insertTextButton.setEnabled(false);
		insertTextButton.addActionListener((event)->{
			try {
				insertText(container, textArea.getText());
			} catch (IOException e1) {				
			}
		});
		extractFileButton = new JButton("Extract file");
		extractFileButton.setEnabled(false);
		extractFileButton.addActionListener((event)->{
			try {
				extractFile(container);
			} catch (IOException e1) {

			}
		});
		extractTextButton = new JButton("Extract text");
		extractTextButton.setEnabled(false);
		extractTextButton.addActionListener((event)->{
			try {
				extractText(container);
			} catch (IOException e1) {

			}
		});

		Border guideBorder = BorderFactory.createEtchedBorder();
		guideLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
		guideLabel.setBorder(guideBorder);
		guideLabel.setHorizontalAlignment(JLabel.CENTER);
		guideLabel.setText("Choose container to open");
		JScrollPane scrollForGuide = new JScrollPane(guideLabel);		
		scrollForGuide.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollForGuide.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		buttonGroup.add(insertFileButton);
		buttonGroup.add(insertTextButton);
		buttonGroup.add(extractFileButton);
		buttonGroup.add(extractTextButton);
		JPanel forButtons = new JPanel();
		forButtons.add(buttonGroup);
		JScrollPane textScroll = new JScrollPane(textArea);
		textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		buttonPanel.add(textScroll, BorderLayout.NORTH);
		buttonPanel.add(forButtons, BorderLayout.SOUTH);
		buttonPanel.add(scrollForGuide, BorderLayout.CENTER);

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperScroll, lowerScroll);
		split.setResizeWeight(0.5);
		imagePanel.add(split, BorderLayout.CENTER);
		JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, buttonPanel);	
		split2.setResizeWeight(0.66);	
		add(split2, BorderLayout.CENTER);
		pack();

	}

	private void setText(JLabel label){
		label.setText("<html>"+"Container: "+container.toString()+"<br>"+
				(fileToInsert!=null?"File to insert: "+fileToInsert.toString():"")+"</html>");
	}
	private void extractText(File extractTextFile) throws IOException {
		readImage = ImageIO.read(extractTextFile);
		int encodedHeight = readImage.getHeight();
		int encodedWidth = readImage.getWidth();
		StringBuilder binaryEncrypted = new StringBuilder();
		boolean hasMessage =true;
		boolean checkMask=true;
		boolean checkLength=true;
		long encryptedMessageLength=1000L;
		for(int i = 0; i<encodedHeight; i++){
			for(int j=0; j<encodedWidth; j++){
				if(!hasMessage) break;
				Object encryptedPixel = readImage.getRaster().getDataElements(j, i, null);
				int encryptedRGB = readImage.getColorModel().getRGB(encryptedPixel);				
				if(binaryEncrypted.length()>=encryptedMessageLength) break;
				for(int bits=0; bits<6; bits++){
					if(binaryEncrypted.length()>=encryptedMessageLength) break;
					if(binaryEncrypted.length()==16&&checkMask){
						if(Integer.parseInt(binaryEncrypted.toString(), 2)!=0xe432) {
							hasMessage = false;
							guideLabel.setText("<html>"+"Message is not found!"+"</html>");
						}
						else binaryEncrypted.delete(0, 16);							
						checkMask=false;
					}
					if(binaryEncrypted.length()==32&&checkLength){
						encryptedMessageLength = Integer.parseInt(binaryEncrypted.toString(), 2)*16;						
						binaryEncrypted.delete(0, 32);						
						checkLength=false;
					}
					switch(bits){
					case 0: 
						if((encryptedRGB&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");							
						break;
					case 1: 
						if(((encryptedRGB>>1)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");						
						break;
					case 2: 
						if(((encryptedRGB>>8)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");						
						break;
					case 3: 
						if(((encryptedRGB>>9)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");						
						break;
					case 4: 
						if(((encryptedRGB>>16)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");						
						break;
					case 5: 
						if(((encryptedRGB>>17)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");						
						break;
					}
				} 		

			}
			if(binaryEncrypted.length()>=encryptedMessageLength) break;
		}
		if(hasMessage){
			String decrypted="";
			while(binaryEncrypted.length()!=0){
				String binaryTemp = binaryEncrypted.substring(0, 16);
				binaryEncrypted.delete(0, 16);
				decrypted = decrypted+(char)(Integer.parseInt(binaryTemp, 2));			
			}
			textArea.setText(decrypted);	
			extractFileButton.setEnabled(false);
		}
		extractTextButton.setEnabled(false);
		lowerLabel.setIcon(null);
		lowerLabel.setText("Encrypted Container");
	}
	private void extractFile(File containerWithFile) throws IOException {					
		readImage = ImageIO.read(containerWithFile);
		FileOutputStream fos=null;
		File decryptedFile = null;
		int encodedHeight = readImage.getHeight();
		int encodedWidth = readImage.getWidth();
		StringBuilder binaryEncrypted = new StringBuilder();
		boolean hasMessage =true;
		boolean checkMask=true;
		boolean checkLength=false;
		boolean checkExtensionLength=false;
		boolean checkExtension=false;
		long encryptedMessageLength=1000L;
		int encryptedExtLength = 0;
		String decryptedExt = "";
		for(int i = 0; i<encodedHeight; i++){
			for(int j=0; j<encodedWidth; j++){
				if(!hasMessage) break;	
				if(encryptedMessageLength==0) break;
				if(binaryEncrypted.length()>=800&&binaryEncrypted.length()%8==0){
					byte[] decrypted= new byte[binaryEncrypted.length()/8];						
					int count = 0;
					while(binaryEncrypted.length()!=0){
						String binaryTemp = binaryEncrypted.substring(0, 8);
						binaryEncrypted.delete(0, 8);
						decrypted[count] = (byte)(Integer.parseInt(binaryTemp, 2));	
						count++;
					}						
					fos.write(decrypted);
				}
				int encryptedRGB = readImage.getRGB(j, i);				
				for(int bits=0; bits<6; bits++){
					if(encryptedMessageLength==0) break;
					if(binaryEncrypted.length()==16&&checkMask){
						if(Integer.parseInt(binaryEncrypted.toString(), 2)!=0x9bcd) {
							guideLabel.setText("<html>"+"File is not found!"+"</html>");
							hasMessage = false;
						}
						else {							
							binaryEncrypted.delete(0, 16);							
							encryptedMessageLength+=16;
							checkLength=true;
						}
						checkMask=false;
					}
					if(binaryEncrypted.length()==32&&checkLength){
						encryptedMessageLength = Integer.parseInt(binaryEncrypted.toString(), 2)*8;						
						binaryEncrypted.delete(0, 32);						
						checkLength=false;
						checkExtensionLength=true;
					}
					if(binaryEncrypted.length()==8&&checkExtensionLength){
						encryptedExtLength = Integer.parseInt(binaryEncrypted.toString(), 2);							
						binaryEncrypted.delete(0, 8);							
						checkExtensionLength=false;
						checkExtension=true;
					}
					if(binaryEncrypted.length()==encryptedExtLength*16&&checkExtension){
						for(int m=0; m<encryptedExtLength; m++){
							String temp = binaryEncrypted.substring(0, 16);
							decryptedExt+=(char)Integer.parseInt(temp, 2);
							binaryEncrypted.delete(0, 16);
						}					
						checkExtension=false;
						String fileName = "JDecrFile";
						decryptedFile = new File(containerWithFile.getParent()+File.separator+fileName+"."+decryptedExt);
						while(!decryptedFile.createNewFile()) {
							fileName+=encryptedExtLength++;
							decryptedFile=new File(containerWithFile.getParent()+File.separator+fileName+"."+decryptedExt);
						}
						fos=new FileOutputStream(decryptedFile);
					}
					switch(bits){
					case 0: 
						if((encryptedRGB&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");
						encryptedMessageLength--;
						break;
					case 1: 
						if(((encryptedRGB>>1)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");	
						encryptedMessageLength--;
						break;
					case 2: 
						if(((encryptedRGB>>8)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");					
						encryptedMessageLength--;
						break;
					case 3: 
						if(((encryptedRGB>>9)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");	
						encryptedMessageLength--;
						break;
					case 4: 
						if(((encryptedRGB>>16)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");	
						encryptedMessageLength--;
						break;
					case 5: 
						if(((encryptedRGB>>17)&0b1)==1) binaryEncrypted.append("1");
						else binaryEncrypted.append("0");	
						encryptedMessageLength--;
						break;
					}
				} 		
				if(encryptedMessageLength==0) break;
			}				
		}		
		if(hasMessage) {
			if(binaryEncrypted.length()>0){		
				byte[] decrypted= new byte[binaryEncrypted.length()/8];
				int count = 0;
				while(binaryEncrypted.length()!=0){
					String binaryTemp = binaryEncrypted.substring(0, 8);
					binaryEncrypted.delete(0, 8);
					decrypted[count] = (byte)(Integer.parseInt(binaryTemp, 2));	
					count++;
				}				
				fos.write(decrypted);
			}
			fos.close();	
			guideLabel.setText("<html>"+"Decrypted file: "+decryptedFile+"</html>");
			extractTextButton.setEnabled(false);		
		}
		extractFileButton.setEnabled(false);
		lowerLabel.setIcon(null);
		lowerLabel.setText("Encrypted Container");
	}

	private void insertText(File containerForText, String textFromArea) throws IOException {
		readImage = ImageIO.read(containerForText);		
		String mask=Integer.toBinaryString(0xe432);		
		int height = readImage.getHeight();
		int width = readImage.getWidth();		
		String messageLength = Integer.toBinaryString(textFromArea.length());
		while(messageLength.length()!=32){
			messageLength="0"+messageLength;
		}
		StringBuilder result= new StringBuilder();
		result = result.append(mask);
		result = result.append(messageLength);		
		char[] storage = textFromArea.toCharArray(); 
		for(int i = 0; i<storage.length; i++){
			String temp = Integer.toBinaryString(storage[i]);
			while(temp.length()!=16){
				temp="0"+temp;
			}			
			result = result.append(temp);
		}						
		for(int i =0; i<height; i++){
			for(int j = 0; j<width; j++) {
				Object pixel = readImage.getRaster().getDataElements(j, i, null);
				int rgb = readImage.getColorModel().getRGB(pixel);				
				for(int bits=0; bits<6; bits++){
					if(result.length()==0) break;
					switch(bits){
					case 0: 
						if(result.charAt(0)=='1') rgb = rgb|0b1;
						else rgb = rgb&~0b1;
						result.deleteCharAt(0);
						break;
					case 1: 
						if(result.charAt(0)=='1') rgb = rgb|0b10;
						else rgb = rgb&~0b10;
						result.deleteCharAt(0);
						break;
					case 2: 
						if(result.charAt(0)=='1') rgb = rgb|0b100000000;
						else rgb = rgb&~0b100000000;
						result.deleteCharAt(0);
						break;
					case 3: 
						if(result.charAt(0)=='1') rgb = rgb|0b1000000000;
						else rgb = rgb&~0b1000000000;
						result.deleteCharAt(0);
						break;
					case 4: 
						if(result.charAt(0)=='1') rgb = rgb|0b10000000000000000;
						else rgb = rgb&~0b10000000000000000;
						result.deleteCharAt(0);
						break;
					case 5: 
						if(result.charAt(0)=='1') rgb = rgb|0b100000000000000000;
						else rgb = rgb&~0b100000000000000000;
						result.deleteCharAt(0);
						break;
					}
				} 				
				readImage.setRGB(j, i, rgb);
				if(result.length()==0) break;				
			}
			if(result.length()==0) break;
		}
		String encrFileName = "JEncrFile";
		int countEncrFile=0;
		File encryptFile = new File(containerForText.getParent()+File.separator+encrFileName+"."+"png");
		while(!encryptFile.createNewFile()) {
			encrFileName+=++countEncrFile;
			encryptFile=new File(containerForText.getParent()+File.separator+encrFileName+"."+"png");
		}
		ImageIO.write(readImage, "png", encryptFile);
		lowerLabel.setText("");
		lowerLabel.setIcon(new ImageIcon(readImage));		
		guideLabel.setText("<html>"+"Encrypted Image: "+encryptFile+"</html>");		
		insertTextButton.setEnabled(false);		
	}	


	private void encryptFile(File containerFile, File insertFile) throws IOException {
		readImage = ImageIO.read(containerFile);		
		String mask=Integer.toBinaryString(0x9bcd);		
		int height = readImage.getHeight();
		int width = readImage.getWidth();	
		long availableLength = height*width*6/8;
		long requiredLength = insertFile.length();
		if (requiredLength+20>availableLength){
			guideLabel.setText("<html>"+"Not enough space in the selected container.<br>"
					+ "Total pixel number for this file should be at least "
					+ (requiredLength+20)*8/6+ ". Select another container"+"</html>");			
		}
		else {
			FileInputStream fis = new FileInputStream(insertFile);
			StringTokenizer findExtension = new StringTokenizer(insertFile.getName(), ".");
			String extension = "";
			while(findExtension.hasMoreTokens()) extension = findExtension.nextToken();	
			String extensionLength = Integer.toBinaryString(extension.length());	
			while(extensionLength.length()!=8){
				extensionLength="0"+extensionLength;
			}	
			String fileLength = Integer.toBinaryString(fis.available());
			while(fileLength.length()!=32){
				fileLength="0"+fileLength;
			}
			StringBuilder result= new StringBuilder();
			result.append(mask);
			result.append(fileLength);	
			result.append(extensionLength);
			char[] extStorage = extension.toCharArray(); 
			for(int i = 0; i<extStorage.length; i++){
				String temp = Integer.toBinaryString(extStorage[i]);
				while(temp.length()!=16){
					temp="0"+temp;
				}		
				result.append(temp);
			}	
			byte[] fileContent = fillBuffer(fis);		
			for(int i = 0; i<fileContent.length; i++){
				String temp = Integer.toBinaryString((fileContent[i]+256)%256);		
				while(temp.length()!=8){
					temp="0"+temp;
				}		
				result.append(temp);
			}	
			for(int i =0; i<height; i++){		
				for(int j = 0; j<width; j++) {					
					int rgb = readImage.getRGB(j, i);				
					for(int bits=0; bits<6; bits++){
						if(result.length()==0&&fis.available()>0){
							fileContent = fillBuffer(fis);		
							for(int k = 0; k<fileContent.length; k++){
								String temp = Integer.toBinaryString((fileContent[k]+256)%256);		
								while(temp.length()!=8){
									temp="0"+temp;
								}		
								result.append(temp);
							}
						}
						if(result.length()==0) break;			
						switch(bits){
						case 0: 
							if(result.charAt(0)=='1') rgb = rgb|0b1;
							else rgb = rgb&~0b1;
							result.deleteCharAt(0);
							break;
						case 1: 
							if(result.charAt(0)=='1') rgb = rgb|0b10;
							else rgb = rgb&~0b10;
							result.deleteCharAt(0);
							break;
						case 2: 
							if(result.charAt(0)=='1') rgb = rgb|0b100000000;
							else rgb = rgb&~0b100000000;
							result.deleteCharAt(0);
							break;
						case 3: 
							if(result.charAt(0)=='1') rgb = rgb|0b1000000000;
							else rgb = rgb&~0b1000000000;
							result.deleteCharAt(0);					
							break;
						case 4: 
							if(result.charAt(0)=='1') rgb = rgb|0b10000000000000000;
							else rgb = rgb&~0b10000000000000000;
							result.deleteCharAt(0);
							break;
						case 5: 
							if(result.charAt(0)=='1') rgb = rgb|0b100000000000000000;
							else rgb = rgb&~0b100000000000000000;
							result.deleteCharAt(0);
							break;
						}
					} 				
					readImage.setRGB(j, i, rgb);
					if(result.length()==0&&fis.available()>0){
						fileContent = fillBuffer(fis);		
						for(int k = 0; k<fileContent.length; k++){
							String temp = Integer.toBinaryString((fileContent[k]+256)%256);		
							while(temp.length()!=8){
								temp="0"+temp;
							}		
							result.append(temp);
						}
					}
					if(result.length()==0) break;				
				}
				if(result.length()==0&&fis.available()>0){
					fileContent = fillBuffer(fis);		
					for(int k = 0; k<fileContent.length; k++){
						String temp = Integer.toBinaryString((fileContent[k]+256)%256);		
						while(temp.length()!=8){
							temp="0"+temp;
						}		
						result.append(temp);
					}
				}
				if(result.length()==0) break;		
			}
			fis.close();
			String encrFileName = "JEncrFile";
			int countEncrFile=0;
			File encryptFile = new File(containerFile.getParent()+File.separator+encrFileName+"."+"png");
			while(!encryptFile.createNewFile()) {
				encrFileName+=++countEncrFile;
				encryptFile=new File(containerFile.getParent()+File.separator+encrFileName+"."+"png");
			}
			ImageIO.write(readImage, "png", encryptFile);
			lowerLabel.setText("");
			lowerLabel.setIcon(new ImageIcon(readImage));		
			guideLabel.setText("<html>"+"Encrypted Image: "+encryptFile+"</html>");
		}
		insertFileButton.setEnabled(false);
	}
	private byte[] fillBuffer(InputStream reader) throws IOException{
		if(reader.available()>100){
			byte[] buffer = new byte[100];
			reader.read(buffer);
			return buffer;
		}
		else {
			byte[] buffer = new byte[reader.available()];
			reader.read(buffer);
			return buffer;
		}		
	}
}
