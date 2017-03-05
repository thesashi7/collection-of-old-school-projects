package Tankgame;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JApplet;

public class Background extends GameObject
{

	//Image backgroundImage;
	int move=0, speed=2;
	AudioClip music;
	

	public Background()
	{
		//this.backgroundImage= this.getSprite("ResourcesTank/Chapter11/Background.png");
		this.addSprite("ResourcesTank/Chapter11/Background.png");
	}
	

	
	public void setMusic(AudioClip audio)
	{
		music= audio;
	}
	
	

	
	// generates a new color with the specified hue
	public void drawBackGround(int w, int h, Graphics2D g2, JApplet ap) {
	   
		int TileWidth, TileHeight, NumberX, NumberY;
	    
		TileWidth = this.obImage.get(0).getWidth(ap);
	    TileHeight = this.obImage.get(0).getHeight(ap);

	    NumberX = (int) (w/ TileWidth);
	    NumberY = (int) (h / TileHeight);
        
       
	  
	    for (int i = -1; i <= NumberY; i++) {
	        for (int j = 0; j <= NumberX; j++) {
	            g2.drawImage(this.obImage.get(0), j * TileWidth, i * TileHeight + (move % TileHeight), TileWidth, TileHeight, ap);
	        }
	    }
	    
	    
	  
	}
	
	public void playMusic()
	{
		music.loop();
	}

	public void stopMusic()
	{
		music.stop();
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


}
