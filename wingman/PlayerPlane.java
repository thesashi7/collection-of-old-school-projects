package Wingman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JApplet;

/**
 * This class PlayerPlane is a subclass of abstract class Plane. This is the player's plane that (s)he will control.
 * This plane has the ability to shoot and increasing health through powerUps.
 * @author thapaliya
 *
 */
public class PlayerPlane extends Plane implements Observer
{
	private int score,  number, time, index, powerUpBullets;
	private byte newPlane;
    private boolean upPress, downPress, leftPress, rightPress,fire;
    private  Plane inComingPlane[],boss;
    private Bullet diagnolBullet1, diagnolBullet2;
    private Vector<Weapon> diagnolBullets;
    
	public PlayerPlane(int n)
	{
		this.score=0;
		this.life=2;
		this.health=4;
		this.speed=2;
		this.number=n;
		this.fireM=16;
		this.setShootability(true);
		this.setWeapon(new Bullet("Resources/bullet.png"));
		this.firedWeapon= new Vector<Weapon>();
		this.time=0;
		this.visible=true;
	    this.inComingPlane = new Plane[50];
		this.newPlane=4; 
		this.powerUpBullets=0;
		this.diagnolBullet1 = new Bullet("Resources/bulletLeft.png");
		this.diagnolBullet1.setSpeed(4, 4);
		this.diagnolBullet2 = new Bullet("Resources/bulletRight.png");
		this.diagnolBullet2.setSpeed(-4, 4);
        this.diagnolBullets = new Vector<Weapon>();
      

	}
	

	
	
	public void setScore(int sc)
	{
		this.score=sc;
	}
	


	
	/**
	 * 
	 * @return the score of this plane.
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * 
	 * @return the health of this plane.
	 */
	public int getHitPoints()
	{
		return health;
	}
	
	
	public int getLife()
	{
		return life;
	}
	
	
	/**
	 * 
	 * @return a reference to the vector that contains all the fired weapons 
	 */
	public  Vector<Weapon> getFiredWeaons()
	{
		return firedWeapon;
	}
	
	public void checkCollision(ArrayList<Plane> enemyPlanes)
	{
		Plane enemyPlane;

	}
	
	public boolean collision(GameObject obj,int x, int y, int w, int h)
	{
        if((y+h > obj.getY()) &&(y < obj.getY()+obj.getHeight()  )) { // very simple test for showing an idea -- this only checks y forwarding direction
            if((x+w > obj.getX()) &&(x < obj.getX() + obj.getWidth() )) {
               return true;
            }
        
        }
        return false;
	}
	
	
	public boolean collision(int x, int y, int w, int h)
	{
        if((y+h > this.getY()) &&(y < this.getY()+this.getHeight() && this.isCollideable() )) { // very simple test for showing an idea -- this only checks y forwarding direction
            if((x+w > this.getX()) &&(x < this.getX() + this.getWidth() )) {
            	System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOCCCCCCCCCCCCCCCCCCCCCOOOOOOOOOOOOOOOOOOOOOOO");
            	collide++;
            	this.collidedWithOther++;
            	health--;
            	this.explosion.setVisibility(true);
            	this.colliderX=x;
            	this.colliderY=y;
               return true;
            }
        
        }
        return false;
	}
	
	
	
	@Override
	public void shoot() 
	{
		
		Weapon fired;
		if(canFire())
		{
			System.out.println("P"+this.number+" SHOOT______________________________________________________________");
			fired = this.getWeapon().fired( this.getX()+fireM, this.getY());
			fired.setSpeed(0,-4);
			fired.addSprite("Resources/bullet.png");
			firedWeapon.add(fired);
			
			if(this.powerUpBullets>0)
			{
				diagnolBullet1.setPosition(this.getX()+fireM, this.getY());
				diagnolBullet1.setSpeed(-4, -4);
				diagnolBullet1.addSprite("Resources/bulletLeft.png");
				firedWeapon.add(diagnolBullet1);
				
				
				diagnolBullet2.setPosition(this.getX()+fireM, this.getY());
				diagnolBullet2.setSpeed(4, -4);
				diagnolBullet2.addSprite("Resources/bulletRight.png");
				firedWeapon.add(diagnolBullet2);
				
				this.powerUpBullets--;
				
			}
		}
		
	}
	

	
 
	
	public boolean canFire()
	{ 
		if(firedWeapon.size()>0 )
		{
			if(this.powerUpBullets>0)
			{
				if(canDiagnolBulletFire() && this.getY()- weapon.getY() >=330) return true;
				return false;
			}
			weapon= (Weapon)firedWeapon.lastElement();
			
			if(this.getY()- weapon.getY() >=150) return true;
		    return false;
		}
		
		return true;
	}

