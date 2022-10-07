package model.carte;

import model.manager.Manager;

/**
 * this class describes a card with an effect
 * 
 * @author daniele
 *
 */
public class Stop extends CartaEffetto {

	public Stop(Colore c) {
		super(Valore.STOP, c);
	}

	/**
	 * this effect increments the index by 1 before that "next turn" method is
	 * called in the controller
	 */
	@Override
	public void applicaEffetto(Manager tManager) {
		tManager.prossimoGiocatore();
	}

}
