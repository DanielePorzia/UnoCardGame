package view.carta;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import controller.Controller;
import model.carte.Carta;

/**
 * This class draws the input card to player hand, is clickable and when a card
 * is pressed it is removed from the hand and added to the discard pile
 * 
 * @author daniele
 *
 */
public class CartaGiocatoreView extends JButton {

	private static final long serialVersionUID = 64894483452L;

	private Image image;
	private Carta carta;

	/**
	 * This method is called from the {@code Controller} class
	 * 
	 * @return the instance of the card which is decorated with its own view
	 */
	public Carta getCarta() {
		return carta;
	}

	public CartaGiocatoreView(Carta c, Controller ctrl) {
		this.carta = c;
		
		try {

			image = ImageIO.read(new File("./src/resources/img/cards/" + carta.toString() + ".png"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Image imageScaled = image.getScaledInstance(70, 100, 16);
		setIcon(new ImageIcon(imageScaled));

		setBorderPainted(false);
		setFocusPainted(false);
		
		/**
		 * Inner class to add a Listener for each card added to the player's hand. It
		 * calls the controller method "cartaCliccata" which throws the selected card on
		 * the discard pile. Moreover, when the mouse passes over a card, it is raised
		 * by 20 pixels, lowered if the mouse exits from that card
		 */
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ctrl.cartaCliccata(CartaGiocatoreView.this);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Point p = ((CartaGiocatoreView) e.getSource()).getLocation();
				p.y -= 20;
				CartaGiocatoreView.this.setLocation(p);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Point p = ((CartaGiocatoreView) e.getSource()).getLocation();
				p.y += 20;
				CartaGiocatoreView.this.setLocation(p);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});

		

	}

}
