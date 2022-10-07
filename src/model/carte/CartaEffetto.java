package model.carte;

import model.manager.Manager;

/**
 * This abstract class, is built as a normal card but has a method which is to
 * override by lower classes
 * 
 * @author daniele
 *
 */
public abstract class CartaEffetto extends Carta {

	public CartaEffetto(Valore v, Colore c) {
		super(v, c);
	}

	abstract public void applicaEffetto(Manager tManager);

}
