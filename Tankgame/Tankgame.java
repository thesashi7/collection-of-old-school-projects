package Tankgame;


/**
 * 
 * 
 */
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.net.URL;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;



public class Tankgame extends JApplet implements Runnable
{
	public Thread thread;
	public GameEvents gameEvents;
    public Background bg;
    public BufferedImage bimg, bimg2, bimg3, bimg4, bimg5, lowerRightImage, lowerLeftImage;
    public PlayerTank playerOne, playerTwo;
    public int heightOfBackGround, widthOfBackGround, totalDrawnWidth,totalDrawnHeight,totalDrawnWidth2,totalDrawnHeight2, scrollX, scrollY, scrollX2, scrollY2, time;
    public ConcreteWall[] conWalls;
    public DestroyableWall[] desWalls;
    public HealthBar hb1, hb2;
    public Collision collision;
    public ObjectController objectController;
    public AudioClip music;
    public JButton play, exit;
  
    /*
     * Initializes all the needed fields for this game 
     * (non-Javadoc)
     * @see java.applet.Applet#init()
     */
	public void init()
	{
		bg = new Background();
		gameEvents = new GameEvents();
		hb1 = new HealthBar();
		hb2 = new HealthBar();
		music = Sound.getAudio(("ResourcesTank/Chapter11/Music.wav"));
	
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar1.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar2.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar3.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar4.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar5.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar6.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar7.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar8.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar9.png");
		hb1.addSprite("ResourcesTank/Chapter11/spr_healthBar10.png");
		
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar1.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar2.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar3.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar4.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar5.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar6.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar7.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar8.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar9.png");
		hb2.addSprite("ResourcesTank/Chapter11/spr_healthBar10.png");
		
		heightOfBackGround =  1248;
		widthOfBackGround = 1248;
		
		scrollX=20;
		scrollY=10;
		scrollX2= this.widthOfBackGround-500;
		scrollY2= this.heightOfBackGround-500;

		
		initPlayerTank();
		initConcreteWalls();
		initDestroyableWalls();
		
		collision = new Collision(conWalls, desWalls);
		objectController = new ObjectController(collision, (BufferedImage)getSprite("ResourcesTank/Chapter11/Pickup_strip4.png"));
		
		playerOne.setCollision(collision);
		playerTwo.setCollision(collision);
		
		gameEvents.addObserver(playerOne);
		gameEvents.addObserver(playerTwo);
		
		
		this.addKeyListener(new KeyControl());
		this.setSize(widthOfBackGround-300, heightOfBackGround-700); //  (-number)just to have the number of walls on x and y axis 40
		this.setFocusable(true);
	}
	
	/**
	 * Initializes player Tanks.
	 */
	public void initPlayerTank()
	{
		BufferedImage stripImage;
		
		playerOne = new PlayerTank(1);
		playerTwo = new PlayerTank(2);
		
		playerOne.setPosition(200, 190);
		stripImage =(BufferedImage) getSprite("ResourcesTank/Chapter11/Tank_blue_basic_strip60.png");
		this.createSpritesFromStripForTank(playerOne, stripImage);
		
		playerTwo.setPosition(this.widthOfBackGround-225, this.heightOfBackGround-220);
		stripImage =(BufferedImage) getSprite("ResourcesTank/Chapter11/Tank_red_basic_strip60.png");
		this.createSpritesFromStripForTank(playerTwo, stripImage);
		
		hb1.setHealthForHB(playerOne.health);
		hb2.setHealthForHB(playerTwo.health);
	}
	
	
	
	/**
	 * Initializes all the required concrete Walls
	 */
	public void initConcreteWalls()
	{
		conWalls = new ConcreteWall[360];
		
		for(int i=0; i<conWalls.length; i++)
		{
			conWalls[i]= new ConcreteWall();
		}
	}
	
	
	/**
	 * Initializes all the required destroyable Walls
	 */
	public void initDestroyableWalls()
	{
		desWalls = new DestroyableWall[60];
		
		for(int i=0; i<desWalls.length; i++)
		{
			desWalls[i]= new DestroyableWall();
		}
	}
	
	
	@Override
	public void run() 
	{  
	  Thread me = Thread.currentThread();
      music.loop();
      
	   while (thread == me) {
       	repaint();
           
         try {
               thread.sleep(10);
           } catch (InterruptedException e) {
               break;
           }
           
       }
		
	}
	
