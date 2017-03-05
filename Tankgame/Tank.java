package Tankgame;

public abstract class Tank extends GameObject
{
	protected Weapon weapon;
	protected int life,speed;
	
	public Tank()
	{
		this.life=2;
	}
	
	public void setWeapon(Weapon w)
	{
		this.weapon=w;
	}
	
	public Weapon getWeapon()
	{
		return this.weapon;
	}
	
	
	public abstract void shoot();
}
