package Tankgame;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public abstract class PickUp extends GameObject
{
	public int timer;
	
	public PickUp()
	{
		this.visible=false;
		this.timer=0;
	}
	
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		g.drawImage(this.obImage.get(0), x, y, imageObs);
		this.timer++;
		
		if(this.timer>300) this.visible=false;
	}
	
	
	/*
	 * PlayerTank or the picker picks the type of PickUp
	 */
	public abstract void picked(Object picker);

}
