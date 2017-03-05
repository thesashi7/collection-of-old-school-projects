package Tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.JApplet;

/**
 * This class Healthbar is a subclass of GameObject.
 * And, this can be used to display a healthbar of an object in a game given its health.
 * And, it is recommended to use the initial or maximum health of the object that this health bar will represent to be a multiple
 * of the number of the images or sprites in the HealthBar or else there is no guarantee that this healthbar will work right.
 * @author thapaliya
 *
 */
public class HealthBar extends GameObject
{

	int health, ratioOfHealthAndImages, bar;

	public void update(int hlt)
	{
		if(hlt>0 ) bar =  (health-hlt)/ratioOfHealthAndImages;
	}
	
	
	/**
	 * Health should be greater or equal to the number of images of healthbar.
	 * @param h
	 */
	public void setHealthForHB(int h)
	{
		health=h;
		ratioOfHealthAndImages= h/obImage.size();
	}
	
	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs)
	{ 
		if(bar< obImage.size() && bar>=0)g.drawImage(obImage.elementAt(bar), x, y, imageObs);
	}
	
	
	

}
