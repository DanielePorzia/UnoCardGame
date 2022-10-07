package model.carte;

import model.manager.Manager;

/**
 * this class describes a card with an effect
 * 
 * @author daniele
 *
 */
public class QuattroPiuCambiaColore extends CartaEffetto {

	public QuattroPiuCambiaColore() {
		super(Valore.CAMBIACOLOREPIU4, Colore.NERO);
	}

	/**
	 * This effect takes the hand of the next player (without changing the actual
	 * index) and adds fours cards in it
	 */
	@Override
	public void applicaEffetto(Manager tManager) {

		tManager.getListaGiocatori().get(tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente())).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());
		tManager.getListaGiocatori().get(tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente())).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());
		tManager.getListaGiocatori().get(tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente())).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());
		tManager.getListaGiocatori().get(tManager.prossimoGiocatoreIpotetico(tManager.getIndiceCorrente())).getMano()
				.aggiungiCarta(tManager.getTavolo().getMazzoPesca().prendiPrimaCarta());
		
	}

}
