package model.giocatore;

import java.util.ArrayList;
import java.util.List;

import model.carte.Carta;

/**
 * This class represents the hand of each player of a game, its methods are
 * nearly self-explicative
 * 
 * @author daniele
 *
 */
public class Mano {

	private List<Carta> manoGiocatore;

	
	public Mano() {
		manoGiocatore = new ArrayList<>();
	}

	public List<Carta> getListCarte() {
		return manoGiocatore;
	}

	public void aggiungiCarta(Carta c) {
		manoGiocatore.add(c);
	}
	
	public void rimuoviCarta(Carta c) {
		manoGiocatore.remove(c);
	}
	
	public Carta selezionaCarta(Carta c) {
		return manoGiocatore.get(manoGiocatore.indexOf(c));
	}

	public int getNCarteManoGiocatore() {
		return manoGiocatore.size();
	}
}