	/**
	 * Enables a triple bullet shooting for the plane for the number of p
	 * @param p
	 */
	public void poweredUp(int p) {
		
		this.powerUpBullets+=p;
		
		
		
	}

	/**
	 * 
	 */
	public void update()
	{
		if(this.getShootability()==true && fire==true) this.shoot();
		if(upPress==true && y>0) this.y-=speed;
		if(downPress==true && y<this.screenHeight-60) this.y+=speed;
		if(leftPress==true && x>0) this.x-=speed;
		if(rightPress==true && x<this.screenWidth-60) this.x+=speed;
		
		if(life!=0 && health==0){ life--; health = 4; this.newPlane=0;}
		if(life==0){this.setVisibility(false); this.setShootability(false); this.setCollidability(false); health=0; }//destroy plane GAME OVER
	}

	public void updateRelease(int rls)
	{
		if(this.number==1)
		{
		   if(rls==KeyEvent.VK_SHIFT) upPress=false;
		   else if(rls==KeyEvent.VK_DOWN) downPress=false;
		   else if(rls==KeyEvent.VK_LEFT) leftPress=false;
		   else if(rls==KeyEvent.VK_RIGHT) rightPress=false;
		   else if (rls==KeyEvent.VK_ENTER) fire= false;
		}
		else
		{
			if(rls==KeyEvent.VK_W) upPress=false;
			else if(rls==KeyEvent.VK_S) downPress=false;
			else if(rls==KeyEvent.VK_A) leftPress=false;
			else if(rls==KeyEvent.VK_D) rightPress=false;
			else if(rls == KeyEvent.VK_SPACE) fire= false;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+rls);
	}
	
	
	@Override
	public void update(Observable o, Object args) {
		GameEvents ge = (GameEvents)args;
		
	    updateRelease(ge.release);
	    
		if(this.number==1) playerOnePress(ge.keyCode);
		else playerTwoPress(ge.keyCode);
		
		
	}
	
	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		screenWidth= ((JApplet)imageObs).getWidth();
		screenHeight=  ((JApplet)imageObs).getHeight();
		
		drawBullets(g, imageObs);
		update();
		
