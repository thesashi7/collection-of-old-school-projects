package Tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;



public class Shell extends TankWeapon
{

	
	public Shell()
	{
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Shell_light_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
		this.damage=1;
	}
	
	public Shell(int ang)
	{
		this.angle=ang;
		this.stripImage = (BufferedImage)this.getSprite("ResourcesTank/Chapter11/Shell_light_strip60.png");
		this.createSpritesFromStrip(this.stripImage.getWidth()/60, this.stripImage.getHeight());
		this.damage=1;
	}
	
	public Shell(String name)
	{
		this.addSprite(name);
		this.damage=1;
	}
	
	
	public void setAngle(int ang)
	{
		this.angle=ang;
	}
	
	
	@Override
	public void update(int x, int y) {
		this.x+= Math.round(this.hSpeed*Math.cos(this.angle * Math.PI/180)*1.71);
		this.y-= Math.round(this.vSpeed*Math.sin(this.angle *Math.PI/180)*1.71);
	}

	@Override
	public Weapon fired(int x, int y) {
		
		Shell s = new Shell(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;
	}
	
	
	public Weapon fired(int x, int y, int angle) {
		Shell s = new Shell(angle);
		
		s.initX=x;
		s.initY=y;
		
		s.setPosition(x, y);
		return  s;
	}
	

	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		g.drawImage(this.obImage.get(0), x, y, imageObs);
	}
}
