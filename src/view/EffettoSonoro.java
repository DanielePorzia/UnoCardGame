package view;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class let the "uno" sound, "error" sound or every other SoundEffect to
 * be played when the {@code TastoUno} is clicked
 * 
 * @author daniele
 *
 */
public class EffettoSonoro {

	private Clip clip;
	private AudioInputStream sound;
	private String path;

	/**
	 * This constructor creates a clip from a file names as the String in input
	 * @param s is the name of the file.wav
	 */
	public EffettoSonoro(String s) {
		path = "./src/resources/audio/" + s + ".wav";
		try {
			File file = new File(path);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * plays the sound
	 */
	public void play() {

		clip.start();
	}

}
