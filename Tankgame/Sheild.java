package Tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Sheild extends GameObject
{
	
	public Sheild(Image img)
	{
		this.addSprite(img);
	}
	
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics2D g, ImageObserver imageObs, int x, int y)
	{
		
		g.drawImage(this.obImage.get(0), x, y, imageObs);
	}

}
