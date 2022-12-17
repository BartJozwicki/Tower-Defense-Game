package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	FloatControl gainControl;
	URL[] soundURL = new URL[5];

	Sound() {
		soundURL[0] = getClass().getResource("/M&B.wav");
		//soundURL[0] = LoadSave.class.getClassLoader().getResourceAsStream("/resources/M&B.wav");
	}
	private void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-50.0f);
	
		} catch (Exception e) {
	
			e.printStackTrace();
		}	
		
	}
	
	private void play() {
		
		clip.start();
	}
	
	private void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	 void stop() {
		
		clip.stop();
		
	}
	 
	 public void soundOn() {
		    setFile(0);
			play();
			loop();
	 }

}
