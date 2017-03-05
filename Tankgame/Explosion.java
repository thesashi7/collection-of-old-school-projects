package Tankgame;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.sound.sampled.Clip;


public class Explosion extends GameObject
{
	private Object aSound;
	private int count;
	
	
	public Explosion(AudioClip sound)
	{
		this.count=0;
		this.aSound=(AudioClip)sound;
		this.x=-100;
		this.y=-100;
		//if(this.aSound==null)System.exit(1);
	}
	

	
	/**
	 * Sets the sound of Explosion to the s.
	 * @param s
	 */
	public void setSound(Object sound)
	{
		
		 aSound = sound;
	}
	
	
	/**
	 * 
	 * @return true if this object can currently show the explosion effect or else false
	 */
	public boolean canExplode()
	{
		if(count< this.obImage.size() && this.isVisible()) return true;
		return false;
	}
	
	
	/**
	 * Sets the position of the explode to a and b.
	 * Then draw needs to be called explicitly to actually see the explosion.
	 * @param a
	 * @param b
	 */
	public void explode(Graphics2D g, ImageObserver imageObs, int a, int b)
	{
	   this.x=a;
	   this.y=b;
	   
		
		if(!this.isVisible() ){//System.out.println("count ="+ count+" isVisible = " + this.isVisible()); 
		  // try{Thread.sleep(200);}catch(Exception ex){}
		}
		
		if(count<this.obImage.size() && this.isVisible() )
		{
			if(count==0)
			{
				System.exit(1);
				if(aSound!=null)((AudioClip)aSound).play();
				
			}
			//System.out.println("x="+x+"    y="+y +"  this is "+this);
			//try{Thread.sleep(2000);}catch(Exception ex){}
			g.drawImage(this.obImage.elementAt(count), x, y, imageObs);
			//System.exit(1);
			count++;
		}
		
		if(count==this.obImage.size())
		{ 
			count=0;
			this.setVisibility(false);
		}
	}
	
	
	public void explodeSoundOnly()
	{
		((AudioClip)this.aSound).play();
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 
	 * @param g
	 * @param imageObs
	 * @param a
	 * @param b
	 */
	public void draw(Graphics2D g, ImageObserver imageObs, int a, int b)
	{

		
		if(count<this.obImage.size())
		{
			g.drawImage(this.obImage.elementAt(count), x, y, imageObs);
			count++;
		}
	
		
	}

	
	public void reset()
	{
		this.count=0;
		this.setVisibility(true);
		
	}

}
