package Wingman;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class Life extends GameObject {

	
	@Override
	public void update() {
		
		
	}


	
	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs, int nlife, int a, int b)
	{
		for(int i=0; i<nlife; i++)g.drawImage(this.obImage.get(0), a+(i*this.obImage.get(0).getWidth(null)), b, imageObs);
		
	}

}
