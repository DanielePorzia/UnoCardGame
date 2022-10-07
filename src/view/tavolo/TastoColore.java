package view.tavolo;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 
 * This class represents the interface shown when a wild card is cast. It has 4
 * buttons, one for each color, when pressed (controlled in the controller)
 * changes color
 * 
 * @author daniele
 *
 */
public class TastoColore extends JLabel {

	private static final long serialVersionUID = -1125826291493518968L;
	private JButton rosso = new JButton();
	private JButton giallo = new JButton();
	private JButton verde = new JButton();
	private JButton blu = new JButton();

	public TastoColore() {

		try {
			rosso.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Rosso.png"))));
			giallo.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Giallo.png"))));
			verde.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Verde.png"))));
			blu.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Blu.png"))));

		} catch (IOException e) {
			e.printStackTrace();
		}

		rosso.setBorderPainted(false);
		rosso.setFocusPainted(false);
		rosso.setBounds(0, 0, 220, 220);

		add(rosso);

		verde.setBorderPainted(false);
		verde.setFocusPainted(false);
		verde.setBounds(221, 0, 220, 220);

		add(verde);

		blu.setBorderPainted(false);
		blu.setFocusPainted(false);
		blu.setBounds(442, 0, 220, 220);

		add(blu);

		giallo.setBorderPainted(false);
		giallo.setFocusPainted(false);
		giallo.setBounds(663, 0, 220, 220);

		add(giallo);

		setBounds(200, 290, 885, 220);
	}

	/**
	 * used by the controller
	 * 
	 * @return the instance of the button
	 */
	public JButton getRosso() {
		return rosso;
	}

	/**
	 * used by the controller
	 * 
	 * @return the instance of the button
	 */
	public JButton getGiallo() {
		return giallo;
	}

	/**
	 * used by the controller
	 * 
	 * @return the instance of the button
	 */
	public JButton getVerde() {
		return verde;
	}

	/**
	 * used by the controller
	 * 
	 * @return the instance of the button
	 */
	public JButton getBlu() {
		return blu;
	}
}
