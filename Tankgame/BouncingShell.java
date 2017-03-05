package Tankgame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BouncingShell extends TankWeapon
{
	public int imageNum, timer;
	public boolean reverse;
	
	public BouncingShell()
	{
		this.damage=2;
		this.imageNum=0;
		this.angle=0;
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Bouncing_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
	}
	
	public BouncingShell(int ang)
	{
		this.angle=ang;
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Bouncing_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
		this.damage=2;
		this.timer=0;
	}

	public Weapon fired(int x, int y, int angle)
	{
		BouncingShell s = new BouncingShell(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;	
	}
	
	
	public Weapon fired(int x, int y)
	{
		BouncingShell s = new BouncingShell(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;	
	}
	

	@Override
	public void update(int x, int y) 
	{
		if(reverse)
		{
			this.x-= Math.round(this.hSpeed*Math.cos(this.angle * Math.PI/180)*2.71);
			this.y+= Math.round(this.vSpeed*Math.sin(this.angle *Math.PI/180)*2.71);
			this.angle= this.angle-150;
			this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
			imageNum++;
			reverse = false;
			return;
		}
		this.x+= Math.round(this.hSpeed*Math.cos(this.angle * Math.PI/180)*1.71);
		this.y-= Math.round(this.vSpeed*Math.sin(this.angle *Math.PI/180)*1.71);
	}
	
	public void setAngle(int ang)
	{
		this.angle=ang;
	}
	
	public void draw(Graphics2D g, ImageObserver iamgeObs)
	{
		g.drawImage(this.obImage.get(this.imageNum), this.x, this.y, iamgeObs);
		timer++;
		
		if(timer>=200) this.visible=false;
	}
	
}
