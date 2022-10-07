package model.carte;

import model.manager.Manager;

/**
 * this class describes a card with an effect
 * 
 * @author daniele
 *
 */
public class PescaDue extends CartaEffetto {

	public PescaDue(Colore c) {
		super(Valore.PESCA2, c);
	}

	/**
	 * This effect takes the hand of the next player (without changing the actual
	 * index) and adds two cards in it
	 */
	@Override
	public void applicaEffetto(Manager tManager) {

		tManager.getListaGiocatori().get((tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente()))).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());

		tManager.getListaGiocatori().get((tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente()))).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());

	}

}
