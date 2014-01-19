import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class ImageFile {
	private BufferedImage image;
	private String name;
	private int nonWhitePixels;
	private int whitePixels;
	private int picHeight;
	private int picWidth;
	public ImageFile(File imageFile){
		String fileName = imageFile.getName();
		int lastPeriod1 = fileName.lastIndexOf('.');
		fileName = fileName.substring(0,lastPeriod1);
		name = fileName;

		try{
			//Puts the image into lithographOutput
			image = ImageIO.read(imageFile);
		}catch(IOException e){
			//Switch this with a textbox
			System.out.println("Write error");
		}
		picHeight = image.getHeight();
		picWidth = image.getWidth();

		for (int height = 0; height<picHeight;height++){
			for (int width = 0; width<picWidth;width++){
				final int clr = image.getRGB(width, height);
				final int red = (clr & 0x00ff0000) >> 16;
			final int green = (clr & 0x0000ff00) >> 8;
			final int blue = clr & 0x000000ff;
			if (red>245 && blue>245 && green>245){
				whitePixels++;
			}else{
				nonWhitePixels++;
			}
			}
		}
	}
	public int numPixels(){
		return nonWhitePixels;
	}
	public int numWhitePixels(){
		return whitePixels;
	}
	public BufferedImage getImage(){
		return image;
	}
	public String getName(){
		return name;
	}
	public int getHeight(){
		return picHeight;
	}
	public int getWidth(){
		return picWidth;
	}
}