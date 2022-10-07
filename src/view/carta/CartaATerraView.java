package view.carta;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.carte.Carta;

/**
 * This class represents the discarded cards. It has two builders: 
 * 
 * - The first requires a Card as an argument, after which (thanks 
 * to the toString () of the card) it reads the photo called VALORE_COLORE.png 
 * 
 * - The second takes a string
 * as input. In the controller, depending on which color is chosen when a wild
 * card is thrown, this constructor is called with the string representing the
 * name of the file to be drawn (red, yellow wild card, etc.)
 * 
 * 
 * @author daniele
 *
 */
public class CartaATerraView extends JLabel {

	private static final long serialVersionUID = -5820007458057420559L;
	private Image image;
	private Carta carta;

	/**
	 * This is the first constructor to call with a card as argument
	 * @param c the card we want to draw
	 */
	public CartaATerraView(Carta c) {
		this.carta = c;

		try {

			image = ImageIO.read(new File("./src/resources/img/cards/" + carta.toString() + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Image imageScaled = image.getScaledInstance(65, 91, 16);
		setIcon(new ImageIcon(imageScaled));
		setBounds(5, 0, 70, 90);

	}

	/**
	 * This is the second constructor to call with a strign as argument
	 * @param coloreWildCard the string to create the card whose name is the string in input
	 */
	public CartaATerraView(String coloreWildCard) {
		try {

			image = ImageIO.read(new File("./src/resources/img/" + coloreWildCard + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Image imageScaled = image.getScaledInstance(65, 91, 16);
		setIcon(new ImageIcon(imageScaled));
		setBounds(5, 0, 70, 90);
	}

}
