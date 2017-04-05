package Wingman;

import java.util.Random;

/**
 * This class Island is a subclass of GameObject which also has the speed at which the island will move in the game.
 * @author thapaliya
 *
 */
public class Island extends GameObject
{
	private int speed;
	private Random gen;
	
	
	public Island(Random generator, int spd)
	{
		gen=generator;
		speed=spd;
	}
	/**
	 * Sets the speed of this Island to s.
	 * @param s
	 */
	public void setSpeed(int s)
	{
		this.speed=s;
	}

	/**
	 * 
	 * @return the speed of this Island.
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the position of this Island to the current position added to the position values passed in the argument(w,h).
	 * @param w
	 * @param h
	 */
	public void update(int w, int h)
	{
        y += speed;
        if (y >= h) {
            y = -100;
            x = Math.abs(gen.nextInt() % (w - 30));
        }
        
	}
	
	
	//Overriding
	public void update()
	{
		this.y+=speed;
		
	}

}
