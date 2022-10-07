package view.tavolo;


import javax.swing.JLayeredPane;

import controller.Controller;
import model.carte.Carta;
import model.giocatore.Giocatore;
import view.carta.CartaGiocatoreView;

/**
 * This class represents the user's hand
 * 
 * @see Controller
 * @see Giocatore
 * @author daniele
 */
public class ManoDown extends JLayeredPane {

	private static final long serialVersionUID = 6760280707058388574L;
	private static int i;
	private Giocatore giocatore;
	private Controller ctrl;

	public ManoDown() {
		setBounds(300,550,600,150);
		setLayout(null);
	}
	/**
	 * This setter allows the controller to set the player who owns this hand
	 * 
	 * @param g is the player who owns this hand
	 */
	public void setGiocatore(Giocatore g) {
		giocatore = g;
	}

	/**
	 * This setter allows the controller to set itself in order to create a new
	 * CartaGiocatoreView (line 60)
	 * 
	 * @param c is the setted controller 
	 */
	public void setController(Controller c) {
		ctrl = c;
	}

	/**
	 * This method allows the controller to call the manoDown repaint with the right
	 * position of cards based on the amount of cards
	 */
	public void visualizzaCarte() {
		
		removeAll();

		int puntoIniziale = trovaCentro(getWidth(), giocatore.getMano().getNCarteManoGiocatore());
		int offset = calcolaOffset(getWidth(), giocatore.getMano().getNCarteManoGiocatore());

		for (Carta carta : giocatore.getMano().getListCarte()) {
			CartaGiocatoreView card = new CartaGiocatoreView(carta, ctrl);
			card.setBounds(puntoIniziale, 30, 70, 100);
			add(card, Integer.valueOf(i++));
			puntoIniziale += offset;
		}
	}

	/**
	 * Private method used to calculate the offset between two cards in relation
	 * with the amount of cards in player's hand
	 * 
	 * @param larghezza   represents the width of the ManoDown pane
	 * @param carteInMano represents the amount of cards in player's hand
	 * @return {@code int} which represent the best distance between two cards
	 */
	private int calcolaOffset(int larghezza, int carteInMano) {
		
		//71 perchè,essendo ogni carta larga 70, c'è 1 px di spazio fra l'una e l'altra
		int offset = 71;
		if (carteInMano > 7) {
			offset = (larghezza - 100) / (carteInMano - 1);
		}
		return offset;

	}

	/**
	 * Private method used to find the centre of the manoDown pane, if the cards'
	 * amount is less than 7, the x coordinate of the centre needs to be calculate
	 * subtracting from manoDown's width the momentary offset between the cards
	 * multiplied by how many cards the player has in hand. Once this is done,
	 * divide by two otherwise the cards would be placed too far to the right
	 * 
	 * @param larghezza   represents the width of the ManoDown pane
	 * @param carteInMano represents the amount of cards in player's hand
	 * @return
	 */
	private int trovaCentro(int larghezza, int carteInMano) {
		int xIniziale=0;
		if (carteInMano <= 7) {
			xIniziale = (larghezza - calcolaOffset(larghezza, carteInMano) * carteInMano) / 2;
		}
		return xIniziale;
	}

}
