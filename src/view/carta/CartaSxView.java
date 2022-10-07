package view.carta;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * This class represents the player's cards on the left. Each card is drawn by
 * taking a photo of the back of a card in perspective. The two offset static
 * fields are used to draw one card next to the other, to give the effect of a
 * hand of cards
 * 
 * @author daniele
 *
 */
public class CartaSxView extends JLabel{


	private static final long serialVersionUID = -6218120503737098825L;
	private static int xOffsetSx=150;
	private static int yOffsetSx;

	
	public CartaSxView() {
		setBounds(xOffsetSx, yOffsetSx, 70, 90);
		
		xOffsetSx -= 10;
		yOffsetSx += 10;
		try {
			setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/cards/Retro_SX.png"))));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	/**
	 * This method resets the offsets, it's useful when the number of card of the
	 * left player changes. When the number of cards changes, in fact, the offsets
	 * are reset and the updated number of cards in hand are drawn, in the right
	 * position.
	 */
	public static void reset() {
		yOffsetSx=0;
		xOffsetSx=150;
	}
	
	
}