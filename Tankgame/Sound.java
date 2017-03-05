package Tankgame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound 
{
	private Object sound;
	
	public Sound(Object s)
	{
		this.sound=s;
		
	}
	
	public Sound(String p)
	{
		this.sound=this.getAudio(p);
		
	}
	
	public void setAudioClip(AudioClip s)
	{
		this.sound=s;
	}
	
	public void playAudioClip()
	{
		//try{Thread.sleep(500);}
		//catch(Exception e){}
		synchronized(this){
		((AudioClip)this.sound).play();}
	}
	
    public static AudioClip getAudio(String name)
    {
    	AudioClip audio=null;
    	try
    	{ 
    	   URL url = GameObject.class.getResource(name);
    	   audio = Applet.newAudioClip(url);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("AUDIO EXCEPTION");
    		System.exit(1);
    	}
	   return audio;
    }
	

}
