package logic.player;

import java.net.URL;

import javazoom.jlgui.basicplayer.*;

public class MusicPlayer {
	
	public final static String MUSIC_RCS_PATH = "/media/sound/";
	public final static String SOUNDTRACK =  "soundtrack.mp3"; 
	
	public final static int MAX_VOL = 100;
	public final static int DEF_VOL = 50;
	
	private BasicPlayer basicPlayer = null;
	private int volume;
	private boolean isMute;
	
	public MusicPlayer(){
		basicPlayer = new BasicPlayer();
		volume = DEF_VOL;
	}
	
	public void playSoundTrack() {
		play(MusicPlayer.class.getResource(MUSIC_RCS_PATH +SOUNDTRACK));
		setVolume(DEF_VOL, MAX_VOL);
	}
	
	public void play (URL file){
		try {
			basicPlayer.open(file);
			basicPlayer.play();
			basicPlayer.setGain(volume/MAX_VOL);
		}
		catch (Exception e){ }
	}
	
	public void stop(){
		try {
			basicPlayer.stop();
		}
		catch (BasicPlayerException e){
		}
	}
	
	public void setMute(boolean mute) {
		isMute = mute;
		setVolume(volume, MAX_VOL);
	}
	
	public void setVolume(int vol, double volMax){
		try{
			if(!isMute) {
				volume = vol;
				basicPlayer.setGain(vol/volMax);
			} else {
				basicPlayer.setGain(0/volMax);
			}
		}
		catch (Exception e){ }
	}
	
	public float getVolume() {
		return this.volume;
	}

	public boolean isMute() {
		return isMute;
	}
}
