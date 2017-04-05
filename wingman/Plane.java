package Wingman;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.Vector;

/**
 * This abstract class Plane is the super class of all the other planes in the game: PlayerPlane, EnemyPlane, EnemyBoss.
 * It has abstract method shoot().
 *
 */
public abstract class Plane extends GameObject
{
	protected Weapon weapon;
	protected boolean canShoot,canCollide;
	protected int speed,time,collide,fireM, life, health, colliderX, colliderY, collidedWithOther;
	protected Vector<Weapon> firedWeapon;
	

	
	public Plane()
	{
		this.time=0;
		this.canCollide=true;
		this.explosion= new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		this.collidedWithOther=0;
		//Adding sprites to explosion so that this is displayed when explosion is drawn
		this.explosion.addSprite(this.getSprite("Resources/explosion1_1.png"));
		this.explosion.addSprite(this.getSprite("Resources/explosion1_2.png"));
		this.explosion.addSprite(this.getSprite("Resources/explosion1_3.png"));
		this.explosion.addSprite(this.getSprite("Resources/explosion1_4.png"));
		this.explosion.addSprite(this.getSprite("Resources/explosion1_5.png"));
		this.explosion.addSprite(this.getSprite("Resources/explosion1_6.png"));
		this.explosion.setVisibility(true);
		if(this.explosion==null) System.exit(1);
	}
	

	/**
	 * Sets the weapon of this plane object as w.
	 * @param w
	 */
	public void setWeapon(Weapon w)
	{
		this.weapon=w;
	}
	
	/**
	 * Sets the ability of this plane to shoot or not shoot.
	 * @param val
	 */
	public void setShootability(boolean val)
	{
		this.canShoot=val;
	}
	
	/**
	 * Sets the ability of this plane to shoot or not shoot.
	 * @param val
	 */
	public void setCollidability(boolean val)
	{
		this.canCollide=val;
	}
	

	/**
	 * Sets the speed of the plane
	 * @param speed of the plane as s
	 */
	public void setSpeed(int s)
	{
		this.speed=s;
	}
	
	/**
	 * 
	 * @return the weapon of this plane.
	 */
	public Weapon getWeapon()
	{
		return this.weapon;
	}

	/**
	 * 
	 * @return true if this plane can shoot or else false
	 */
	public boolean getShootability()
	{
		return this.canShoot;
	}
	
	public void reset()
	{
		this.collide=0;
		this.life=1;
		this.health=1;
		this.collidedWithOther=0;
		this.explosion.setVisibility(true);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isCollideable()
	{
		return this.canCollide;
	}
	
	@Override
	public void update()
	{
		this.y+=speed;
	}
	
	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		if(this.collide==0)
		{
			if(time==0){g.drawImage(this.obImage.get(0), x, y, imageObs); time=1;}
			else if(time ==1 && obImage.size()>0){g.drawImage(this.obImage.get(1), x, y, imageObs); time=2;}
			else if(time ==2 && obImage.size()>0){g.drawImage(this.obImage.get(2), x, y, imageObs); time=0;}
			else{System.out.println("NOT DRAWN!!!!!!!");}
		}
		else
		{
			//try{Thread.sleep(300);}catch(Exception ex){}
			
			System.out.println("CollideWithOtherNumber ====>"+ this.collidedWithOther);
			
			if(this.explosion!=null && this.collidedWithOther!=0)this.explosion.explode(g, imageObs, this.colliderX, this.colliderY);
			//else if(life<=0)System.exit(1);
		    System.out.println("Should explode"+this);
			if(this.life>0)
			{
				if(time==0){g.drawImage(this.obImage.get(0), x, y, imageObs); time=1;}
				else if(time ==1 && obImage.size()>0){g.drawImage(this.obImage.get(1), x, y, imageObs); time=2;}
				else if(time ==2 && obImage.size()>0){g.drawImage(this.obImage.get(2), x, y, imageObs); time=0;}
			}
		}
		
	}
	
	/*
	 * Sets collide flag up.
	 */
	public void collided()
	{
	  System.out.println("health ==>"+health);
      health--;
      if(health==0) life--;
	  collide++;
	  if(life==0){ this.x=-22; this.y=-22; this.visible=false;}
	  System.out.println("health="+health+" life="+life);
	  //try{Thread.sleep(5000);}catch(Exception ex){}
	}
	
	
	public boolean collision(int x, int y, int w, int h)
	{
        if((y+h > this.getY()) &&(y < this.getY()+this.getHeight() && this.isCollideable() )) { // very simple test for showing an idea -- this only checks y forwarding direction
            if((x+w > this.getX()) &&(x < this.getX() + this.getWidth() )) {
            	System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOCCCCCCCCCCCCCCCCCCCCCOOOOOOOOOOOOOOOOOOOOOOO");
            	collide++;
            	health--;
            	collidedWithOther++;// needed when  enemy collides with bullet 
            	this.colliderX=x;
            	this.colliderY=y;
            	if(health==0)life--;
            	if(life==0){ this.x=-22; this.y=-22; this.visible=false;}
            	
                return true;
            }
        
        }
        return false;
	}
	/**
	 * 
	 * @return true if this plane has collided or else false.
	 
    public boolean hascollided()
    {
    	if(collide!=0) return false;
    	return true;
    }*/
	
    public  String getName()
    {
    	return null;
    }
    
    public boolean isAlive()
    {
    	if(life<=0) return false;
    	return true;
    }
    


    
	/**
	 * Shoots a bullet with the class type Weapon if this plane contains a weapon and has the ability to shoot. 
	 */
	public abstract void shoot();
	
	/**
	 * 
	 */
	public abstract void draw(Graphics2D g, ImageObserver imageObs, GameObject o1, GameObject o2);
	
	

	
}
