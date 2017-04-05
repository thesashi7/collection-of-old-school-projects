package Wingman;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JApplet;

public class ObjectController 
{
	private Random generator;
	private PowerUp power;
	private PlayerPlane p1,p2;
	private Plane[] downEnemyPlane, upEnemyPlane, rightEnemyPlane, leftEnemyPlane, downShooterEnemy,  releasedEnemy;
	private long startTime;
	private int screenWidth, screenHeight,numberE,  numberWave,time,index,timer;
	private Island isl1, isl2, isl3;
	private Life life1, life2;
	
    public ObjectController(long currentTime, PlayerPlane pO, PlayerPlane pT)
    {
    	generator= new Random(1234);
    	power = new PowerUp();
    	
    	isl1= new Island(generator,2);
    	isl2= new Island(generator,2);
    	isl3= new Island(generator,2);
    	
    	life1= new Life();
    	life2 = new Life();
    	
    	power.addSprite("Resources/powerup.png");
    	power.setPosition(60, 1);
    	
    	isl1.addSprite("Resources/island1.png");
    	isl1.setPosition(90, 100);
    	isl2.addSprite("Resources/island2.png");
    	isl2.setPosition(500, 500);
    	isl3.addSprite("Resources/island3.png");
    	isl3.setPosition(900,700);
    	
    	life1.addSprite("Resources/life.png");
    	life2.addSprite("Resources/life.png");
    	
    	startTime= currentTime;
    	
    	
    	p1=(PlayerPlane)pO;
    	p2=(PlayerPlane)pT;
    	
    	//explosSound = explosS;
    	
    	numberE=0;

    	numberWave=0;
    	time=0;
    	
    	index=0;
    	initEnemies();
    }
    
    public void initEnemies()
    {
    	downEnemyPlane = new DownEnemeyPlane[20];
    	upEnemyPlane = new UpEnemeyPlane[20];
    	leftEnemyPlane = new LeftEnemeyPlane[20];
    	rightEnemyPlane = new RightEnemyPlane[20];
    	downShooterEnemy = new DownEnemeyPlane[20];
    	releasedEnemy = new Plane[50];
 
    	


 	    
    	for(int i=0; i<20; i++)
    	{
    		   downEnemyPlane[i] = new DownEnemeyPlane();
    		  // plane.setPosition(i*dist, -2);
    		   downEnemyPlane[i].addSprite("Resources/enemy1_1.png");
    		   downEnemyPlane[i].addSprite("Resources/enemy1_12.png");
    		   downEnemyPlane[i].addSprite("Resources/enemy1_13.png");
    		   downEnemyPlane[i].setSpeed(3);
    		   
    		   	
    		   
    		   upEnemyPlane[i] = new UpEnemeyPlane();
    		   //plane.setPosition((90*i)+dist, screenHeight-2);
    	       upEnemyPlane[i].addSprite("Resources/enemy4_1.png");
    		   upEnemyPlane[i].addSprite("Resources/enemy4_2.png");
    		   upEnemyPlane[i].addSprite("Resources/enemy4_3.png");
    		   upEnemyPlane[i].setSpeed(3);
    		   
    		   
    		   
    		   rightEnemyPlane[i] = new RightEnemyPlane();
    		   //plane.setPosition(-2, (90*i)+dist);
    		   rightEnemyPlane[i].addSprite("Resources/enemy3_11.png");
    		   rightEnemyPlane[i].addSprite("Resources/enemy3_12.png");
    		   rightEnemyPlane[i].addSprite("Resources/enemy3_13.png");
    		   rightEnemyPlane[i].setSpeed(3);
    		   
    		   
    		   
    		   leftEnemyPlane[i] = new LeftEnemeyPlane();
    		  //plane.setPosition(screenWidth-50, (90*i)+dist+(dist/2));
    		   leftEnemyPlane[i].addSprite("Resources/enemy3_1.png");
    		   leftEnemyPlane[i].addSprite("Resources/enemy3_2.png");
    		   leftEnemyPlane[i].addSprite("Resources/enemy3_3.png");
    		   leftEnemyPlane[i].setSpeed(3);
    		   
    		  
    		   
    		   downShooterEnemy[i] = new DownEnemeyPlane();
    		//	plane.setPosition((90*i)+dist, screenHeight-2);
    		   downShooterEnemy[i].addSprite("Resources/enemy2_1.png");
    		   downShooterEnemy[i].addSprite("Resources/enemy2_2.png");
    		   downShooterEnemy[i].addSprite("Resources/enemy2_3.png");
    		   downShooterEnemy[i].setSpeed(3);
    		   downShooterEnemy[i].setShootability(true);
    		   ((DownEnemeyPlane)downShooterEnemy[i]).setBulletSpeed(0, 5);
    		  
    	}
    	//initialize boss here
    }
    
