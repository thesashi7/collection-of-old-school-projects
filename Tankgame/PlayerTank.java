package Tankgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JApplet;



public class PlayerTank extends Tank implements Observer 
{
	private int playerNumber,imageNumber,theta, changeOfAngle,velocityX, velocityY, faceX, faceY,fireTime, time, otherX, otherY, otherW, otherH, aimX, aimY, barelX,  barelY;
	private boolean up, down, left, right, sheildOn;
	public boolean ammoPicked, explode, reStartBattle;
	public ArrayList<Weapon> firedWeapon; 
	public int health, heavyAmmo, score, shieldT, timeShield;
	public TankWeapon weapon, shell;
	private Collision collision;
	private Explosion explosion, largeExplosion;
	public Sheild sheild;
	
	public PlayerTank(int n)
	{
		this.life=2;
		this.health=10;
		this.playerNumber=n;
		this.speed = 5;
		this.velocityX=5;
		this.velocityY=5;
		this.changeOfAngle=6;
		this.heavyAmmo=0;
		this.timeShield=250;
		this.shieldT=0;
		this.firedWeapon = new ArrayList<Weapon>();
		this.shell = new Shell();
		this.sheild = new Sheild(this.getSprite("ResourcesTank/Chapter11/Shield"+Integer.toString(playerNumber)+".png"));
		this.weapon=  shell; 
		this.explosion= new Explosion(this.getAudio("ResourcesTank/Chapter11/Explosion_small.wav"));
		
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_1.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_2.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_3.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_4.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_5.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_6.png");
		
		this.largeExplosion = new Explosion(this.getAudio("ResourcesTank/Chapter11/Explosion_large.wav"));
	}
	
	
	
	public void setCollision(Collision c)
	{
		this.collision = c;
	}
	
	public void setAngleAndImage(int a, int b)
	{
		this.theta=a;
		this.imageNumber=b;
	}
	
	/*
	 * Need to pass the other colliding objects x and y axis
	 */
	/*public void collidedWithObject(int oX, int oY, int oW, int oH)
	{
	   this.collidedWithObject=true;
	   this.otherX=oX;
	   this.otherY=oY;
	   this.otherW= oW;
	   this.otherH= oH;
	}
	
	public void collidedWithWeapon()
	{
		this.collidedWithWeapon=true;
	}
	*/
	
	
	/*
	 * Get whatever is picked up
	 */
	public void acceptPickUp(PickUp pick)
	{
		pick.picked(this);
	}
	
	/*
	 * Draw tank and fired weapons
	 */
	public void draw(Graphics2D g, ImageObserver imageObs, int bgWidth, int bgHeight, PlayerTank opPlayer)
	{
		
		update();
	
		drawFiredWeapons(g, imageObs, bgWidth, bgHeight, opPlayer);
		g.drawImage(this.obImage.get(this.imageNumber), this.x,this.y,  imageObs);
		
		if(this.explode)
		{
			this.explosion.explode(g, imageObs,this.otherX, this.otherY);
			this.explosion.explodeSoundOnly();
			this.explode=false;
		}
		
		this.explosion.draw(g, imageObs, 0,0);

		
		if(this.sheildOn && this.shieldT>0)
		{
		   this.sheild.draw(g, imageObs, this.x, this.y);
		   this.timeShield--;
		   if(this.timeShield<=0)
			{
			   this.sheildOn=false;
			   this.shieldT--;
			   this.timeShield=250;
			}
		   
		   
		}
		else this.sheildOn=false;
		time++;
	}
	
	
	public void drawFiredWeapons(Graphics2D g, ImageObserver imageObs, int bgWidth, int bgHeight, PlayerTank opPlayer)
	{
		TankWeapon weapon=null;
		
		for(int i =0; i< this.firedWeapon.size(); i++)
		{
			weapon= (TankWeapon)this.firedWeapon.get(i);
			
			
			
			if(weapon.getY()<=0 || weapon.getX()<=0 || Math.abs(weapon.getX()-weapon.initX)>=360 || Math.abs(weapon.getY()-weapon.initY)>=360|| !weapon.visible) 
			{
				firedWeapon.remove(i);
				i--;
			}
			else
			{
			 
			  if(this.collision.collision(weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight(), opPlayer.x, opPlayer.y, opPlayer.getWidth(), opPlayer.getHeight()))
			  {
				if(!opPlayer.sheildOn || opPlayer.shieldT<=0)
				{
					System.out.println("Exploedded");
				   opPlayer.acceptHit(weapon); 
				   if(opPlayer.health==10) this.score++; // this means that the other tank has just died so its health is full as the game restarts
				}
				else
				{
					
					this.explosion.explode(g, imageObs, weapon.x, weapon.y);
					this.explosion.explodeSoundOnly();
				}
				firedWeapon.remove(i);
				i--;
				
			  }
			  else
			  {	
	
		         weapon.draw(g, imageObs);
		         weapon.update(0,0);
		         firedWeapon.remove(i);
			     firedWeapon.add(i,weapon);
			  }
		      
			}
		}
	}

	
	@Override
	public void update() 
	{
		
		if(up) moveForward();
		if(down) moveBackward();
		if (left)
		{
			if(this.imageNumber==59)
			{
				this.imageNumber=0;
			}
			else this.imageNumber++; 
			theta+=6;
			if(theta==360) theta=0;
		}
		if (right)
		{
			if(this.imageNumber==0)this.imageNumber=59;
			else this.imageNumber--; 
			theta-=6;
			if(theta==-360) theta=0;
		}
		

		
	}

