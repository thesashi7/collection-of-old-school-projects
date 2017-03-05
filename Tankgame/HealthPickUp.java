package Tankgame;

public class HealthPickUp extends PickUp 
{
	
	/*
	 * Increases health of the picker Player Tank
	 * (non-Javadoc)
	 * @see Tankgame.PickUp#picked(java.lang.Object)
	 */
	public void picked(Object picker)
	{
		((PlayerTank)picker).health+=5;
		if(((PlayerTank)picker).health >10) ((PlayerTank)picker).health=10;
		
	}
	
	public void update()
	{
		
	}

}
