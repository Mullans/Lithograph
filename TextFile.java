import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class TextFile {
	private int length;
	private boolean phrase = false;
	private ArrayList<Character> textPic = new ArrayList<Character>();
	private String bookTitle;
	private String imageTitle;
	private int fontSize;
	private int fontWidth;
	private int fontHeight;
	private int fontCase = 0;
	private String fontType;
	//Constructor creates the variables and holds them, count and textArray return them
	public TextFile(File text){
		//Counts the number of characters in a text file, not including spaces
		String fileName = text.getName(); 
		int lastPeriod = fileName.lastIndexOf('.');
		fileName = fileName.substring(0,lastPeriod);
		bookTitle = fileName;
		int numText = 0;
        try{
            Scanner textScan = new Scanner(text);
            while (textScan.hasNext()){
            	String textSeek = textScan.next();
            	char space = ' ';
            	int index = 0;
            	while (index<textSeek.length()){
            		if (textSeek.charAt(index)!=space){
            			numText++;
            			
            		}
            		textPic.add(textSeek.charAt(index));
            		index++;
            	}
            }
        }catch(FileNotFoundException e) {
            //Exchange this with a text box
        	System.out.println("Sorry, Nothing There");
            //Quit Program
        }
        length = numText;
	}
	public TextFile(String text){
		//Counts the number of characters in a text file, not including spaces
		String fileName = ""; 
		bookTitle = fileName;
		int numText = 0;
		Scanner textScan = new Scanner(text);
		while (textScan.hasNext()){
			String textSeek = textScan.next();
			char space = ' ';
			int index = 0;
			while (index<textSeek.length()){
				if (textSeek.charAt(index)!=space){
					numText++;

				}
				textPic.add(textSeek.charAt(index));
				index++;
			}
		}
        length = numText;
	}
	

	public void setTitle(String e){
		imageTitle = e;
	}
	public String getTitle(){
		return imageTitle;
	}
	public void notPhrase(){
		phrase = false;
	}
	public void setPhrase(){
		phrase = true;
	}
	public boolean isPhrase(){
		return phrase;
	}
	public int count(){
		return length;
	}
	public ArrayList<Character> textArray(){
		return textPic;
	}
	public String getName(){
		return bookTitle;
	}
	public void setFont(int size){
		fontSize = size;
		switch (fontSize){
		case 6:
			fontWidth = 4;
			fontHeight = 4;
			break;
		case 7:
			fontWidth = 5;
			fontHeight = 5;
			break;
		case 8:
			fontWidth = 6;
			fontHeight = 6;
			break;
		case 9:
			fontWidth = 7;
			fontHeight = 7;
			break;
		case 10:
			fontWidth = 7;
			fontHeight = 8;
			break;
		case 11:
			fontWidth = 8;
			fontHeight = 9;
			break;
		case 12:
			fontWidth = 9;
			fontHeight = 9;
			break;
		case 13:
			fontWidth = 9;
			fontHeight = 10;
			break;
		case 14:
			fontWidth = 10;
			fontHeight = 11;
			break;
		case 15:
			fontWidth = 11;
			fontHeight = 11;
			break;
		case 16:
			fontWidth = 11;
			fontHeight = 12;
			break;
		}
	}
	public int getFont(){
		return fontSize;
	}
	public int getFontWidth(){
		return fontWidth;
	}
	public int getFontHeight(){
		return fontHeight;
	}
	public void setCase(int c){
		fontCase = c;
	}
	public int getCase(){
		return fontCase;
	}
	public void setType(String typeface){
		fontType = typeface;
	}
	public String getType(){
		return fontType;
	}
}
