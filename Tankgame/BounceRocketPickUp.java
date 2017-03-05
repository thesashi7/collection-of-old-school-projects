package Tankgame;

public class BounceRocketPickUp extends PickUp
{

	@Override
	public void picked(Object picker) 
	{
		((PlayerTank)picker).weapon = new BouncingShell();
		((PlayerTank)picker).heavyAmmo=10;
		((PlayerTank)picker).ammoPicked=true;
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
