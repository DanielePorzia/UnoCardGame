package view.carta;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * This class represents the player's cards on the top. Each card is drawn by
 * taking a photo of the back of a card in perspective. The two offset static
 * fields are used to draw one card next to the other, to give the effect of a
 * hand of cards
 * 
 * @author daniele
 *
 */
public class CartaUpView extends JLabel{


	private static final long serialVersionUID = 3498046748487272439L;
	private static int xOffset;
	

	
	public CartaUpView() {
		setBounds(xOffset, 0, 70, 90);
		xOffset +=25;
		
		
		try {
			setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/cards/Retro_UP.png"))));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method resets the offset, it's useful when the number of card of the
	 * top player changes. When the number of cards changes, in fact, the offset
	 * are reset and the updated number of cards in hand are drawn, in the right
	 * position.
	 */
	public static void reset() {
		xOffset=0;
	}
	
	
}