package Tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Genrates various types of weapons and health for the players to  pick up.
 * @author thapaliya
 *
 */
public class ObjectController 
{
	private Collision collision;
	private PickUp[] pickUps;
	private int timer,num, x,y,width, height;
	private Image pickStrip;
	
	public ObjectController(Collision col, BufferedImage pS )
	{
		this.collision = col;
		this.pickUps = new PickUp[16];
		this.timer=0;
		this.num=0;
		this.x=0;
		this.y=0;
		this.pickStrip= pS;
		this.width = this.pickStrip.getWidth(null)/4;
		this.height = this.pickStrip.getHeight(null);
		
		initPickUps();
	}
	
	/*
	 * inititalizes pickups 
	 */
	public void initPickUps()
	{
		ArrayList<PickUp> pUp =  new ArrayList<PickUp>();
		
		for(int i=0; i< pickUps.length; i++)
		{
			if(i<4)
			{
				pickUps[i] = new RocketPickUp();
				pickUps[i].addSprite(this.getPickUpSprite(0));
				pUp.add(pickUps[i]);
			}
			else if(i<8)
			{
				pickUps[i] = new SheildPickUp();
				pickUps[i].addSprite(this.getPickUpSprite(2));
				pUp.add(pickUps[i]);
			}
			else if(i<12)
			{
				pickUps[i] = new BounceRocketPickUp();
				pickUps[i].addSprite(this.getPickUpSprite(1));
				pUp.add(pickUps[i]);
			}
			else if(i<16)
			{
				pickUps[i] = new HealthPickUp();
				pickUps[i].addSprite(this.getPickUpSprite(3));
				pUp.add(pickUps[i]);
			}
		}
		
		Collections.shuffle(pUp);
		this.pickUps= pUp.toArray(this.pickUps);
		
	}
	
	
	public void draw(Graphics2D g2, ImageObserver imageObs, int bgWidth, int bgHeight, PlayerTank p1, PlayerTank p2)
	{
		//draw the pickups that are visible
		for(int i=0; i< this.pickUps.length; i++)
		{
			if(this.pickUps[i].visible)
			{
				this.pickUps[i].draw(g2, imageObs);
				if(collision.collision(this.pickUps[i].x, this.pickUps[i].y, this.pickUps[i].getWidth(), this.pickUps[i].getHeight(), p1.x, p1.y, p1.getWidth(), p1.getHeight()))
				{
					p1.acceptPickUp(this.pickUps[i]);
					this.pickUps[i].visible=false;
					this.pickUps[i].timer=0;
				}
				else if(collision.collision(this.pickUps[i].x, this.pickUps[i].y, this.pickUps[i].getWidth(), this.pickUps[i].getHeight(), p2.x, p2.y, p2.getWidth(), p2.getHeight()))
				{
					p2.acceptPickUp(this.pickUps[i]);
					this.pickUps[i].visible=false;
					this.pickUps[i].timer=0;
				}
			}
		}
		
		//Genreate pickups on timely manner in the empy spaces in the map
		if(this.timer>20)
		{
		   x= (int)((Math.random()*bgWidth));
		   y= (int)(Math.random()*bgHeight);
		
		   if(!collision.checkCollisionWithWalls(x, y, width, height) && !this.pickUps[num].visible)
		   {
			  this.pickUps[num].visible=true;
			  this.pickUps[num].setPosition(x, y);
			  this.pickUps[num].timer=0;
			  this.num++;
		   }
		
		   if(this.num>15) this.num=0;
		
		   this.timer=0;
		}
		else this.timer++;
	}
	
	private Image getPickUpSprite(int num)
	{
		return ((BufferedImage)this.pickStrip).getSubimage(num*width, 0, width, height);
	}

}
