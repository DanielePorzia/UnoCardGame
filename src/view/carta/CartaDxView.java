package view.carta;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * This class represents the player's cards on the right. Each card is drawn by
 * taking a photo of the back of a card in perspective. The two offset static
 * fields are used to draw one card next to the other, to give the effect of a
 * hand of cards
 * 
 * @author daniele
 *
 */
public class CartaDxView extends JLabel {

	private static final long serialVersionUID = -577285341039916955L;
	private static int xOffset;
	private static int yOffset;

	public CartaDxView() {

		setBounds(xOffset, yOffset, 70, 130);
		xOffset += 12;
		yOffset += 7;

		try {
			setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/cards/Retro_DX.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method resets the offsets, it's useful when the number of card of the
	 * right player changes. When the number of cards changes, in fact, the offsets
	 * are reset and the updated number of cards in hand are drawn, in the right
	 * position.
	 */
	public static void reset() {
		xOffset = 0;
		yOffset = 0;
	}

}
