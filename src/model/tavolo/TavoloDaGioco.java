package model.tavolo;

import java.util.ArrayList;
import java.util.List;
import model.giocatore.Giocatore;
import model.manager.Manager;
import model.mazzo.Mazzo;

/**
 * This class represents the game table, which has the two decks, the players
 * and a turn manager.
 * 
 * The methods within it are almost self-explanatory
 * 
 * @author daniele
 *
 */
public class TavoloDaGioco {

	private Manager tManager;

	private static TavoloDaGioco instance;
	private List<Giocatore> listaGiocatori;
	private Mazzo mPesca;
	private Mazzo mScarti;

	public static TavoloDaGioco getInstance() {
		if (instance == null)
			instance = new TavoloDaGioco();
		return instance;
	}

	private TavoloDaGioco() {
		listaGiocatori = new ArrayList<>(
				List.of(new Giocatore("Player0"), new Giocatore("Player1"), new Giocatore("Player2")));
		mPesca = new Mazzo();
		mPesca.creaMazzo();
		mPesca.mescola();
		mScarti = new Mazzo();
		tManager = new Manager(this);

	}

	public Mazzo getMazzoPesca() {
		return mPesca;
	}

	public Manager getManager() {
		return tManager;
	}

	public Mazzo getMazzoScarti() {
		return mScarti;
	}

	public List<Giocatore> getListaGiocatori() {
		return listaGiocatori;
	}

	public void addGiocatore(int i, Giocatore g) {
		listaGiocatori.add(i, g);
	}
}
