import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
//TODO add background option (none, white, picture at 70% alpha)

public class LithographGUI extends JFrame{
	public static void main(String[] args) {
    	new LithographGUI();
    }
	public BufferedImage image;
	public ArrayList<Character> text;
	ArrayList<Integer> pixelColors;
	BufferedImage canvas;

	private JCheckBox phraseBox;
	private JCheckBox upperBox;
	private JCheckBox lowerBox;
	private JButton startButton;
	private JButton imageButton;
	private JLabel imageText;
	private JButton textButton;
	private JLabel textText;
	private static ImageFile picture;
	private static TextFile book;
	private int fontSize = 10;
	private boolean isPhrase = false;
	private String phraseString;
	private JTextField title;
	private JTextField phrase;
	String titleString;
	private JComboBox fontChoice;
	private JComboBox typeface;
	
	public LithographGUI(){
	    ClickListener actions = new ClickListener();
	    BoxChecker items = new BoxChecker();

    	
    	//sets up the lithographGUI frame
    	this.setSize(700,300);
    	this.setTitle("Lithograph Generator");
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//Sets the location based on the screen
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	Dimension d = tk.getScreenSize();
    	int x = d.width/4;
    	int y = d.height/4;
    	this.setLocation(x,y);
    	
    	//jpanel code
    	JPanel panel = new JPanel();
    	
		panel.setLayout(null);
		Insets insets = panel.getInsets();

		//StartButton
		startButton = new JButton("Make Lithograph");
		//set false later
		startButton.setEnabled(true);
		Dimension size = startButton.getPreferredSize();
		startButton.setBounds(insets.right+350-size.width/2,230,size.width, size.height);
		startButton.addActionListener(actions);
		panel.add(startButton);

		//image text
		imageButton = new JButton("Browse...");
		imageButton.setEnabled(true);
		size = imageButton.getPreferredSize();
		imageButton.setBounds(insets.right+175, 95, size.width, size.height);
		imageButton.addActionListener(actions);
		panel.add(imageButton);
		
		imageText = new JLabel("No Image Selected");
		size = imageText.getPreferredSize();
		imageText.setBounds(insets.right+50, 100, 125, size.height);
		panel.add(imageText);
		
		//text text
		textButton = new JButton("Browse...");
		textButton.setEnabled(true);
		size = textButton.getPreferredSize();
		textButton.setBounds(insets.right+175, 175, size.width, size.height);
		textButton.addActionListener(actions);
		panel.add(textButton);
		
		textText = new JLabel("No Text Selected");
		size = textText.getPreferredSize();
		textText.setBounds(insets.right+50, 180, size.width, size.height);
		panel.add(textText);
		
		//file name text box
		title = new JTextField(20);
		size = title.getPreferredSize();
		title.setBounds(insets.right+350-size.width/2, insets.top+50, size.width, size.height);
		panel.add(title);
		
		JLabel titleText = new JLabel("Type Name of Lithograph");
		size = titleText.getPreferredSize();
		titleText.setBounds(insets.right+350-size.width/2, insets.top+25, size.width, size.height);
		panel.add(titleText);
		
		//Font text & drop down menu?
		JLabel fontText = new JLabel("Select Font");
		size = fontText.getPreferredSize();
		fontText.setBounds(insets.right+400, 80, size.width, size.height);
		panel.add(fontText);
		
		Integer[] fontChoices = {6,7,8,9,10,11,12,13,14,15,16};
		fontChoice = new JComboBox(fontChoices);
		size = fontChoice.getPreferredSize();
		fontChoice.setBounds(insets.right+570, 95, size.width, size.height);
		fontChoice.setSelectedIndex(4);
		fontChoice.addItemListener(items);
		panel.add(fontChoice);
		
		String[] typefaces = {"Serif","Times New Roman"};
		typeface = new JComboBox(typefaces);
		size = typeface.getPreferredSize();
		typeface.setBounds(insets.right+395,95,size.width,size.height);
		typeface.setSelectedIndex(0);
		typeface.addItemListener(items);
		panel.add(typeface);
		
		//Upper/Lower case check boxes
		JLabel upperText = new JLabel("Uppercase");
		size = upperText.getPreferredSize();
		upperText.setBounds(insets.right+400,124,size.width, size.height);
		panel.add(upperText);
		
		upperBox = new JCheckBox();
		upperBox.setSelected(false);
		size = upperBox.getPreferredSize();
		upperBox.setBounds(insets.right+465,121,size.width,size.height);
		upperBox.addItemListener(items);
		panel.add(upperBox);
		
		JLabel lowerText = new JLabel("Lowercase");
		size = lowerText.getPreferredSize();
		lowerText.setBounds(insets.right+500,124,size.width, size.height);
		panel.add(lowerText);
		
		lowerBox = new JCheckBox();
		lowerBox.setSelected(false);
		size = lowerBox.getPreferredSize();
		lowerBox.setBounds(insets.right+565,121,size.width,size.height);
		lowerBox.addItemListener(items);
		panel.add(lowerBox);
		
		//Phrase check box button
		//disable text button if checked and activate text box
		JLabel phraseBoxText = new JLabel("Use Phrase");
		size = phraseBoxText.getPreferredSize();
		phraseBoxText.setBounds(insets.right+400,155,size.width, size.height);
		panel.add(phraseBoxText);
		
		phraseBox = new JCheckBox();
		phraseBox.setSelected(false);
		size = phraseBox.getPreferredSize();
		phraseBox.setBounds(insets.right+480,150,size.width, size.height);
		phraseBox.addItemListener(items);
		panel.add(phraseBox);
		
		//Box to type in phrase
		phrase = new JTextField(20);
		size = phrase.getPreferredSize();
		phrase.setBounds(insets.right+400,180,size.width, size.height);
		phrase.setEnabled(false);
		panel.add(phrase);
		
		
    	this.add(panel);
    	this.setVisible(true);
    }
	private class BoxChecker implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == phraseBox){
				if(phrase.isEnabled()==false){
					phrase.setEnabled(true);
				}else{
					phrase.setEnabled(false);
				}
				
			}else if (e.getSource() == fontChoice){
				JComboBox cb = (JComboBox)e.getSource();
		        fontSize = (Integer) cb.getSelectedItem();
			}else if (e.getSource() == upperBox){
				if (lowerBox.isSelected() && upperBox.isSelected()){
					lowerBox.setSelected(false);
				}
			}else if (e.getSource() == lowerBox){
				if (upperBox.isSelected() && lowerBox.isSelected()){
					upperBox.setSelected(false);
				}
			}
		}
		
	}
    private class ClickListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		if (e.getSource() == startButton){
    			if (phrase.isEnabled()==true){
    				book = new TextFile(phrase.getText());
    				book.setPhrase();
    			}
    			if (upperBox.isSelected()){
    				book.setCase(1);
    			}else if(lowerBox.isSelected()){
    				book.setCase(2);
    			}
    			book.setType((String)typeface.getSelectedItem());
    			book.setFont(fontSize);
    			titleString = title.getText();
    			book.setTitle(titleString);
    			phraseString = phrase.getText();    			
    			startButton.setText("Working...");
    			EventQueue.invokeLater(new Runnable() {

    	            @Override
    	            public void run() {
    	                create();
    	    			startButton.setText("Finished");
    	    			try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	    			startButton.setText("Make Lithograph");
    	            }
    	        });
//    			System.exit(0);
    			
    		}else if(e.getSource() == imageButton){
    			FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("PNG Only", "png");
    			JFileChooser f = new JFileChooser(System.getProperty("user.home") + "/Desktop");
    			f.addChoosableFileFilter(imageFilter);
    			f.setDialogTitle("Pick An Image File");
    			//Figure out how to limit to .jpg
    			int result = f.showOpenDialog(null);
    			File imageFile = null;
    			if(result == JFileChooser.APPROVE_OPTION){
    				imageFile = f.getSelectedFile();
    			}
    			picture = new ImageFile(imageFile);
    			imageText.setText(picture.getName());
    			image = picture.getImage();
    			canvas = new BufferedImage(picture.getWidth(), picture.getHeight(),BufferedImage.TYPE_INT_RGB);
    		}else if(e.getSource()==textButton){
    			FileNameExtensionFilter textFilter = new FileNameExtensionFilter("TXT Only","txt");
    			JFileChooser g = new JFileChooser(System.getProperty("user.home") + "/Desktop");
    			g.addChoosableFileFilter(textFilter);
    			g.setDialogTitle("Pick A Text File");
    			int result2 = g.showOpenDialog(null);
    			File text = null;
    			if(result2 == JFileChooser.APPROVE_OPTION){
    				text = g.getSelectedFile();
    			}
    			book = new TextFile(text);
    			textText.setText(book.getName());
    		}
    	}
    }
    private static void create() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new TextOverlay(picture, book));
        f.pack();
        //f.setVisible(true);
        //System.exit(0);
    }
}

