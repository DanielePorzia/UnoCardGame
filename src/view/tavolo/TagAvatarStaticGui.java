package view.tavolo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * This class represents the static part of the gui that is the part that, even
 * if clicked, does not undergo changes and does not involve any action.
 * 
 * It randomly chooses 3 opponents, writes their name on the appropriate tag and
 * shows each one's avatar
 * 
 * It also reads the name and the avatar of the user and displays them.
 * 
 * @author daniele
 */
public class TagAvatarStaticGui extends JLabel {

	private static final long serialVersionUID = 2892924944103058720L;
	private ArrayList<String> nomi = new ArrayList<>(
			List.of("Mario", "Luigi", "Peach", "Bowser", "Yoshi", "Wario", "Waluigi"));
	private JLabel pic1;
	private JLabel pic2;
	private JLabel pic3;
	private JLabel avatar;
	private String name1String;
	private String name2String;
	private String name3String;

	/**
	 * For each player we will create: · A square avatar with a black background and
	 * a white border · A tag with the name inside it Then everything is appended at
	 * the {@code this} JLabel
	 */
	public TagAvatarStaticGui() {

		try {
			// Left Opponent
			name1String = randomName();
			pic1 = new JLabel(
					new ImageIcon(ImageIO.read(new File("./src/resources/img/avatar/" + name1String + ".png"))));
			pic1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
			pic1.setBackground(Color.BLACK);
			pic1.setOpaque(true);
			pic1.setBounds(40, 395, 90, 90);

			JLabel name1 = new JLabel(name1String, SwingConstants.CENTER);
			name1.setFont(new Font("Helvetica", 1, 30));

			JLabel tag1 = new JLabel(new ImageIcon(ImageIO.read(new File("./src/resources/img/Tag.png"))));
			tag1.setBounds(5, 480, 250, 60);
			tag1.setLayout(new GridLayout());

			tag1.add(name1);
			add(pic1);
			add(tag1);

			// Up Opponent
			name2String = randomName();
			pic2 = new JLabel(
					new ImageIcon(ImageIO.read(new File("./src/resources/img/avatar/" + name2String + ".png"))));
			pic2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
			pic2.setBackground(Color.BLACK);
			pic2.setOpaque(true);
			pic2.setBounds(280, 13, 90, 90);

			JLabel name2 = new JLabel(name2String, SwingConstants.CENTER);

			name2.setFont(new Font("Helvetica", 1, 30));

			JLabel tag2 = new JLabel(new ImageIcon(ImageIO.read(new File("./src/resources/img/Tag.png"))));
			tag2.setBounds(245, 100, 250, 60);
			tag2.setLayout(new GridLayout());

			tag2.add(name2);
			add(pic2);
			add(tag2);

			// Right Opponent
			name3String = randomName();
			pic3 = new JLabel(
					new ImageIcon(ImageIO.read(new File("./src/resources/img/avatar/" + name3String + ".png"))));
			pic3.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
			pic3.setBackground(Color.BLACK);
			pic3.setOpaque(true);
			pic3.setBounds(1160, 395, 90, 90);

			JLabel name3 = new JLabel(name3String, SwingConstants.CENTER);
			name3.setFont(new Font("Helvetica", 1, 30));

			JLabel tag3 = new JLabel(new ImageIcon(ImageIO.read(new File("./src/resources/img/Tag.png"))));
			tag3.setBounds(1035, 480, 250, 60);
			tag3.setLayout(new GridLayout());

			tag3.add(name3);
			add(pic3);
			add(tag3);

			// Real Player
			List<String> righeStats = Files.readAllLines(Paths.get("./src/resources/Stats.txt"));
			JLabel name = new JLabel(righeStats.get(0), SwingConstants.CENTER);
			name.setBounds(30, 123, 150, 30);
			name.setFont(new Font("Helvetica", 1, 30));

			JLabel tag = new JLabel(new ImageIcon(ImageIO.read(new File("./src/resources/img/Tag.png"))));
			tag.setBounds(785, 678, 250, 60);
			tag.setLayout(new GridLayout());

			Image image = ImageIO.read(new File(righeStats.get(1)));
			Image imageScaled = image.getScaledInstance(100, 100, 16);
			avatar = new JLabel(new ImageIcon(imageScaled));
			avatar.setBounds(901, 583, 100, 100);
			avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
			avatar.setBackground(Color.BLACK);

			tag.add(name);
			add(avatar);
			add(tag);

		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds(0, 0, 1280, 800);
		setLayout(null);
	}

	/**
	 * The following 4 methods, depending on which one is called, draw the red
	 * border around the avatar of the current player
	 * 
	 */
	public void setPic1VisualTurn() {
		pic1.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

	}

	public void setPic2VisualTurn() {
		pic2.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

	}

	public void setPic3VisualTurn() {
		pic3.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

	}

	public void setUserVisualTurn() {
		avatar.setBorder(BorderFactory.createLineBorder(Color.RED, 10));

	}

	/**
	 * This method resets all player's border to a white one.
	 */
	public void resetColorPics() {
		pic1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

		pic2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

		pic3.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

		avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

	}

	/**
	 * This method is called in Controller class to allow the audio with the voice
	 * of the right character to be reproduced.
	 * 
	 * @param posizione the index of the current player
	 * @return the name of the current player
	 */
	public String getNameOpponent(int posizione) {
		switch (posizione) {
		case 1:
			return name1String;
		case 2:
			return name2String;
		case 3:
			return name3String;
		default:
			return null;
		}
	}

	/**
	 * Pick a name from the name's list and remove it from the list
	 * 
	 * @return a {@code String} which represents the picked opponent
	 */
	public String randomName() {
		Random rand = new Random();
		return nomi.remove(rand.nextInt(nomi.size() - 1));

	}
}