	/**
	 * Draws the image of this object using the passed Graphics and ImageObserver
	 * @param g
	 * @param imageObs
	 */
	public void draw(Graphics2D g, ImageObserver imageObs)
	{
		int i=0;
		Plane enemyPlane;
		
		screenWidth=  ((JApplet)imageObs).getWidth();
		screenHeight = ((JApplet)imageObs).getSize().height;
		
		
		generateIslands(g, imageObs);
		generateTimedPlanes(g, imageObs);
		
		life1.draw(g, imageObs,p1.getLife(),1160-80, 580);
		life1.draw(g, imageObs,p2.getLife(),90+120,580);
		
		// for power up
		if(timer>=500 && power.isAlive())
		{
			power.draw(g, imageObs);
			
			if(power.collision(p1.x, p1.y, p1.getWidth(), p1.getHeight())) power.picked(p1);
			else if(power.collision(p2.x, p2.y, p2.getWidth(), p2.getHeight())) power.picked(p2);
			
			power.update();
		}
		
		while( i<releasedEnemy.length && releasedEnemy[i]!=null)
		{
			System.out.println(i);
			
				//check collision with player plane here before drawing
			if(releasedEnemy[i].collide<3)
			{
				System.out.println(i+" collide ="+ releasedEnemy[i].collide);
				//if(releasedEnemy[i].collide!=0) System.exit(1);
			   
			   p1.incomingEnemies(releasedEnemy);
			   p2.incomingEnemies(releasedEnemy);
			   this.releasedEnemy[i].draw(g, imageObs,p1,p2);
			   
			   System.out.println(i+" collide ="+ releasedEnemy[i].collide);
			   
			   if(releasedEnemy[i].isAlive() && p1.collision(releasedEnemy[i].x, releasedEnemy[i].y, releasedEnemy[i].getWidth(), releasedEnemy[i].getHeight())) releasedEnemy[i].collided(); //explosion2.explode(g, imageObs, releasedEnemy[i].x, releasedEnemy[i].y);}
			   if(releasedEnemy[i].isAlive() && p2.collision(releasedEnemy[i].x, releasedEnemy[i].y, releasedEnemy[i].getWidth(), releasedEnemy[i].getHeight())) releasedEnemy[i].collided(); //explosion2.explode(g, imageObs, releasedEnemy[i].x, releasedEnemy[i].y);}
			   
		
			   
			   if(releasedEnemy[i].isAlive())releasedEnemy[i].update();
			}
			

			   //i++;
			//}
			i++;
			
		}
		System.out.println("Timer="+timer);
	   timer++;
	   
	}
	
	
	/**
	 * 
	 * @param enPlane
	 * @param imageObs
	 * @return true if the enemey plane is still on displayable screen or else false
	 */
	public boolean onScreen(Plane enPlane, ImageObserver imageObs)
	{
		if(enPlane.getX()<-2 || enPlane.getX()> screenWidth-enPlane.getSprite().getWidth(imageObs))
		{
			return false;
		}
		else if(enPlane.getY()<-2 || enPlane.getY()> screenHeight-enPlane.getSprite().getHeight(imageObs))
		{
			return false;
		}
		
		
		return true;
	}
	
	
	public void generateIslands(Graphics2D g, ImageObserver imageObs)
	{
		//if(System.currentTimeMillis()-initialTime > 9000)
		//{
		   isl1.update( screenWidth, screenHeight);
		   isl1.draw(g, imageObs);
		
		   isl2.update(screenWidth-20, screenHeight);
		   isl2.draw(g, imageObs);
		
		   isl3.update(screenWidth-40, screenHeight);
		   isl3.draw(g, imageObs);
		//}
	}
	
