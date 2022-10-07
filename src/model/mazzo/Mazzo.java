package model.mazzo;

import java.util.Random;
import java.util.Stack;

import model.carte.CambiaColore;
import model.carte.Carta;
import model.carte.CartaNumero;
import model.carte.Colore;
import model.carte.Inverti;
import model.carte.PescaDue;
import model.carte.QuattroPiuCambiaColore;
import model.carte.Stop;
import model.carte.Valore;

/**
 * This class represents the deck (both Pesca and Scarti). When built, it is
 * deliberately not filled with 108 cards because the discard pile is a deck but
 * it must not have any cards inside it at the start of the game.
 * 
 * 
 * And this is the meaning of the method creaMazzo. The other methods are the
 * same method of Stack class.
 * 
 * @author daniele
 *
 */
public class Mazzo {

	private Stack<Carta> mazzo = new Stack<>();

	/**
	 * Fills the deck with 108 cards according to the rules of the game
	 */
	public void creaMazzo() {

		for (int i = 0; i < 2; i++) {
			for (Colore c : Colore.values()) {
				if (c.equals(Colore.NERO)) {
					for (int k = 0; k < 2; k++) { // Due for da 0 a 2 annidati per aggiungere 4 carte nere.
													// Il for esterno serve anche per le altre carte
						mazzo.add(new CambiaColore());
						mazzo.add(new QuattroPiuCambiaColore());
					}
				} else { // Se il colore non è nero aggiungi le carteEffettoColorate
					mazzo.add(new PescaDue(c));
					mazzo.add(new Stop(c));
					mazzo.add(new Inverti(c));
					for (Valore v : Valore.values()) { // Per ogni valore che non sia un valore speciale

						if (!v.equals(Valore.CAMBIACOLORE) && !v.equals(Valore.CAMBIACOLOREPIU4)
								&& !v.equals(Valore.INVERTI) && !v.equals(Valore.PESCA2) && !v.equals(Valore.STOP)) {
							if (v.equals(Valore.ZERO) && i == 1) // Se il valore é ZERO e lo abbiamo gia
																	// aggiunto,continua
								continue;
							else // Altrimenti aggiugni la cartaNumero colorata al mazzo
								mazzo.add(new CartaNumero(v, c));
						}
					}
				}
			}
		}
	}

	public boolean isEmpty() {
		return mazzo.empty();
	}

	public Carta controllaPrimaCarta() {
		return mazzo.peek();
	}

	public Carta prendiPrimaCarta() {
		return mazzo.pop();
	}

	public void appoggiaCarta(Carta c) {
		mazzo.push(c);
	}

	/**
	 * This method mixes the deck according to a criterion seen in the laboratory
	 * with Professor Cinelli
	 */
	public void mescola() {
		Random r = new Random();
		Carta cartaAppoggio;

		for (int k = 0; k < 236; k++) {
			int indice1 = r.nextInt(108), indice2 = r.nextInt(108);
			if (indice1 != indice2) {
				cartaAppoggio = mazzo.get(indice1);
				mazzo.set(indice1, mazzo.get(indice2));
				mazzo.set(indice2, cartaAppoggio);
			}
		}
	}

	public Stack<Carta> getMazzo() {
		return mazzo;
	}

	public int getMazzoSize() {
		return mazzo.size();
	}

}
