import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextOverlay extends JPanel {
    public BufferedImage portrait;
    public BufferedImage drawing;
	public BufferedImage image;
    public TextOverlay(ImageFile picture, TextFile book) {
		image = picture.getImage();
    	drawing = image;
		this.setPreferredSize(new Dimension(picture.getWidth(), picture.getHeight()));
        portrait = process(image, picture, book);
    	String fileName;
        if (book.getTitle().equals("")){
            fileName = (picture.getName() + "-" +book.getName() + "-lithograph");
    	}else{
    		fileName = book.getTitle();
    	}
    	File lithographOutput = new File(System.getProperty("user.home") + "/Desktop",fileName+".png");
    	try{
    		//Puts the image into lithographOutput
    		ImageIO.write(portrait, "png", lithographOutput);
    	}catch(IOException e){
    		//Switch this with a textbox
    		System.out.println("Write error for " + lithographOutput.getPath() + ": "+e.getMessage());
    	}
    }

    private BufferedImage process(BufferedImage old, ImageFile picture, TextFile book) {
		image = picture.getImage();
//    	BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_RGB);
        ArrayList<Character> text = book.textArray();
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        //        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font(book.getType(), Font.PLAIN, book.getFont()));
		int iadd = book.getFontWidth();
        int jadd = book.getFontHeight();
        int i = 0;
		int j = 8;
		char t;
		int index = 0;
		if (book.isPhrase()){
			while(true){
				char s = text.get(index);
				if (i>=picture.getWidth()){
					i=0;
					j += jadd;
					if(j>=picture.getHeight()){
						break;
					}
				}
				if (book.getCase()==0){
					g2d.drawString(Character.toString(s), 0+i, 0+j);
				}else if(book.getCase()==1){
					g2d.drawString(Character.toString(Character.toUpperCase(s)), 0+i, 0+j);
				}else if(book.getCase()==2){
					g2d.drawString(Character.toString(Character.toLowerCase(s)), 0+i, 0+j);
				}
				g2d.setColor(new Color(image.getRGB(i, j)));
				index++;
				index = index % text.size();
				i+=iadd;

			}
		}else{
			for (Character s: text){
				if (i>=picture.getWidth()-5){
					i=0;
					j += jadd;
					if(j>picture.getHeight()-10){
						break;
					}
				}
				if (book.getCase()==0){
					g2d.drawString(Character.toString(s), 0+i, 0+j);
				}else if(book.getCase()==1){
					g2d.drawString(Character.toString(Character.toUpperCase(s)), 0+i, 0+j);
				}else if(book.getCase()==2){
					g2d.drawString(Character.toString(Character.toLowerCase(s)), 0+i, 0+j);
				}				g2d.setColor(new Color(image.getRGB(i, j)));
				i+=iadd;
			}
		}
        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(portrait, 0, 0, null);
    }

//    private static void create() {
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.add(new TextOverlay());
//        f.pack();
//        f.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                create();
//            }
//        });
//    }
}