	public void start()
	{
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g)
	{
		Image scaledImage;
	    Graphics2D g2 = createGraphics2D(widthOfBackGround, heightOfBackGround);
	    Graphics2D g3 =createGraphics2DForBim2(100, 100);
	    Graphics2D g5 = createGraphics2DForBim4( (this.getWidth()/2)-100, this.getHeight()-101);
	    Graphics2D g6 = createGraphics2DForBim5((this.getWidth()/2)-100, this.getHeight()-101);
	    
	    if(!playerOne.isAlive() || !playerTwo.isAlive())
	    {
	    	g.setFont(new Font("Times Roman",50, 50));
	    	g.setColor(Color.white);
	    	music.stop();
	    	
	    	if(playerOne.life<=0)
	    	{
	    		g.drawString("Player Two Won!", this.getWidth()/2, this.getHeight()/2);
	    		
	    	}
	    	else
	    	{
	    		g.drawString("Player One Won!", this.getWidth()/2, this.getHeight()/2);
	    	}
	    	
	    }
	    else
	    {
		bg.drawBackGround(widthOfBackGround, heightOfBackGround, g2, this);

	    drawConcreteWalls(g2);
	    drawDestroyableWalls(g2);
	  
	    playerOne.draw(g2, this, widthOfBackGround, heightOfBackGround, playerTwo);
	    hb1.setPosition(playerOne.x-10, playerOne.y);
	    hb1.update(playerOne.health);
	    hb1.draw(g2, this);
	    
	    playerTwo.draw(g2, this, widthOfBackGround, heightOfBackGround, playerOne);
	    hb2.setPosition(playerTwo.x-10, playerTwo.y);
	    hb2.update(playerTwo.health);
	    hb2.draw(g2, this);
	    
	    objectController.draw(g2, this, this.widthOfBackGround, this.heightOfBackGround, playerOne, playerTwo);
		
		scaledImage= bimg.getScaledInstance(200,100, 1);
		Graphics2D g4 =createGraphics2DForBim3(200, 200);
		g4.drawImage(scaledImage, 0, 0, null);
		
		playerOneScrollMap();
		playerTwoScrollMap();
		bimg2=bimg;
		
		if(playerOne.reStartBattle || playerTwo.reStartBattle)
		{
			playerOne.health=10;
			playerTwo.health=10;
			playerOne.setPosition(200, 190);
			playerTwo.setPosition(this.widthOfBackGround-225, this.heightOfBackGround-220);
			playerOne.setAngleAndImage(0,0);
			playerTwo.setAngleAndImage(0,0);
			
			scrollX=20;
			scrollY=10;
			scrollX2= this.widthOfBackGround-500;
			scrollY2= this.heightOfBackGround-500;
			
			playerOne.reStartBattle=false;
			playerTwo.reStartBattle=false;
		}
		
		bimg=bimg.getSubimage(this.scrollX, this.scrollY, this.widthOfBackGround/3, this.heightOfBackGround/3);
		bimg2= bimg2.getSubimage(this.scrollX2, this.scrollY2,this.widthOfBackGround/3, this.heightOfBackGround/3);
	
		
		g.drawImage(bimg, 0, 0,(this.getWidth()/2)-10, this.getHeight()-100, this);
		g.drawImage(bimg2, (this.getWidth()/2)+10, 0, this.getWidth()/2, this.getHeight()-100, this);		
		
		g.drawImage(bimg3,(this.getWidth()/2)-100, this.getHeight()-100, this);
		
		g.setColor(Color.black);
		g.drawRect((this.getWidth()/2)-10, 0, 20, this.getHeight()-100);
		g.fillRect((this.getWidth()/2)-10, 0, 20, this.getHeight()-100);
		
		g5.setColor(Color.DARK_GRAY);
		g5.drawRect(0, 0, (this.getWidth()/2)-100, 200);
		g5.fillRect(0, 0, (this.getWidth()/2)-100, 200);
		
		g5.setColor(Color.red);
		g5.setFont(new Font("Times Roman", 28, 28));
		
		g5.drawString("Life:",10 , 22 );
		g5.drawString(Integer.toString( playerOne.life), 70, 22);
		g5.drawString("Score:",10 , 60 );
		g5.drawString(Integer.toString(playerOne.score), 100, 60);
		g5.drawString("Ammo:",10 , 120);
		for(int i=0; i <playerOne.heavyAmmo; i++)g5.drawImage(playerOne.weapon.getSprite(0),(i*playerOne.weapon.getWidth())+110,105, this);
		g5.drawString("Sheild:",10 , 165 );
		for(int i=0; i< playerOne.shieldT; i++) g5.drawImage(playerOne.sheild.getSprite(0),(i*20)+110 , 150, 20,20,this);
		
		g.drawImage(bimg4,0, this.getHeight()-100, (this.getWidth()/2)-100, 200, this);
		
		g6.setColor(Color.DARK_GRAY);
		g6.drawRect(0, 0, (this.getWidth()/2)-100, 200);
		g6.fillRect(0, 0, (this.getWidth()/2)-100, 200);
		
		g6.setColor(Color.white);
		g6.setFont(new Font("Times Roman", 28, 28));
		
		g6.drawString("Life:",10 , 22 );
		g6.drawString(Integer.toString( playerTwo.life), 70, 22);
		g6.drawString("Score:",10 , 60 );
		g6.drawString(Integer.toString(playerTwo.score), 100, 60);
		g6.drawString("Ammo:",10 , 120);
		for(int i=0; i <playerTwo.heavyAmmo; i++)g6.drawImage(playerTwo.weapon.getSprite(0),(i*playerTwo.weapon.getWidth())+110,105, this);
		g6.drawString("Sheild:",10 , 165 );
		for(int i=0; i< playerTwo.shieldT; i++) g6.drawImage(playerTwo.sheild.getSprite(0),(i*20)+110 , 150, 20,20,this);
		
		g.drawImage(bimg5, (this.getWidth()/2)+100, this.getHeight()-100, (this.getWidth()/2)-100, 200, this);
		
		g6.dispose();
		g5.dispose();
		g2.dispose();
	    }
		g.dispose();
		 
	}
	
	
	/*
	 * Draws Concrete Walls on the background
	 */
	public void drawConcreteWalls(Graphics2D g2)
	{
		int j=0, k=0,l=0,wallNum=0, wallWidth=0, wallHeight=0;
		wallWidth = conWalls[0].getWidth();
		wallHeight = conWalls[0].getHeight();
		
		for(int i=0; i<=(widthOfBackGround/ wallWidth) || i<=(heightOfBackGround/wallHeight); i++)
		{
			conWalls[wallNum].setPosition(i*wallWidth, 0); // draws top horizontal walls
			conWalls[wallNum++].draw(g2, this,playerOne, playerTwo);
			
			conWalls[wallNum].setPosition(i*wallWidth , heightOfBackGround-wallHeight); // draws bottom horizontal walls
			conWalls[wallNum++].draw(g2, this, playerOne, playerTwo );
			 
	
			conWalls[wallNum].setPosition(0, (i+1)*wallHeight); // draws left vertical walls
			conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
			
			
			conWalls[wallNum].setPosition(widthOfBackGround-wallWidth, (i+1)*wallHeight); // draws right vertical walls
			conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
			 
			
			// draws the four rectangular shape of Walls at the middle
			if(i*wallHeight>= 17*wallHeight && j<4)
			{
				conWalls[wallNum].setPosition(wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(2*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,2*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(3*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,3*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(4*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,4*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(5*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,5*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
		
				conWalls[wallNum].setPosition(12*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,12*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(13*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,13*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(14*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,14*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(15*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,15*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(16*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,16*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition(17*wallWidth , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,17*conWalls[i].getWidth() , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-2*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-2*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-3*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-3*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-4*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-4*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-5*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-5*conWalls[i].getWidth()), (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-6*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-6*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-13*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-13*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-14*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-14*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-15*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-15*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-16*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-16*conWalls[i].getWidth()), (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-17*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-17*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				conWalls[wallNum].setPosition((widthOfBackGround-18*wallWidth) , (i+1)*wallHeight);
				conWalls[wallNum++].draw(g2, this,playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,(widthOfBackGround-18*conWalls[i].getWidth()) , (i+1)*conWalls[i].getHeight(), this);
				
				j++;
			}
			
			if(i*wallHeight>= 5*wallHeight && k<4)// draws right inverted U shape of walls at the top
			{
				conWalls[wallNum].setPosition(5*wallWidth , (i)*wallHeight);
				conWalls[wallNum++].draw(g2, this,playerOne, playerTwo);
				//g2.drawImage(conWalls[wallNum++].getSprite(0) ,4*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);
				
				if(k==0 || k==3)
				{
					conWalls[wallNum].setPosition(6*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,5*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
					
					conWalls[wallNum].setPosition(7*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,6*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
					
					conWalls[wallNum].setPosition(8*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,7*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
				}
				k++;
			}
			else if(i*wallHeight>= 8*wallHeight && i*wallHeight<=13*wallHeight) // draws L shape of walls at the top
			{
				conWalls[wallNum].setPosition(27*wallWidth , (i)*wallHeight);
				conWalls[wallNum++].draw(g2, this,playerOne, playerTwo);
				   
				if(i*wallHeight== 13*wallHeight)
				{
					for(int cn= 12; cn<=27; cn++)
					{
					   conWalls[wallNum].setPosition(cn*wallWidth , (i)*wallHeight);
					   conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					}
				}
			}
			else if(i*wallHeight>= 31*wallHeight && l<4) // draws left inverted U shape of Walls at the bottom
			{
				conWalls[wallNum].setPosition(31*wallWidth , (i)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				
				if(l==0 || l==3)
				{
					conWalls[wallNum].setPosition(32*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,5*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
					
					conWalls[wallNum].setPosition(33*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,6*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
					
					conWalls[wallNum].setPosition(34*wallWidth , (i)*wallHeight);
					conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					//g2.drawImage(conWalls[wallNum++].getSprite(0) ,7*conWalls[i].getWidth() , (i)*conWalls[i].getHeight(), this);	
				}
				
				l++;
			}
			else if(i*wallHeight>= 26*wallHeight && i*wallHeight < 31*wallHeight) // draws L shape of walls at the bottom
			{
				conWalls[wallNum].setPosition(12*wallWidth , (i)*wallHeight);
				conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				
				if(i*wallHeight == 26*wallHeight)
				{
					for(int cn= 12; cn<=27; cn++)
					{
					   conWalls[wallNum].setPosition(cn*wallWidth , (i)*wallHeight);
					   conWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
					}
				}
				
			}
			
		}
	
	
	}
	
	/*
	 * Draws Destroyable Walls on the background
	 * @param g2
	 */
	public void drawDestroyableWalls(Graphics2D g2)

	{
		int wallNum=0;
		int wallWidth = desWalls[0].getWidth();
		int wallHeight = desWalls[0].getHeight();
		int bt=27;
		
		for(int i=0; i<25; i++)
		{
			
			if(i< 12)
			{
				desWalls[wallNum].setPosition(19*wallWidth , (i+1)*wallHeight);
				desWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
			
				desWalls[wallNum].setPosition(20*wallWidth , (i+1)*wallHeight);
				desWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
			}
			else if(i>=12 && i< 23)
			{
				//for(int bt=27; bt< 30; bt++){
				desWalls[wallNum].setPosition(19*wallWidth , (bt)*wallHeight);
				desWalls[wallNum++].draw(g2, this, playerOne, playerTwo );
				
				desWalls[wallNum].setPosition(20*wallWidth , (bt)*wallHeight);
				desWalls[wallNum++].draw(g2, this, playerOne, playerTwo);
				
				bt++;
				
			}
			else if(i==23)
			{
				for(int c=1; c<7; c++)
				{
					desWalls[wallNum].setPosition(c*wallWidth , (33)*wallHeight);
					desWalls[wallNum++].draw(g2, this, playerOne, playerTwo);	
				}
			}
			else if(i==24)
			{
				for(int c=2; c<8; c++)
				{
					desWalls[wallNum].setPosition(widthOfBackGround-c*wallWidth , (5)*wallHeight);
					desWalls[wallNum++].draw(g2, this, playerOne, playerTwo);	
				}
			}
		}
	}
	
	/*
	 * Scroll playerOnes map or background when needed at the proper time
	 */
	public void playerOneScrollMap()
	{
		if(totalDrawnWidth- playerOne.getX()<= 190 && (totalDrawnWidth < widthOfBackGround-playerOne.speed)) scrollX+=2;
		else if(playerOne.getX()-scrollX <= 170 && scrollX>=playerOne.speed) scrollX-=2;
		
		if(totalDrawnHeight- playerOne.getY()<=180 && (totalDrawnHeight < heightOfBackGround-playerOne.speed)) scrollY+=2;
		else if(playerOne.getY()- scrollY <=170 && scrollY>=playerOne.speed) scrollY-=2;
			
		totalDrawnWidth = scrollX+ (this.widthOfBackGround/3); //   scrollX + amount of Width of the background that is drawn
		totalDrawnHeight = scrollY +( this.widthOfBackGround/3); //   scrollX + amount of Height of the background that is drawn
	
	}
	
	/*
	 * Scroll playerTwo map or background when needed at the proper time
	 */
	public void playerTwoScrollMap()
	{
		if(totalDrawnWidth2- playerTwo.getX()<= 190 && (totalDrawnWidth2 < widthOfBackGround-playerTwo.speed)) scrollX2+=2;
		else if(playerTwo.getX()-scrollX2 <= 170 && scrollX2>=playerTwo.speed) scrollX2-=2;
		
		if(totalDrawnHeight2- playerTwo.getY()<=180 && (totalDrawnHeight2 < heightOfBackGround-playerTwo.speed)) scrollY2+=2;
		else if(playerTwo.getY()- scrollY2 <=170 && scrollY2>=playerTwo.speed) scrollY2-=2;
			
		totalDrawnWidth2 = scrollX2+ (this.widthOfBackGround/3); //   scrollX + amount of Width of the background that is drawn
		totalDrawnHeight2 = scrollY2 +( this.widthOfBackGround/3); //   scrollX + amount of Height of the background that is drawn
	
	}

	
	public Graphics2D createGraphics2D(int w, int h)
	{
	   Graphics2D g2 = null;
	   if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
	      bimg = (BufferedImage) createImage(w, h);
	    }
	   g2 = bimg.createGraphics();
	   g2.setBackground(getBackground());
	   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   g2.clearRect(0, 0, w, h);
	   return g2;
	}
	
	
	public Graphics2D createGraphics2DForBim5(int w, int h)
	{
	   Graphics2D g2 = null;

	   if (bimg5 == null || bimg5.getWidth() != w || bimg5.getHeight() != h) {
	      bimg5 =(BufferedImage) createImage(w, h);
	    }
	   g2 = bimg5.createGraphics();
	   g2.setBackground(getBackground());
	   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   g2.clearRect(0, 0, w, h);
	   return g2;
	}
	
	public Graphics2D createGraphics2DForBim4(int w, int h)
	{
	   Graphics2D g2 = null;

	   if (bimg4 == null || bimg4.getWidth() != w || bimg4.getHeight() != h) {
	      bimg4 =(BufferedImage) createImage(w, h);
	    }
	   g2 = bimg4.createGraphics();
	   g2.setBackground(getBackground());
	   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   g2.clearRect(0, 0, w, h);
	   return g2;
	}
	
	public Graphics2D createGraphics2DForBim2(int w, int h)
	{
	   Graphics2D g2 = null;

	   if (bimg2 == null || bimg2.getWidth() != w || bimg2.getHeight() != h) {
	      bimg2 =(BufferedImage) createImage(w, h);
	    }
	   g2 = bimg2.createGraphics();
	   g2.setBackground(getBackground());
	   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   g2.clearRect(0, 0, 200, 200);
	   return g2;
	}
	   
	public Graphics2D createGraphics2DForBim3(int w, int h)
	{
	   Graphics2D g2 = null;

	   if (bimg3 == null || bimg3.getWidth() != w || bimg3.getHeight() != h) {
	      bimg3 =(BufferedImage) createImage(w, h);
	    }
	   g2 = bimg3.createGraphics();
	   g2.setBackground(getBackground());
	   g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	  // g2.clearRect(0, 0, 200, 200);
	   return g2;
	}
	   
	
	public void createSpritesFromStripForTank(PlayerTank player, BufferedImage stripImage)
	{
		int width=0 ,height=0;
		width = stripImage.getWidth()/60;
		height= stripImage.getHeight();
		
		for(int i=0; i<60; i++)
		{
			player.addSprite(stripImage.getSubimage(i*width, 0, width, height));
		}
	
	}

    
    public Image getSprite(String name) 
	 {
		   Image img=null;
		   
		   try{
		   URL url = GameObject.class.getResource(name);
	       img = ImageIO.read(url);
		   }
	      /* try {
	           MediaTracker tracker = new MediaTracker(this);
	           tracker.addImage(img, 0);
	           tracker.waitForID(0);
	       }*/
		   catch (Exception e) {
			   System.exit(1);
	       }
		   
	       return img;
	   }
	/**
	 * KeyControl class is a subclass of KeyAdapter.
	 * @author thapaliya
	 *
	 */
	class KeyControl extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
	    {
	       gameEvents.keyPressed(e.getKeyCode());
	    		 
	    }
		public void keyReleased(KeyEvent e)
		{
			gameEvents.keyReleased(e.getKeyCode());
	    }
	   
	}

}

class GameEvents extends Observable
{
    int keyCode;
    int release;
    
    public void keyPressed(int keycode) 
    {
      release=-1;
      keyCode =keycode;
      setChanged();
      
      // trigger notification
      notifyObservers(this);  
    }
	
    public void keyReleased(int msg)
    {
    	release = msg;
    	keyCode=-1;
        setChanged();
        
        // trigger notification
        notifyObservers(this); 
    }

    public static void main(String argv[]) {
        final Tankgame demo = new Tankgame();
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {});
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }
 

}