	public void updateRelease(int rel)
	{
		if(this.playerNumber==1)
		{
			if(rel == KeyEvent.VK_W) up=false;
			else if(rel == KeyEvent.VK_S) down=false;
			else if(rel == KeyEvent.VK_A) left=false;
			else if (rel == KeyEvent.VK_D) right = false;
		}
		else
		{
			if(rel == KeyEvent.VK_SHIFT) up=false;
			else if(rel == KeyEvent.VK_DOWN) down=false;
			else if(rel == KeyEvent.VK_LEFT) left=false;
			else if (rel == KeyEvent.VK_RIGHT) right = false;
		}

		
	}
	
	
	@Override
	public void update(Observable o, Object args) {
	
		updateRelease(((GameEvents)o).release);
		
		if(this.playerNumber==1) playerOnePress(((GameEvents)o).keyCode);
		else playerTwoPress(((GameEvents)o).keyCode);
		
	}
	
	public void playerOnePress(int key)
	{
		switch(key)
		{
			case KeyEvent.VK_W:
				this.up=true;
				break;
			case KeyEvent.VK_A:
				this.left=true;
				break;
			case KeyEvent.VK_S:
				this.down=true;
				break;
			case KeyEvent.VK_D:
				this.right=true;
				break;
			case KeyEvent.VK_SPACE:
				this.shoot();
				break;
			case KeyEvent.VK_CONTROL:
				this.sheildOn=true;
		}
	}

	public void playerTwoPress(int key)
	{
		switch(key)
		{
			case KeyEvent.VK_SHIFT:
				this.up=true;
				break;
			case KeyEvent.VK_LEFT:
				this.left=true;
				break;
			case KeyEvent.VK_DOWN:
				this.down=true;
				break;
			case KeyEvent.VK_RIGHT:
				this.right=true;
				break;
			case KeyEvent.VK_ENTER:
				this.shoot();
				break;
			case KeyEvent.VK_P:
				this.sheildOn=true;
				break;
		}
		
	}
	
	@Override
	public void shoot() 
	{
		int h=0, p=0;
		
		p= this.getHeight()/3;
		try
		{
		   h = this.getWidth()/ (int)(Math.cos(this.theta)*1.5);
		}
		catch(ArithmeticException ae)
		{
		   h=0;
		}
		p += h* (int)Math.sin(this.theta)*2;
		
		
		int dX=0,dY=0, r=0;
		dX= this.x+(this.getWidth()/2);
		dY= this.y+ (this.getHeight()/2);
		r= (int)Math.atan2(x, y);
		
		faceX = this.x+(int)(Math.cos(r)*(this.getWidth()/2));
		
		
		
		
		if(this.time-this.fireTime>10 || time==0 )
		{
		   Weapon weapon = null;

		   if(this.theta==270 || this.theta==-90) faceX-=3;
		   weapon = this.weapon.fired(faceX, this.y+p, this.theta);
		
	       weapon.setSpeed(this.velocityX+2, this.velocityY+2);

		
		   this.firedWeapon.add(weapon);
		
		
		   this.fireTime = this.time;
		
		   if(this.ammoPicked) 
		   {
			  this.heavyAmmo--;
			  if(this.heavyAmmo==0)
			  { 
				 this.ammoPicked=false;
				 this.weapon=this.shell;
			  }
		   }
		}
		
		
		
	}
	
	/**
	 * Moves the tank forward in the direction that it is facing
	 */
	public void moveForward()
	{
		int temX, temY;
		temX = this.x+(int)Math.round(this.velocityX*Math.cos(theta * Math.PI/180)*1.71);
		temY = this.y -(int)Math.round(this.velocityY*Math.sin(theta *Math.PI/180)*1.71);
		
		if(collision.checkCollisionWithWalls(temX, temY, this.getWidth(), this.getHeight()))
		{
		   temX=this.x-(int)Math.round(this.velocityX*Math.cos(theta * Math.PI/180)*1.71);
		   temY= this.y+(int)Math.round(this.velocityY*Math.sin(theta *Math.PI/180)*1.71);
		}
		
		this.x =temX;
		this.y = temY;
			
	}
	
	/**
	 * Moves the tank backward in the direction that it is facing
	 */
	public void moveBackward()
	{
		int temX, temY;
		temX= this.x-(int)Math.round(this.velocityX*Math.cos(theta * Math.PI/180)*1.71);
		temY= this.y+ (int) Math.round(this.velocityY*Math.sin(theta *Math.PI/180)*1.71);
	
		if(collision.checkCollisionWithWalls(temX, temY, this.getWidth(), this.getHeight()))
		{	
			temX=this.x+(int)Math.round(this.velocityX*Math.cos(theta * Math.PI/180)*1.71);
			temY= this.y-(int)Math.round(this.velocityY*Math.sin(theta *Math.PI/180)*1.71);
		}
	
		
		this.x = temX;
		this.y = temY;
			
	}
	
	public void acceptHit(TankWeapon w)
	{
		this.health-= w.damage;
		if(this.health<=0)
		{
		   health=10;
		   this.reStartBattle=true;
		   this.largeExplosion.explodeSoundOnly();
		   this.life--;
		   if(life<=0) this.visible=false;
		}
		this.explode=true;
		this.otherX= w.x;
		this.otherY=w.y;
		this.otherW = w.getWidth();
		this.otherH= w.getHeight();
		
	}
	
}
