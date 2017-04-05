package Wingman;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.JApplet;

public class BackGround 
{
	Image backgroundImage;
	int move=0, speed=2;
	AudioClip music;
	

	public void setImage(Image bg)
	{
		backgroundImage = bg;
	}
	
	public void setMusic(AudioClip audio)
	{
		music= audio;
	}
	
	
	public Image getImage()
	{
		return backgroundImage;
	}
	
	// generates a new color with the specified hue
	public void drawBackGroundWithTileImage(int w, int h, Graphics2D g2, JApplet ap) {
		  
	    
		int TileWidth = backgroundImage.getWidth(ap);
	    int TileHeight = backgroundImage.getHeight(ap);

	    int NumberX = (int) (w / TileWidth);
	    int NumberY = (int) (h / TileHeight);

	    Image Buffer = ap.createImage(NumberX * TileWidth, NumberY * TileHeight);
	    //Graphics BufferG = Buffer.getGraphics();
        

	    for (int i = -1; i <= NumberY; i++) {
	        for (int j = 0; j <= NumberX; j++) {
	            g2.drawImage(backgroundImage, j * TileWidth, i * TileHeight + (move % TileHeight), TileWidth, TileHeight, ap);
	        }
	    }
	    move += speed;
	}
	
	public void playMusic()
	{
		music.loop();
	}

	public void stopMusic()
	{
		music.stop();
	}
}
