package Tankgame;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class DestroyableWall extends GameObject
{
	private Collision collision;
	//private Explosion explosion;
	private int timeToRegenerate;
	private Sound explSound;
	
	public DestroyableWall()
	{
		this.collision = new Collision();
		this.explosion = new Explosion(null);
		this.timeToRegenerate=0;
		this.explSound = new Sound(this.getAudio("ResourcesTank/Chapter11/Explosion_small.wav"));
		
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_1.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_2.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_3.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_4.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_5.png");
		this.explosion.addSprite("ResourcesTank/Chapter11/expl_small_6.png");
		
		this.addSprite("ResourcesTank/Chapter11/spr_wall2.png");
	}
	
	
	public void draw(Graphics2D g2, ImageObserver imageObs, PlayerTank p1, PlayerTank p2 )
	{
	    Weapon weapon=null; 

		 for(int i=0; i< p1.firedWeapon.size(); i++)
		 {
		    weapon= p1.firedWeapon.get(i);
		    if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()) && this.visible)
		    {
			   if(weapon instanceof BouncingShell )
			   {
				   ((BouncingShell) weapon).reverse=true;
			   }else
			   {
				   p1.firedWeapon.remove(i);
				   this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				   this.explSound.playAudioClip();
				   this.visible=false;
			   }
		    }
			 
		 }
		 
		// now checking if player Twos shot hit the walls
		 for(int i=0; i< p2.firedWeapon.size(); i++)
		 {
			 weapon= p2.firedWeapon.get(i);
			 if(collision.collision(this.x,this.y, this.getWidth(), this.getHeight(), weapon.x, weapon.y, weapon.getWidth(), weapon.getHeight()) && this.visible)
			 {
			   if(weapon instanceof BouncingShell )
			   {
				  ((BouncingShell) weapon).reverse=true;
			   }else
			   {
			      p2.firedWeapon.remove(i);
				  this.explosion.explode(g2, imageObs,weapon.x+5 , weapon.y+5);
				  this.explSound.playAudioClip();
				  this.visible=false;
				}
			  }
				 
		 }
		
		
		// explosion will be drawn only if this wall has collided with weapon	
		this.explosion.draw(g2, imageObs, 0,0);
		
		// only draw if this wall is visible or hasnt been destroyed
		if(this.visible)g2.drawImage(this.obImage.get(0), this.x, this.y, 36, 36, imageObs);
		else
		{
		   this.timeToRegenerate++;
		   if(this.timeToRegenerate>250)
		   { 
			   this.visible=true;
			   this.timeToRegenerate=0;
		   }
			  
		}
		  
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
