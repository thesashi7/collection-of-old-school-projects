package Wingman;


public class PowerUp extends GameObject
{
	private int power;

	
	/**
	 * Calls the planes poweredUp method that increases the health of the plane by power.
	 * @param p
	 */
	public void picked(PlayerPlane p)
	{
		p.poweredUp(1);
		this.setVisibility(false);
		this.setPosition(-10, -10);
	}
	
	
	//Overriding
	public void update()
	{
		this.y+=6;
		
	}

	public boolean collision(int x, int y, int w, int h)
	{
        if((y+h > this.getY()) &&(y < this.getY()+this.getHeight()  )) { // very simple test for showing an idea -- this only checks y forwarding direction
            if((x+w > this.getX()) &&(x < this.getX() + this.getWidth() )) {
                return true;
            }
        
        }
        return false;
	}


}