	/**
	 * Generates enemy planes of various types in a timely manner.
	 */
	public void generateTimedPlanes(Graphics2D g, ImageObserver imageObs)
	{
		System.out.println("Time");
	
		if(timeForBoss())
		{
			straightPattern(100,15);
			
			
		}
		else if(timeForWaveFour())
		{
			patternFour(100, 20);
			
		}
		else if (timeForWaveThree())
		{
			patternThree(100,6);
		}
		else if(timeForWaveTwo())
		{
			diagnolPatternTwo(100,12);
		}
		else if(timeForWaveOne())
		{
		   	diagnolPatternOne(180,9);
		   	
		    //diagnolPatternOne(200,5,g, imageObs);
		}
		else if(timer>200 && time==0 )
		{
			straightPattern(200,7);
			//if(timer>=290)straightPattern(200,0,7,g, imageObs);
			
		}

	}
	
	
	public void straightPattern(int dist, int numP)
	{
		Plane enemyPlane=null;
		
		if( (numberE<= numP-1 && numberWave==0) )
		{
			System.out.println("Entered");
			downEnemyPlane[numberE].setPosition(numberE*dist, -2);
			releasedEnemy[numberE]= downEnemyPlane[numberE];
			releasedEnemy[numberE].reset();
			//(numberWave, (screenWidth-40-(numberE*dist)) );
			numberE++;
			
			startTime= System.currentTimeMillis();
			
			//try{Thread.sleep(1000);}catch(Exception ex){}
			System.out.println("Index="+index);
		}
	    else if(numberE> numP-1){ startTime= System.currentTimeMillis();  numberE=0; numberWave=1; time=0; }

	}
	
	public void diagnolPatternOne( int dist, int numP)
	{ 
		System.out.println("HEREEEEIS");
		System.out.println("index="+index);
			if(System.currentTimeMillis()-startTime >=500 &&(numberE<= numP-1 && numberWave==1))
			{
			//if(timer- initialTime>50 && (numberE<= numP-1 && numberWave==1) )
			//{
				System.out.println("Entered");
				downEnemyPlane[numberE].setPosition(numberE*dist, -2);
				releasedEnemy[numberE]= downEnemyPlane[numberE];
				releasedEnemy[numberE].reset();
				//(numberWave, (screenWidth-40-(numberE*dist)) );
				numberE++;
				
				startTime= System.currentTimeMillis();
				//try{Thread.sleep(1000);}catch(Exception ex){}
				System.out.println("Index="+index);
			}
		    else if(numberE> numP-1){ startTime= System.currentTimeMillis();  numberE=0; numberWave=2; time++; }
		
	
		
	}
		
	
	public void diagnolPatternTwo( int dist, int numP)
	{
		
		System.out.println("patternTwo():");

		//System.exit(1);
		if(System.currentTimeMillis()-startTime >=500 &&(numberE<= numP-1 && numberWave==2))
		{
			System.out.println("Entered");
			
			downEnemyPlane[numberE].setPosition(screenWidth-40-(numberE*dist), -2);
			releasedEnemy[numberE]= downEnemyPlane[numberE];
		
			releasedEnemy[numberE].reset();
			//(numberWave, (screenWidth-40-(numberE*dist)) );
	
			
			numberE++;
			startTime= System.currentTimeMillis();
			
			System.out.println("Index="+index);
		}
	    else if(numberE> numP-1){ startTime= System.currentTimeMillis();  numberE=0; numberWave++; time++;}

			
	}
	
	
	public void patternThree(int dist, int numP)
	{
		System.out.println("patternThree():");
		if(System.currentTimeMillis()-startTime >=500 &&(numberE<= numP-1 && numberWave==3))
		{
			releasedEnemy[numberE] = rightEnemyPlane[numberE];
			releasedEnemy[numberE].setPosition(-2, 90*numberE+(dist-40));
			releasedEnemy[numberE].reset();
			
			releasedEnemy[numberE+1] = rightEnemyPlane[numberE+1];
			releasedEnemy[numberE+1].setPosition(-2, 90*numberE+(dist-40)+90);
			releasedEnemy[numberE+1].reset();
			
			releasedEnemy[numberE+2] = leftEnemyPlane[numberE];
			releasedEnemy[numberE+2].setPosition(screenWidth-50, (90*numberE)+(dist-40)+((dist-40)/2));
			releasedEnemy[numberE+2].reset();
			
			releasedEnemy[numberE+3] = leftEnemyPlane[numberE+1];
			releasedEnemy[numberE+3].setPosition(screenWidth-50, (90*numberE)+(dist-40)+((dist-40)/2)+90);
			releasedEnemy[numberE+3].reset();
			
			//rightEnemy(-2, 90*numberE+(dist-40));
		   // leftEnemy(numberE, dist-40);
		    startTime= System.currentTimeMillis();
		    numberE+=4;
		   
		    
		}
		else if(numberE> numP-1)
		{ 
			startTime= System.currentTimeMillis(); 
			index=0;
			numberE=0; 
			numberWave++;
			time++;
			System.out.println("INDEX="+index+ "  NUMBERE="+ numberE);
		}
		
	}
	
