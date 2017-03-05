package Tankgame;

import java.awt.image.BufferedImage;

public abstract class TankWeapon extends Weapon
{
	public int angle,initX, initY;
	public int damage;
	protected BufferedImage stripImage;
	
	
	protected void createSpritesFromStrip(int width, int height)
	{
		if(this.angle<0) this.angle= 360 + this.angle;
		this.addSprite(this.stripImage.getSubimage( (this.angle/6)*width, 0, width, height));
		
		
	}

	
	/*
	 * 
	 * @param x
	 * @param y
	 * @param angle
	 * @return TankWeapon
	 */
	public abstract Weapon fired(int x, int y, int angle);
	
	/*
	 * Set fire angle of the TankWeapon
	 */
	public abstract void setAngle(int ang);

}
