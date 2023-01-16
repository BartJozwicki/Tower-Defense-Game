package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	FloatControl gainControl;
	URL[] soundURL = new URL[1];

	Sound() {
		soundURL[0] = getClass().getResource("/M&B.wav");

	}

	// Setup selected file
	private void setFile(int i) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Control the volume
	public void setVolume(float gain) {
		try {
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-50.0f + (gain * 50.0f));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Start music
	private void play() {

		clip.start();
	}

	// Loop endlessly
	private void loop() {

		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	// Music Off
	void stop() {

		clip.stop();

	}

	// Procedure to turn the music on
	public void soundOn() {
		setFile(0);
		setVolume(1.0f);
		play();
		loop();
	}

}
