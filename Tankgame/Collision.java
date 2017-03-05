package Tankgame;

public class Collision 
{
	GameObject[] conWall, desWall;
	
	public Collision()
	{
		
	}
	
	public Collision(GameObject[]w, GameObject[]w2)
	{
		this.conWall=w;
		this.desWall=w2; 
	}
	
	
	/*public void chekcCollision(PlayerTank p, GameObject[]wall)
	{
		for(int i=0; i< wall.length; i++)
		{
			if (collision(p.x, p.y, p.getWidth(), p.getHeight(), wall[i].x, wall[i].y, wall[i].getWidth(), wall[i].getHeight()))
				p.collidedWithObject(wall[i].x, wall[i].y, wall[i].getWidth(), wall[i].getHeight());
		}
		
		
	}
	*/
	public boolean checkCollisionWithWalls( int px, int py, int pw, int ph)
	{
		int pX = px, pY = py, pW= pw-10, pH= ph-10;
		
		//check collision with concrete walls
		for(int i=0; i< conWall.length; i++)
		{
			
			if((pY+pH> conWall[i].y) && (pY< conWall[i].y+(conWall[i].getHeight()-10)))
			{
				if((pX+pW> conWall[i].x) && (pX< conWall[i].x+conWall[i].getWidth()-10))
				{
					return true;
				}
			}
		}
		
		//check collision with destroyable walls
		
		for(int i=0; i< desWall.length; i++)
		{
			if(desWall[i].visible && (pY+pH> desWall[i].y) && (pY< desWall[i].y+(desWall[i].getHeight()-10)))
			{
				if((pX+pW> desWall[i].x) && (pX< desWall[i].x+desWall[i].getWidth()-10))
				{
					return true;
				}
			}
			
		}
		
		
		
		return false;
	}
	/*
	 * 
	 * @param x1
	 * @param y1
	 * @param w1
	 * @param h1
	 * @param x2
	 * @param y2
	 * @param w2
	 * @param h2
	 */
	public boolean collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
	{
		
		 if((y1+h1 > y2) &&(y1 < y2+h2 ) ) { 
	            if((x1+w1 > x2) &&(x1 < x2 + w2 )) {
	            
	            	return true;

	            }
	        
	        }
		 return false;
	   
	}

}
