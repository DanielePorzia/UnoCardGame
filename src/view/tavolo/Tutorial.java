package view.tavolo;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 
 * This class represents a sort of tutorial where some functions and keys of the
 * game are shown and explained
 * 
 * @author daniele
 *
 */
public class Tutorial extends JLabel {

	private static final long serialVersionUID = 2513898894913057157L;
	private JButton done = new JButton();

	public Tutorial() {
		setBounds(0, 0, 1280, 800);
		setBackground(new Color(0, 0, 0, 200));
		setOpaque(true);

		JLabel scritte = new JLabel();
		scritte.setBounds(0, 0, 1280, 800);
		scritte.setIcon(new ImageIcon("./src/resources/img/Tutorial.png"));
		scritte.setVisible(true);
		add(scritte);

		done.setIcon(new ImageIcon("./src/resources/img/Ok.png"));
		done.setBounds(400, 650, 450, 120);
		done.setBorderPainted(false);
		done.setFocusPainted(false);
		add(done);
	}


	/**
	 * Method that returns an instance of the done button
	 * @return instance of the done button
	 */
	public JButton getDone() {
		return done;
	}

}
