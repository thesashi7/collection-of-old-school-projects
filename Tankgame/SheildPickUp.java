package Tankgame;

public class SheildPickUp extends PickUp
{

	@Override
	public void picked(Object picker) 
	{
	   ((PlayerTank)picker).shieldT=2;
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
