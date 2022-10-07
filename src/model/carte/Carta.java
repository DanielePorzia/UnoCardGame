package model.carte;

/**
 * This abstract class represents the general card, which only has color, value
 * and amount of points
 * 
 * @author daniele
 *
 */
public abstract class Carta {

	private Colore colore;
	private Valore valore;
	private int punti;

	public Carta(Valore v, Colore c) {
		this.valore = v;
		this.colore = c;
		this.punti = v.getPunti();
	}

	/**
	 * method to get the card's color
	 * 
	 * @return the color of the card
	 */
	public Colore getColore() {
		return colore;
	}

	/**
	 * method to get the card's value
	 * 
	 * @return the value of the card
	 */
	public Valore getValore() {
		return valore;
	}

	/**
	 * method to get the amount of points
	 * 
	 * @return the car'd points value
	 */
	public int getPunti() {
		return punti;
	}

	/**
	 * method to ghet the string version of the card
	 * 
	 * @return something like VALORE_COLORE
	 */
	@Override
	public String toString() {
		return getValore() + "_" + getColore();
	}

}
