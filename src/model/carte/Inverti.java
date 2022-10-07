package model.carte;

import model.manager.Manager;

/**
 * this class describes a card with an effect
 * 
 * @author daniele
 *
 */
public class Inverti extends CartaEffetto{

	public Inverti(Colore c) {
		super(Valore.INVERTI, c);
		
	}

	/**
	 * Its effect is to change the direction on the game
	 */
	@Override
	public void applicaEffetto(Manager tManager) {
		tManager.setDirezioneGiro();
	}

}
