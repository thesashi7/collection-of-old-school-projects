package Wingman;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.Vector;
import javax.swing.JApplet;
/**
 * This class DownEnemeyPlane is a subclass of Plane.
 * This is a plane that faces vertically downward and moves in this down direction.
 * @author thapaliya
 *
 */
public class DownEnemeyPlane extends Plane
{
	
    private int shootingSpeed;
	private int fireSpeedX, fireSpeedY;
	
	public DownEnemeyPlane()
	{
	   this.speed=2;	
	   this.fireM=0;
	   this.shootingSpeed=20;
	   this.firedWeapon = new Vector<Weapon>();
	   this.health=1;
	   this.life=1;
	   this.fireSpeedX=4;
	   this.fireSpeedY=4;
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
			this.weapon.setSpeed(0, 5);
			
		}
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
	
	public void update()
	{
		y+=speed;
		if(this.getShootability()) this.shoot();
	}
	
	public void update(int x, int y)
	{
		this.y+=speed;
		//this.x+=speed;
		//if(y<=this.y) this.y=10;
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

	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs, GameObject o1, GameObject o2)
	{
		/*if(time==0){g.drawImage(this.objectImage, x, y, imageObs); time=1;}
		else if(time ==1 && objectImage1!=null){g.drawImage(this.objectImage1, x, y, imageObs); time=2;}
		else if(time ==2 && objectImage2!=null){g.drawImage(this.objectImage2, x, y, imageObs); time=0;}*/
		super.draw(g, imageObs);
		
		if(this.getShootability() ){shoot(); drawBullets(g, imageObs, (PlayerPlane)o1, (PlayerPlane)o2); }
		
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
				if(p1.collision(fired.x, fired.y, fired.getWidth(), fired.getHeight())||p2.collision(fired.x, fired.y, fired.getWidth(), fired.getHeight()))
				{
					this.firedWeapon.remove(i);
					i--;
				}
				else
				{
				
			      fired.draw(g, imageObs);
			   
			      this.firedWeapon.remove(i);
			      this.firedWeapon.add(i, fired);
			    }
			}
			else
			{
				this.firedWeapon.remove(i);
				i--;
			}
			
		}
		
		
	}



}
