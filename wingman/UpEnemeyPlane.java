package Wingman;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.Vector;

import javax.swing.JApplet;

/**
 * This class UpEnemeyPlane is a subclass of Plane.
 * This is a plane that faces vertically upward and moves in this up direction.
 * @author thapaliya
 *
 */
public class UpEnemeyPlane extends Plane
{
	private int shootingSpeed;
	private int fireSpeedX, fireSpeedY;
	
	public UpEnemeyPlane()
	{
		   this.speed=3;
		   this.shootingSpeed=40;
		   this.fireSpeedX=5;
		   this.fireSpeedY=5;
		   this.firedWeapon = new Vector<Weapon>();
	  
	}
	
	
	public void update()
	{
		y-=speed;
	}
	
	public void update(int x, int y)
	{
		//x-=speed;
		y-=speed;
	}
	

	public void setShootingSpeed(int s)
	{
		this.shootingSpeed=s;
	}
	
	public void setBulletSpeed(int sX, int sY)
	{
		this.fireSpeedX=sX;
		this.fireSpeedY=sY;
		
	}
	
	/**
	 * Sets the ability of this plane to shoot or not shoot.
	 * @param val
	 */
	public void setShootability(boolean val)
	{
		this.canShoot=val;
		
		if(val==true)
		{
			this.weapon= new Bullet("Resources/enemybullet1.png");
			//this.weapon.setSprite();
			//this.weapon.setPosition(0,6);
			this.weapon.setSpeed(0, 6);
			
		}
	}
	
	@Override
	public void shoot() 
	{
		
		Weapon fired;
		if(this.getY()>0 && canFire())
		{
			//System.out.println("P"+this.number+" SHOOT______________________________________________________________");
			fired = this.getWeapon().fired( this.getX()+fireM, this.getY()+14);
			fired.setSpeed(this.fireSpeedX, this.fireSpeedY);
			firedWeapon.add(fired);
		}
		
	}
	
	public boolean canFire()
	{ 
		if(firedWeapon.size()>0)
		{
			weapon= (Weapon)firedWeapon.lastElement();
			
			if(this.getY()- weapon.getY() >=shootingSpeed) return true;
		    return false;
		}
		
		return true;
	}


	@Override
	public void draw(Graphics2D g, ImageObserver imageObs, GameObject o1, GameObject o2)
	{
		super.draw(g, imageObs);
		
		if(this.getShootability())
		{
			shoot();
			drawBullets(g, imageObs, (PlayerPlane)o1, (PlayerPlane)o2);
		}
		
		
	}
	
	
	public void drawBullets(Graphics2D g, ImageObserver imageObs, PlayerPlane p1, PlayerPlane p2)
	{
		Weapon fired;
		
		for(int i=0; i< this.firedWeapon.size(); i++)
		{
			fired = this.firedWeapon.get(i);
			if(fired.getY()< ((JApplet)imageObs).getHeight())
			{
				fired.update(0, 0);
				if(p1.collision(fired.x, fired.y, fired.getWidth(), fired.getHeight())); //fired.collided();
				if(p2.collision(fired.x, fired.y, fired.getWidth(), fired.getHeight()));
				
			   fired.draw(g, imageObs);
			   
			   this.firedWeapon.remove(i);
			   this.firedWeapon.add(i, fired);
			}
			else
			{
				this.firedWeapon.remove(i);
				i--;
			}
			
		}
	}
	

}
