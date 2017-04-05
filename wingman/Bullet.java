package Wingman;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class Bullet extends Weapon
{
	
	
	
	public Bullet()
	{
		//this.vSpeed=6;
		//this.hSpeed=6;
		//this.setSprite("Resources/bullet.png");
	}
	
	public Bullet(int x, int y)
	{
		this.setPosition(x, y);
		//this.vSpeed=6;
		//this.hSpeed=6;
		//this.setSprite("Resources/bullet.png");
	}
	
	public Bullet(String image)
	{
		//this.vSpeed=6;
		//this.hSpeed=6;
		this.addSprite(image);
	}
	
	

	@Override
	public void update(int x, int y) {
		// TODO Auto-generated method stub
		this.x+=(this.hSpeed+x);
		this.y+=(this.vSpeed+y);
		
	}
	
	public Weapon fired(int x, int y)
	{
		Bullet b = new Bullet();
		b.setSprite(this.getSprite());
		b.setPosition(x, y);
		return  b;
	}


	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	/*public void draw(Graphics2D g, ImageObserver imageObs)
	{
		
		
		//if(this.==0){g.drawImage(this.objectImage, x, y, imageObs); time=1;}
		//else if(time ==1 && objectImage1!=null){g.drawImage(this.objectImage1, x, y, imageObs); time=2;}
		//else if(time ==2 && objectImage2!=null){g.drawImage(this.objectImage2, x, y, imageObs); time=0;}
		
	}*/

}
