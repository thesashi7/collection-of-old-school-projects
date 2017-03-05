package Tankgame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class AimRocket extends TankWeapon
{
	public AimRocket()
	{
		this.damage=3;
		this.angle=0;
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Rocket_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
	}
	
	public AimRocket(int ang)
	{
		this.angle=ang;
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Rocket_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
		this.damage=3;
	}

	@Override
	public Weapon fired(int x, int y, int angle) 
	{
		AimRocket s = new AimRocket(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;
	}

	@Override
	public void setAngle(int ang) 
	{
		this.angle=ang;
	}

	@Override
	public void update(int x, int y) 
	{
		this.x+= Math.round(this.hSpeed*Math.cos(this.angle * Math.PI/180)*1.71);
		this.y-= Math.round(this.vSpeed*Math.sin(this.angle *Math.PI/180)*1.71);
	}

	@Override
	public Weapon fired(int x, int y) 
	{
		AimRocket s = new AimRocket(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;
	}
	
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		g.drawImage(this.obImage.get(0), x, y,  imageObs);
	}

}
