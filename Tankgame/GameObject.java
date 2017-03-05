package Tankgame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;

/**
 * This abstract class GameObject is the super class for most of the objects in the game: Plane, Weapon, PowerUp, Explosion, Island, HealthBar, PlayersPanel . 
 * It has abstract method update().
 *
 */
public abstract class GameObject 
{
	protected int x,y, screenWidth, screenHeight;
	//protected Image objectImage,objectImage1, objectImage2;
	protected boolean visible;
	protected Vector<Image> obImage= new Vector<Image>();
	protected Explosion explosion;
	
	
	public GameObject()
	{
		this.visible=true;
		
	}
	
	/**
	 * Sets the horizontal position of this object in the game 
	 * @param a
	 */
	public void setX(int a)
	{
		this.x=a;
	}
	
	/**
	 * Sets the vertical position of this object in the game 
	 * @param b
	 */
	public void setY(int b)
	{
		this.y=b;
	}
	
	/**
	 * Sets both the horizontal (as a) and vertical position (as b) of this object in the game 
	 * @param a, b
	 */
	public void setPosition(int a, int b)
	{
		this.x=a;
		this.y=b;
	}
	
	/**
	 * Sets the image or sprite of this object.
	 * @param img
	 */
	public void setSprite(Image img)
	{
		//this.objectImage= img;
		this.obImage.add(img);
	}
	
	
	/**
	 * Sets the image or sprite of this object using the name of the file.
	 * @param img
	 */
	public void addSprite(String name)
	{
		this.obImage.add(this.getSprite(name));
	}
	

	/**
	 * Sets the visibility of this object in the game.
	 * @param visb
	 */
	public void setVisibility(boolean visb)
	{
		this.visible= visb;
	}
	
	/**
	 * Sets up an Explosion for this game object
	 * 
	 * @param ex
	 */
	public void setExplosion(Explosion ex)
	{
		this.explosion = ex;
	}
	
	
	public void addSprite(Image img)
	{
		obImage.add(img);
	}
	
    /**
     * 
     * @return the horizontal position of this object
     */
	public int getX()
	{
		return x;
	}
	
	/**
     * 
     * @return the vertical position of this object
     */
	public int getY()
	{
		return y;
	}
	
	/**
	 * 
	 * @return the width of this object in terms of the image.
	 */
	public int getWidth()
	{
	   if(obImage.size()>0) return obImage.get(0).getWidth(null);
	   return 0;
	}
	
	/**
	 * 
	 * @return the height of this object in terms of image.
	 */
	public int getHeight()
	{
	   if(obImage.size()>0) return obImage.get(0).getHeight(null);
	   return 0;	
	}
	
	/**
     * 
     * @return the image or sprite of this object
     */
	public Image getSprite(int i)
	{
		return this.obImage.get(i);
	}
	
	/**
	 * 
	 * @return true if this object is visible or else false
	 */
	public boolean isVisible()
	{
		return visible;
	}
	
	/**
	 * 
	 * @return true if this object is visible or else false
	 */
	public boolean isAlive()
	{
		return isVisible();
	}
	
	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		g.drawImage(this.obImage.get(0), x, y, imageObs);
	}
	
     public Image getSprite(String name) 
	 {
		   Image img=null;
		   
		   try{
		   URL url = GameObject.class.getResource(name);
	       img = ImageIO.read(url);
		   }
	      /* try {
	           MediaTracker tracker = new MediaTracker(this);
	           tracker.addImage(img, 0);
	           tracker.waitForID(0);
	       }*/
		   catch (Exception e) {
			   System.exit(1);
	       }
		   
	       return img;
	   }
     
     
    protected AudioClip getAudio(String name)
    {
    	AudioClip audio=null;
    	try
    	{ 
    	   URL url = GameObject.class.getResource(name);
    	   audio = Applet.newAudioClip(url);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("AUDIO EXCEPTION");
    		System.exit(1);
    	}
	   return audio;
    }

	/**
	 * Resets the state of the current object.
	 */
	public abstract void update();
	

}