	public void patternFour(int dist, int numP)
	{
		System.out.println("patternFour():");
		int i=0;
		/*for(i=0; i<numP; i++)
		{
			releasedEnemy[i] = shooterEnemy[i];
			releasedEnemy[i].setPosition(i*dist, -2);
		}*/
		
		if(System.currentTimeMillis()-startTime >=500 &&(numberE<= numP-1 && numberWave==4))
		{
			releasedEnemy[numberE] = downShooterEnemy[numberE];
			releasedEnemy[numberE].setPosition(numberE*dist, -2);
			releasedEnemy[numberE].reset();
			
			releasedEnemy[numberE+1] = leftEnemyPlane[numberE];
			releasedEnemy[numberE+1].setPosition(screenWidth-50, (90*numberE)+(dist-40)+((dist-40)/2));
			releasedEnemy[numberE+1].reset();
			releasedEnemy[numberE+1].setShootability(true);
			((LeftEnemeyPlane)releasedEnemy[numberE+1]).setBulletSpeed(3, (numberE)+2);
			
			releasedEnemy[numberE+2] = upEnemyPlane[numberE];
			releasedEnemy[numberE+2].setPosition(90*numberE +dist, screenHeight);
			releasedEnemy[numberE+2].reset();
			
			//rightEnemy(-2, 90*numberE+(dist-40));
		    //leftEnemy(numberE, dist-40);
		    startTime= System.currentTimeMillis();
		    //numberE+=2;
		    numberE+=3;
		    
		    //index++;
		}
		else if(numberE> numP-1)
		{ 
			startTime= System.currentTimeMillis(); 
			numberE=0; 
			numberWave=0;
			this.time=0;
		}
	}
	
	
	/**
	 * 
	 * @return true if it is the time for the arrival of the enemy boss or else false.
	 */
	public boolean timeForBoss()
	{
		if(timer>3500) return true;
		return false;
	}
	
	
	public boolean timeForWaveOne()
	{
		if(timer >= 500) return true;
		return false;
	}

	public boolean timeForWaveTwo()
	{
		if(timer >= 1000){ return true;}
		return false;
	}
	
	public boolean timeForWaveThree()
	{
		if(timer>=1500){ return true;}
		return false;
	}
	
	public boolean timeForWaveFour()
	{
		if(timer >= 2500) return true;
		return false;
	}
	
	public void resetDownEnemyPlanePos(int nP)
	{
		for(int i=0; i<nP; i++)
		{
			downEnemyPlane[i].setY(1);
		}
	}
	/*public void enemyPlanescollision()
	{
		p1.checkCollision(enemeyPlanes);
		p2.checkCollision(enemeyPlanes);
	}*/
}
