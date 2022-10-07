package view;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class let the soundtrack to be played, both at the beginning of the game
 * and any time the audioOff button is pressed
 * 
 * @author daniele
 */
public class Music {

	private Clip clip;
	private AudioInputStream sound;
	private String PATH = "./src/resources/audio/PokemonOST.wav";

	public Music() {
		try {
			File file = new File(PATH);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (Exception e) {
		}
	}

	/**
	 * Plays the track and loop it continuosly. The soundtrack has been edited to be
	 * circular, with no gap between the end and the beginning
	 */
	public void play() {
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stops the soundtrack
	 */
	public void stop() {
		clip.stop();
	}
}