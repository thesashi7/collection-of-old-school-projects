package Tankgame;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class ConcreteWall extends GameObject
{
	private Collision collision;
	//private Explosion explosion;
	private Sound explSound;
	
	public ConcreteWall()
	{
		this.collision = new Collision();
		this.explosion = new Explosion(null);
		this.explSound = new Sound(this.getAudio("ResourcesTank/Chapter11/Explosion_small.wav"));
		
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_1.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_2.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_3.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_4.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_5.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_6.png");

		this.addSprite("ResourcesTank/Chapter11/spr_wall1.png");
	}

	
	public void draw(Graphics2D g2, ImageObserver imageObs, PlayerTank p1, PlayerTank p2 )
	{
	    Weapon weapon=null; 

		 for(int i=0; i< p1.firedWeapon.size(); i++)
		 {
		    weapon= p1.firedWeapon.get(i);
		    if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
		    {
			   if(weapon instanceof BouncingShell )
			   {
				   ((BouncingShell) weapon).reverse=true;
			   }else
			   {
				   p1.firedWeapon.remove(i);
				   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				   this.explSound.playAudioClip();
			   }
		    }
			 
		 }
		 
		// now checking if player Twos shot hit the walls
		for(int i=0; i< p2.firedWeapon.size(); i++)
		 {
			 weapon= p2.firedWeapon.get(i);
			 if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
			 {
			   if(weapon instanceof BouncingShell )
			   {
				  ((BouncingShell) weapon).reverse=true;
			   }else
			   {
			      p2.firedWeapon.remove(i);
				  this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				  this.explSound.playAudioClip();
				}
			  }
				 
		 }
		 
		 
		 //this below code was efficient than loop but wouldnt properly work for bouncing shells
		/*if(size>0)
		{
			weapon= p1.firedWeapon.get(0);
			if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
			{
				if(weapon instanceof BouncingShell )
				{
					((BouncingShell) weapon).reverse=true;
				}else
				{
				   p1.firedWeapon.remove(0);
				   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				   this.explSound.playAudioClip();
				}
			}
			else if(size>1)
			{
				weapon= p1.firedWeapon.get(1);
				if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
				{
					if(weapon instanceof BouncingShell )
					{
						((BouncingShell) weapon).reverse=true;
					}
					else
					{
					   p1.firedWeapon.remove(1);
					   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
					   this.explSound.playAudioClip();
					}
					
				}
				else if(size>2)
				{
					weapon= p1.firedWeapon.get(2);
					if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
					{
						if(weapon instanceof BouncingShell )
						{
							((BouncingShell) weapon).reverse=true;
						}
						else
						{
						   p1.firedWeapon.remove(2);
						   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
						   this.explSound.playAudioClip();
						}
						
					}
				}
			}
		}*/
			
		// now checking if player Twos shot hit the walls
		/*size = p2.firedWeapon.size();
		if(size>0)
		{
			weapon= p2.firedWeapon.get(0);
			if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
			{
				if(weapon instanceof BouncingShell )
				{
					((BouncingShell) weapon).reverse=true;
				}
				else
				{
				   p2.firedWeapon.remove(0);
				   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				   this.explSound.playAudioClip();
				}
			}
			else if(size>1)
			{
				weapon= p2.firedWeapon.get(1);
				if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
				{
					if(weapon instanceof BouncingShell )
					{
						((BouncingShell) weapon).reverse=true;
					}
					else
					{
					   p2.firedWeapon.remove(1);
					   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
					   this.explSound.playAudioClip();
					}
				}
				else if(size>2)
				{
					weapon= p2.firedWeapon.get(2);
					if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()))
					{
						if(weapon instanceof BouncingShell )
						{
							((BouncingShell) weapon).reverse=true;
						}
						else
						{
						   p2.firedWeapon.remove(2);
						   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
						   this.explSound.playAudioClip();
						}
							
					}
				}
			}
				
				
		}*/
		
		// explosion will be drawn only if this wall has collided with weapon	
	   this.explosion.draw(g2, imageObs, 0,0);
		
       g2.drawImage(this.obImage.get(0), this.x, this.y, 36, 36, imageObs);
		  
	}
	
	
	@Override
	public void update() {
		
		
	}

}