		if(this.newPlane ==4 &&  this.isAlive() )super.draw(g, imageObs);
		else newPlane++;
		
	}
	
	/*
	 * 
	 */
	public void drawBullets(Graphics2D g, ImageObserver imageObs)
	{
	
		
		for(int i=0; i< firedWeapon.size(); i++)
		{
			//System.exit(1);
			weapon= (Weapon)firedWeapon.get(i);
			
			//if(this.powerUpBullets<=0){ weapon.addSprite("Resources/bullet.png");} // to stop changing the sprite of the bullet 
			
			//if(weapon.vSpeed)
			if(weapon.getY()<=0 || weapon.getX()<=0 || weapon.getX()>=((JApplet)imageObs).getWidth()) 
			{
				firedWeapon.remove(i);
				i--;
			}
			else
			{
				if(weapon.getHSpeed()!=0) this.diagnolBullets.add(weapon);
				
		       weapon.draw(g, imageObs);
		       if( bulletCollideEnemyPlane(weapon))
		       {
		    	  // try{Thread.sleep(2000);}catch(Exception ex){}
		    	   firedWeapon.remove(i);
		    	   i--;
		       }
		       else
		       {
		    	  weapon.update(0,0);
		
			      firedWeapon.remove(i);
			      firedWeapon.add(i,weapon);
		       }
			}
		}
	
		
	}
	

	public boolean bulletCollideEnemyPlane(Weapon b)
	{
		for(int i=0;  this.inComingPlane[i]!=null; i++)
		{
			//System.exit(1);
			//System.out.println("HEALTH="+this.inComingPlane[i].health);
			if(this.inComingPlane[i].isAlive() && this.inComingPlane[i].collision(b.x, b.y, b.getWidth(), b.getHeight())){  
			System.out.println("Player bullet collided with plane "+i+" life ="+ this.inComingPlane[i].isVisible());
			this.score+=10;
			//try{Thread.sleep(2000);}catch(Exception ex){}
			//System.exit(1);
			//this.explosion.explodeSoundOnly();
			return true;
			}
		}
		
		if(boss!=null && boss.isVisible()&&  boss.collision(b.x, b.y, b.getWidth(), b.getHeight()))
		{
			score+=50;
			System.out.println("boss checked !");
			//System.exit(1);
			//try{Thread.sleep(2000);}catch(Exception ex){}
		
			//this.explosion.explodeSoundOnly();
			return true;
		}
		
		return false;
		
	}
	/**
	 * 
	 * @param event
	 */
	public void playerOnePress(int event)
	{
		
		switch(event)
		{
			case KeyEvent.VK_SHIFT:
			    //if(y>0 && System.currentTimeMillis()-moveTime >=1){y-=speed; moveTime=System.currentTimeMillis(); upPress=true;}
				if(y>0){upPress=true;}
			break;
			
			case KeyEvent.VK_DOWN:
			    //if(y<screenHeight-60 && System.currentTimeMillis()-moveTime >=1){y+=speed; moveTime=System.currentTimeMillis();downPress=true;}
				if(y<screenHeight-60){downPress=true;}
			break;
			
			case KeyEvent.VK_LEFT:
			   // if(x>0){x-=speed; moveTime=System.currentTimeMillis();leftPress=true;}
				if(x>0){leftPress=true;}
			break;
			
			case KeyEvent.VK_RIGHT:
			   //if(x < screenWidth-60){ x+=speed; moveTime=System.currentTimeMillis(); rightPress=true;}
				if(x < screenWidth-60){ rightPress=true;}
			break;
			
			case KeyEvent.VK_ENTER:
				fire=true;
				break;
				
			default:
				break;
		
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void playerTwoPress(int event)
	{
		
		switch(event)
		{
			case KeyEvent.VK_W:
			    //if(y>0 && System.currentTimeMillis()-moveTime >=1){y-=speed; moveTime= System.currentTimeMillis();upPress=true;}
				if(y>0){upPress=true;}
			break;
			
			case KeyEvent.VK_S:
			    //if(y< screenHeight-60 && System.currentTimeMillis()-moveTime >=1){y+=speed; moveTime=System.currentTimeMillis();downPress=true;}
				if(y< screenHeight-60){downPress=true;}
				break;
			
			case KeyEvent.VK_A:
			    //if(x>0 ){x-=speed; moveTime=System.currentTimeMillis(); leftPress=true;}
				if(x>0 ){ leftPress=true;}
			break;
			
			case KeyEvent.VK_D:
			   // if(x<screenWidth-60){ x+=speed; moveTime= System.currentTimeMillis(); rightPress=true;}
				if(x<screenWidth-60){rightPress=true;}
			break;
			
			case KeyEvent.VK_SPACE:
				fire=true;
			default:
				break;
		
		}
	}



	public void incomingEnemies(Plane releasedEnemy[])
	{

	    this.inComingPlane = releasedEnemy;
	  
	}
	
	public void incomingBoss(Plane b)
	{
		boss = b;
	}
	
	@Override
	public void draw(Graphics2D g, ImageObserver imageObs, GameObject o1,
			GameObject o2) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean canDiagnolBulletFire()
	{ 
		int h =0;
		int x=0;
		int y=0;
	    Weapon fired= this.diagnolBullets.lastElement();
	   
		
		x= fired.getX();
	    y= fired.getY();
		
		System.out.println("x="+x);
		System.out.println("y="+y);
		
		x = Math.abs(x- this.getX());
		y= Math.abs(y - this.getY());
		
		System.out.println("\n\nx="+x);
		System.out.println("y="+y);
		//System.exit(1);
		
		h =(int) Math.sqrt((x*x) +(y*y));
		
		System.out.println("h="+ h);
		
		//try{Thread.sleep(500);}catch(Exception ex){}
		if(h>=500) return true;
		return false;
		
	}

}
