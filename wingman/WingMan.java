package Wingman;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;





/**
 * This class WingMan is a subclass of JApplet and implements interface Runnable
 * And, so this is the main Applet program that runs the game (WingMan).
 * @author thapaliya
 *
 */
public class WingMan extends JApplet implements Runnable
{
	GameObject playerOne, playerTwo, boss;
	GameEvents gameEvents;
	BackGround bg;
	Thread thread;
	BufferedImage bimg;
	ObjectController controller;
	HealthBar hb1,hb2;
	long startTime;
	Explosion explosion1, explosion2;
	Image youLose, youWin, gameOver;
	AudioClip gameLoose, gameWin;
	int loose,time,win;
	
	public void init()
	{
	   initExplosion();// initializes Explosion object
	   playerOne= new PlayerPlane(1);
	   playerTwo = new PlayerPlane(2);
	   boss= new Boss();
	   gameEvents = new GameEvents();
	   bg = new BackGround();
	   controller = new ObjectController(System.currentTimeMillis(), (PlayerPlane)playerOne, (PlayerPlane)playerTwo) ;
	   hb1 = new HealthBar();
	   hb2 = new HealthBar();
	   

	   //Setting up sprites of player one
	   playerOne.addSprite(getSprite("Resources/myplane_1.png"));
	   playerOne.addSprite(getSprite("Resources/myplane_2.png"));
	   playerOne.addSprite(getSprite("Resources/myplane_3.png"));
	   playerOne.setPosition(900,500);
	   playerOne.setExplosion(explosion1);
	   ((PlayerPlane)playerOne).incomingBoss((Plane)boss);
	  
	   //setting up sprites of player two
	   playerTwo.addSprite(getSprite("Resources/myplane_1.png"));
	   playerTwo.addSprite(getSprite("Resources/myplane_2.png"));
	   playerTwo.addSprite(getSprite("Resources/myplane_3.png"));
	   playerTwo.setPosition(100, 500);
	   playerTwo.setExplosion(explosion2);	
	   ((PlayerPlane)playerTwo).incomingBoss((Plane)boss);
	   
	   
	   //setting up sprites of boss
	   boss.addSprite("Resources/boss1.1.png");
	   boss.addSprite("Resources/boss1.2.png");
	   boss.addSprite("Resources/boss1.3.png");
	   boss.setPosition(600, 20);
	   ((Boss)boss).setShootability(true);
	   ((Boss)boss).setVisibility(false);
	   
	   gameEvents.addObserver((Observer)playerOne);
	   gameEvents.addObserver((Observer)playerTwo);
	   
	   bg.setImage(getSprite("Resources/water.png"));
	   bg.setMusic(getAudio("Resources/background.mid"));
	
	   hb1.addSprite(getSprite("Resources/health.png"));
	   hb1.addSprite(getSprite("Resources/health1.png"));
	   hb1.addSprite(getSprite("Resources/health2.png"));
	   hb1.addSprite(getSprite("Resources/health3.png"));
	  
	   hb1.setHealthForHB(((PlayerPlane)playerOne).getHitPoints());
	   hb1.setPosition(1160, 580);
	   
	   hb2.addSprite(getSprite("Resources/health.png"));
	   hb2.addSprite(getSprite("Resources/health1.png"));
	   hb2.addSprite(getSprite("Resources/health2.png"));
	   hb2.addSprite(getSprite("Resources/health3.png"));
	  
	   hb2.setHealthForHB(((PlayerPlane)playerOne).getHitPoints());
	   hb2.setPosition(90, 580);
	   
	   youLose= this.getSprite("Resources/youLose.png");
	   youWin = this.getSprite("Resources/youWin.png");
	   gameOver = this.getSprite("Resources/gameOver.png");
	   
	   gameLoose = this.getAudio("Resources/GameLoose.wav");
	   
	   this.setSize(900, 500);
	   this.addKeyListener(new KeyControl());
	   this.setFocusable(true);
	   
	   
	}

