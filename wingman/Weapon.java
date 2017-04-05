package Wingman;


/**
 * This abstract class Weapon is the SuperClass of all the weapons in the game : PlayerBullet, BasicEnemyBullet, DirectionalEnemeyBullet.
 * @author thapaliya
 *
 */
public abstract class Weapon extends GameObject 
{
	protected int hSpeed,vSpeed;

	
	/**
	 * Sets the speed of this weapon to s.
	 * @param s
	 */
	public void setVSpeed(int s)
	{
		this.vSpeed=s;
	}
	
	/**
	 * Sets the speed of this weapon to s.
	 * @param s
	 */
	public void setSpeed(int xS, int yS)
	{
	    this.hSpeed= xS;
		this.vSpeed=yS;
	}
	

	
	/**
	 * 
	 * @return the speed of this weapon.
	 */
	public int getVSpeed()
	{
		return vSpeed;
	}
	
	
	/**
	 * 
	 * @return the speed of this weapon.
	 */
	public int getHSpeed()
	{
		return hSpeed;
	}
	

	//Overriding
	public void update()
	{
		this.y-=vSpeed;
		this.x-=hSpeed;
	}

	/**
	 * Resets the state of this weapon.
	 * @param x
	 * @param y
	 */
	public abstract void update(int x, int y);
	
	/**
	 * 
	 * @return the fired weapon
	 */
	public abstract Weapon fired(int x, int y);

}
