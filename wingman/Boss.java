package Wingman;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.Vector;

import javax.swing.JApplet;

public class Boss extends Plane{

	private int shootingSpeed;
	private HealthBar healthBar;
	private Explosion explosion2,explosion3, explosion4;
	
	public Boss()
	{
		this.canShoot=true;
		this.canCollide=true;
		this.life=1;
		this.health=28;
		this.firedWeapon = new Vector<Weapon>();
		this.shootingSpeed=20;
		this.healthBar= new HealthBar();
		this.fireM=15;
		initHealthBar();
		initExplosion();
		this.setPosition(-500, -200);
	}
	
	/**
	 * Intitialises the health bar of this boss
	 */
	public void initHealthBar()
	{
		this.healthBar.setPosition(1, 1);
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_1.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_2.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_3.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_4.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_5.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_6.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_7.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_8.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_9.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_10.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_11.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_12.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_13.png"));
		this.healthBar.addSprite(getSprite("Resources/bHealthBar_14.png"));
		this.healthBar.setHealthForHB(this.health);
		
	}
	
	/**
	 * Initializes the (when the boss is in danger to die) explosions of this boss
	 */
	public void initExplosion()
	{
		this.explosion2 = new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		this.explosion3 = new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		this.explosion4 = new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_1.png"));
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_2.png"));
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_3.png"));
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_4.png"));
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_5.png"));
		this.explosion2.addSprite(this.getSprite("Resources/explosion1_6.png"));
		this.explosion2.setVisibility(true);
		
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_1.png"));
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_2.png"));
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_3.png"));
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_4.png"));
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_5.png"));
		this.explosion3.addSprite(this.getSprite("Resources/explosion1_6.png"));
		this.explosion3.setVisibility(true);
		
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_1.png"));
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_2.png"));
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_3.png"));
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_4.png"));
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_5.png"));
		this.explosion4.addSprite(this.getSprite("Resources/explosion1_6.png"));
		this.explosion4.setVisibility(true);
		
		
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
	
	public void update(int x, int y)
	{
		this.x+=this.speed;
		this.y+= this.speed;
	}
	
	@Override
	public void shoot() {
        Weapon fired;
		if(this.getY()>0 && canFire())
		{
			//System.out.println("P"+this.number+" SHOOT______________________________________________________________");
			fired = this.getWeapon().fired( this.getX()+fireM, this.getY()+50);
			fired.setSpeed(0, 6);
			firedWeapon.add(fired);
			
			fired = this.getWeapon().fired( this.getX()+this.obImage.get(0).getWidth(null)-60, this.getY()+50);
			fired.setSpeed(0, 6);
			firedWeapon.add(fired);
			
			
			fired = this.getWeapon().fired( this.getX()+this.obImage.get(0).getWidth(null)-60, this.getY()+50);
			fired.setSpeed(6, 6);
			firedWeapon.add(fired);
			
			fired = this.getWeapon().fired( this.getX()+fireM, this.getY()+50);
			fired.setSpeed(-6, 6);
			firedWeapon.add(fired);
			
			if(this.health<=14)
			{
				fired = this.getWeapon().fired( this.getX()+fireM, this.getY()+50);
				fired.setSpeed(-2, 3);
				firedWeapon.add(fired);
				
				
				fired = this.getWeapon().fired( this.getX()+this.obImage.get(0).getWidth(null)-60, this.getY()+50);
				fired.setSpeed(2, 3);
				firedWeapon.add(fired);
				
				fired = this.getWeapon().fired( this.getX()+fireM, this.getY()+50);
				fired.setSpeed(-5, 5);
				firedWeapon.add(fired);
				
				
				fired = this.getWeapon().fired( this.getX()+this.obImage.get(0).getWidth(null)-60, this.getY()+50);
				fired.setSpeed(5, 5);
				firedWeapon.add(fired);
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g, ImageObserver imageObs, GameObject o1, GameObject o2) {
		
		System.out.println("Boss DRAW");
		//try{Thread.sleep(2000);}catch(Exception e){}
		if(this.isAlive())super.draw(g, imageObs);
		
		//g.drawImage(this.objectImage, x, y, imageObs);
		if(health<14)
		{   
			
			this.explosion2.explode(g, imageObs, this.x+20, this.y+45);
			this.explosion3.explode(g, imageObs, this.x+80, this.y+30);
			this.explosion4.explode(g, imageObs, this.x+30, this.y+60);
		}
		//else
		//{
		 if(health>=0)
		 {
	        this.healthBar.update(this.health);
		    this.healthBar.draw(g, imageObs);
			System.out.println("YES SHOULD BE DRAWN");
			if(!this.explosion.canExplode() )
			{ 
				this.explosion.reset(); 
				this.collide=0;
			}
		 }
		//}
		
		
		if(this.getShootability()){shoot(); drawBullets(g, imageObs, (PlayerPlane)o1, (PlayerPlane)o2); }
		
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
	
	
	public boolean isAlive()
	{
		if(life>0) return true;
		return false;
	}
	
	public void update(ImageObserver iamgeObs)
	{}

}