	public void initExplosion()
	{
		explosion1 = new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		explosion2 = new Explosion(this.getAudio("Resources/snd_explosion2.wav"));
		
		explosion1.addSprite(this.getSprite("Resources/explosion1_1.png"));
		explosion1.addSprite(this.getSprite("Resources/explosion1_2.png"));
		explosion1.addSprite(this.getSprite("Resources/explosion1_3.png"));
		explosion1.addSprite(this.getSprite("Resources/explosion1_4.png"));
		explosion1.addSprite(this.getSprite("Resources/explosion1_5.png"));
		explosion1.addSprite(this.getSprite("Resources/explosion1_6.png"));
		
		explosion2.addSprite(this.getSprite("Resources/explosion1_1.png"));
		explosion2.addSprite(this.getSprite("Resources/explosion1_2.png"));
		explosion2.addSprite(this.getSprite("Resources/explosion1_3.png"));
		explosion2.addSprite(this.getSprite("Resources/explosion1_4.png"));
		explosion2.addSprite(this.getSprite("Resources/explosion1_5.png"));
		explosion2.addSprite(this.getSprite("Resources/explosion1_6.png"));
		    //this.explosion.setSound(sound);
		

	}
	@Override
	public void run() {
		
        Thread me = Thread.currentThread();
         bg.playMusic();
        
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
   
    
   public void paint(Graphics g)
   {
	   System.out.println("Main time= "+ time);
       Dimension d = getSize();
       Graphics2D g2 = createGraphics2D(d.width, d.height);
      
       
	   bg.drawBackGroundWithTileImage(d.width, d.height, g2, this);
	   if(playerOne.isAlive() && playerTwo.isAlive() && boss.isAlive())
	   {
	      controller.draw(g2, this);
	   
	      hb1.update(((PlayerPlane)playerOne).getHitPoints());
	      hb1.draw(g2, this);
	   
	      hb2.update(((PlayerPlane)playerTwo).getHitPoints());
	      hb2.draw(g2, this);
	   
	     
	      playerOne.draw(g2, this);
	      playerTwo.draw(g2, this);
	      if(time>=3000)
	      {
	    	 // try{thread.sleep(2000);}catch(Exception ex){}
	    	  boss.setVisibility(true);
	    	 ((Boss) boss).draw(g2, this, playerOne, playerTwo);
	    	 if(((PlayerPlane)playerOne).collision(boss.x, boss.y, boss.getWidth(), boss.getHeight())) ((Boss)boss).collided();
	    	 if(((PlayerPlane)playerTwo).collision(boss.x, boss.y, boss.getWidth(), boss.getHeight())) ((Boss)boss).collided();
	    	  
	    	 System.out.println("BOSS DRAW_-----");
	      }
	   }
	   else if(!boss.isAlive())
	   {
		   //bg.stopMusic();
		   g2.setColor(Color.red);
			  
		   g2.drawString("SCORES", 60, 30);
		   
		   g2.setColor(Color.orange);
		   g2.drawString("PLAYER ONE :"+ Integer.toString(((PlayerPlane)playerOne).getScore()), 50, 50);
		   g2.drawString("PLAYER TWO :"+ Integer.toString(((PlayerPlane)playerTwo).getScore()), 50, 70);
		   
		   g2.drawImage(this.youWin, 750, win-100, this);
		   
		   if(win==0);// play win music
		   
		   
		   if(win>=this.getHeight()) win=1;
		   win++;
	   }
	   else 
	   {
		   
		   g2.drawImage(youLose, 100, 100,this);
		   g2.drawImage(gameOver, 750, 1+loose,this);
		   
		   g2.setColor(Color.red);
		  
		   g2.drawString("SCORES", 60, 30);
		   
		   g2.setColor(Color.orange);
		   g2.drawString("PLAYER ONE :"+ Integer.toString(((PlayerPlane)playerOne).getScore()), 50, 50);
		   g2.drawString("PLAYER TWO :"+ Integer.toString(((PlayerPlane)playerTwo).getScore()), 50, 70);
		   
		   
		   g2.setColor(Color.white);
		   g2.drawString("MUSIC ARTIST: WAR", 600, 10+loose);
		   
		   if(loose>=this.getWidth()-100) loose=1;
		   
		   //if(playerOne.getX()< this.getWidth() || playerOne.getY()< this.getHeight())playerOne.setPosition(800+loose, 300+loose);
		   //else{loose =1; playerOne.setPosition(loose, loose);}
		 //  ((Plane)playerOne).reset();
		  // playerOne.draw(g2,this);
		   
		   
		   if(!playerOne.isAlive()) System.out.println("Player one is dead");
		   if(!playerTwo.isAlive()) System.out.println("Player two is dead");
		   bg.stopMusic();
		   
		   if(loose==0)gameLoose.loop();
		   loose++;
		   System.out.println("Player dead");
		   //System.exit(1);
	   }
	   
	   g2.dispose();
	   g.drawImage(bimg, 0, 0, this);
	   
	   time++;
	   
   }
	   
	 
   
   
   
   public Image getSprite(String name) 
   {
	   URL url = WingMan.class.getResource(name);
       Image img = getToolkit().getImage(url);
       try {
           MediaTracker tracker = new MediaTracker(this);
           tracker.addImage(img, 0);
           tracker.waitForID(0);
       } catch (Exception e) {
       }
       return img;
   }
   
  
   public AudioClip getAudio(String name)
   {
	   AudioClip audio=null;
	   try
	   {
		   URL url = WingMan.class.getResource(name);
	       audio = getAudioClip(url);
	   }
	   catch(Exception ex){}
	   
	   return audio;
   }

   public Graphics2D createGraphics2D(int w, int h)
   {
       Graphics2D g2 = null;
       if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
           bimg = (BufferedImage) createImage(w, h);
       }
       g2 = bimg.createGraphics();
       g2.setBackground(getBackground());
       g2.setRenderingHint(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);
       g2.clearRect(0, 0, w, h);
       return g2;
   }
   
   /**
    * 
    * @author thapaliya
    *
    */
   class KeyControl extends KeyAdapter
   {

	   
       public void keyPressed(KeyEvent e)
       {
       	
    	   System.out.println("keyPressed()");
    	 //  System.out.println(e.getKeyCode()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	   gameEvents.keyPressed(e.getKeyCode());
    		 
       }
	   
	
       
       public void keyReleased(KeyEvent e)
       {
    	
    	   System.out.println("KeyReleased()");
    	    gameEvents.keyReleased(e.getKeyCode());
    	   
        }
   
   }
   
   
   public static void main(String argv[]) {
       final WingMan demo = new WingMan();
       demo.init();
       JFrame f = new JFrame("WingMan");
       f.addWindowListener(new WindowAdapter() {
       });
       f.getContentPane().add("Center", demo);
       f.pack();
       f.setSize(new Dimension(640, 480));
       f.setVisible(true);
       f.setResizable(false);
       demo.start();
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
    
 

}


