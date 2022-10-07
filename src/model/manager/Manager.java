package model.manager;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import model.carte.CambiaColore;
import model.carte.Carta;
import model.carte.CartaEffetto;
import model.carte.Colore;

import model.carte.QuattroPiuCambiaColore;
import model.carte.Valore;
import model.giocatore.Giocatore;
import model.tavolo.TavoloDaGioco;

/**
 * This class is the masterpiece of the model package. It manages everything in
 * the model. From the opponent's turn to checking cards and decks
 * 
 * @author daniele
 *
 */
public class Manager {

	private TavoloDaGioco tavolo;
	private boolean isSensoOrario = true;
	private boolean isGiocoFinito;
	private Random rand = new Random();

	private Colore coloreRegnante;
	private Valore valoreATerra;

	private int indiceGiocatore;

	private int azioneEffettuata = 1; // 0= pescato 1 = lancio carta effetto 2 = lancio carta normale

	public Manager(TavoloDaGioco tg) {
		this.tavolo = tg;

		// Gira 1 Carta
		giraPrimaCarta();
		while (tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto
				|| tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore) {
			tavolo.getMazzoPesca().appoggiaCarta(tavolo.getMazzoScarti().prendiPrimaCarta());
			tavolo.getMazzoPesca().mescola();
			giraPrimaCarta();
		}

		// imposta valore 1a carta
		this.setColoreDaTerra();
		this.setValoreDaTerra();

	}

	/**
	 * This method simulates an opponent's turn. Additional comments within it
	 * better explain how it works
	 */
	public void turnoAvversario() {
		if (isGiocoFinito)
			return;

		if (tavolo.getMazzoPesca().isEmpty()) {
			rimischiaMazzo();
		}

		// Quando l'indice rappresenta un giocatore "non umano"
		if (indiceGiocatore != 0) {
			Giocatore avversarioDiTurno = getListaGiocatori().get(indiceGiocatore);

			// Crea una lista di carte giocabili fra quelle che il giocatore ha in mano
			List<Carta> carteGiocabili = avversarioDiTurno.getMano().getListCarte().stream()
					.filter(x -> isCartaValida(x)).collect(Collectors.toList());

			// Se non ha carte giocabili in mano
			if (carteGiocabili.isEmpty()) {
				// Se la prima carta del mazzoPesca è giocabile, pesca e lancia la carta
				if (isCartaValida(tavolo.getMazzoPesca().controllaPrimaCarta())) {
					tavolo.getMazzoScarti().appoggiaCarta(tavolo.getMazzoPesca().prendiPrimaCarta());
					azioneEffettuata = controllaAzione();
				}
				// Se non è giocabile, aggiungila alla mano e prosegui
				else {
					avversarioDiTurno.getMano().aggiungiCarta(tavolo.getMazzoPesca().prendiPrimaCarta());
					azioneEffettuata = 0;
				}
			}
			// Se la carta giocabile è una sola la lancia
			else if (carteGiocabili.size() == 1) {
				tavolo.getMazzoScarti().appoggiaCarta(carteGiocabili.get(0));
				avversarioDiTurno.getMano().rimuoviCarta(carteGiocabili.get(0));
				azioneEffettuata = controllaAzione();
			}
			// Se le carte giocabili sono più di una, tirane una casuale
			else if (carteGiocabili.size() > 1) {
				int indiceCarta = rand.nextInt(carteGiocabili.size() - 1);
				tavolo.getMazzoScarti().appoggiaCarta(carteGiocabili.get(indiceCarta));
				avversarioDiTurno.getMano().rimuoviCarta(carteGiocabili.get(indiceCarta));
				azioneEffettuata = controllaAzione();
			}

			// Se, dopo aver lanciato la carta, questa è un cambia colore, scegli il colore
			// da impostare
			if (tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore
					|| tavolo.getMazzoScarti().controllaPrimaCarta() instanceof QuattroPiuCambiaColore) {
				impostaColoreDaLancio(scegliColore());

			}
			// Se non è un cambia colore, imposta valore e colore regnante dalla carta a
			// terra
			else {
				setColoreDaTerra();
				setValoreDaTerra();
			}

		}

	}

	/**
	 * This method let the controller understand which card the oppnent has thrown,
	 * to play the correct "emotion" sound
	 * 
	 * @return and int representing which action the opponent made
	 */
	public int getAzioneEffettuata() {
		return azioneEffettuata;
	}

	/**
	 * This method checks if the opponent has thrown an effect card or not
	 * 
	 * @return 1 if the thrown card is an effect card, 2 if it's a normal one
	 */
	public int controllaAzione() {
		if (tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto
				|| tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore)
			return 1;
		return 2;
	}

	/**
	 * This method let the opponent choose a color when throws a wild card. The
	 * logic behind it is that the occurrences of cards of each color in the
	 * opponent's hand are counted. The color is then changed to the one that has
	 * the most card occurrences in the opponent's hand
	 * 
	 * @return the color to be setted ad the dominant
	 */
	public Colore scegliColore() {
		Map<Colore, Integer> coloreMax = new HashMap<>();
		coloreMax.put(Colore.ROSSO, 0);
		coloreMax.put(Colore.GIALLO, 0);
		coloreMax.put(Colore.BLU, 0);
		coloreMax.put(Colore.VERDE, 0);
		for (Carta c : tavolo.getListaGiocatori().get(indiceGiocatore).getMano().getListCarte()) {
			if (c.getColore().equals(Colore.ROSSO))
				coloreMax.put(Colore.ROSSO, coloreMax.get(Colore.ROSSO) + 1);
			else if (c.getColore().equals(Colore.GIALLO))
				coloreMax.put(Colore.GIALLO, coloreMax.get(Colore.GIALLO) + 1);
			else if (c.getColore().equals(Colore.BLU))
				coloreMax.put(Colore.BLU, coloreMax.get(Colore.BLU) + 1);
			else
				coloreMax.put(Colore.VERDE, coloreMax.get(Colore.VERDE) + 1);
		}
		coloreRegnante = Collections.max(coloreMax.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
		return coloreRegnante;
	}

	public int getIndiceCorrente() {
		return indiceGiocatore;
	}

	public TavoloDaGioco getTavolo() {
		return tavolo;
	}

	public boolean getPartitaFinita() {
		return isGiocoFinito;
	}

	/**
	 * 7 cards are distribuited at the beggining of the game
	 */
	public void distribuisciCarte() {
		for (Giocatore g : tavolo.getListaGiocatori()) {
			for (int k = 0; k < 7; k++) {
				g.getMano().aggiungiCarta(tavolo.getMazzoPesca().prendiPrimaCarta());
			}
		}
	}

	public void giraPrimaCarta() {
		tavolo.getMazzoScarti().appoggiaCarta(tavolo.getMazzoPesca().prendiPrimaCarta());
	}

	public void controllaEffettoCartaATerra() {
		if (tavolo.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto)
			((CartaEffetto) tavolo.getMazzoScarti().controllaPrimaCarta()).applicaEffetto(this);
	}

	public void impostaColoreDaLancio(Colore c) {
		coloreRegnante = c;
	}

	public void setColoreDaTerra() {
		coloreRegnante = tavolo.getMazzoScarti().controllaPrimaCarta().getColore();
	}

	public void setValoreDaTerra() {
		valoreATerra = tavolo.getMazzoScarti().controllaPrimaCarta().getValore();
	}

	public Colore getColoreRegnante() {
		return coloreRegnante;
	}

	public Valore getValoreATerra() {
		return valoreATerra;
	}

	/**
	 * This method checks if the selected card is allowed to be thrown.
	 * 
	 * @param c represents the selected card
	 * @return {@code true} if the selected card is valid
	 */
	public boolean isCartaValida(Carta c) {

		// se il colore o il valore della carta che voglio lanciare è uguale a quella a
		// terra, o è una carta nera, true
		if (c.getColore().equals(coloreRegnante) || c.getValore().equals(valoreATerra)
				|| c.getColore().equals(Colore.NERO))
			return true;
		// in tutti gli altri casi, non va bene
		return false;
	}

	/**
	 * When it is necessary to shuffle the deck (such as if the cards in the
	 * mazzoPesca run out) the mazzoScarti is emptied into the mazzoPesca and then
	 * it is shuffled
	 */
	public void rimischiaMazzo() {

		while (!tavolo.getMazzoScarti().isEmpty()) {
			tavolo.getMazzoPesca().appoggiaCarta(tavolo.getMazzoScarti().prendiPrimaCarta());
		}
		tavolo.getMazzoPesca().mescola();
	}

	/**
	 * Reverse the direction of the turn
	 */
	public void setDirezioneGiro() {
		isSensoOrario = !isSensoOrario;
	}

	/**
	 * Returns the sense of the turn
	 * 
	 * 
	 * @return {@code true} if the direction is clockwise
	 */
	public boolean getDirezioneGiro() {
		return isSensoOrario;
	}

	/**
	 * Increase the index to define the player who has to take the turn
	 */
	public void prossimoGiocatore() {

		if (isSensoOrario) {
			// se è l'ultimo giocatore della lista (il numero 3) allora il prossimo turno
			// sarà del giocatore numero 0
			if (indiceGiocatore == tavolo.getListaGiocatori().size() - 1)
				indiceGiocatore -= indiceGiocatore;
			// altrimenti, incrementa l'indice normalmente
			else
				indiceGiocatore++;
		}
		// se il giorno è antiorario
		else {
			// e se l'indice è uguale a zero, il prossimo turno deve essere del giocatore
			// con
			// l'ultimo indice (3 in questo caso)
			if (indiceGiocatore == 0)
				indiceGiocatore = tavolo.getListaGiocatori().size() - 1;
			// altrimenti decrementa l'indice normalmente
			else
				indiceGiocatore--;
		}
	}

	/**
	 * 
	 * This method was created to calculate the next player to the one taking the
	 * turn at the moment. This is to be able to apply card effects (such as draw2)
	 * to the next player without modifying the "indiceGiocatore" field.
	 * 
	 * @param i is the int representing the current index of the game
	 * @return an int representing which is the next player in the game
	 */
	public int prossimoGiocatoreIpotetico(int i) {
		if (isSensoOrario) {
			if (i == tavolo.getListaGiocatori().size() - 1)
				i -= i;
			else
				i++;
		} else {
			if (i == 0)
				i = tavolo.getListaGiocatori().size() - 1;
			else
				i--;
		}
		return i;
	}

	/**
	 * This method checks if the current player has won
	 * 
	 * @return {@code true} if the current player's hand has 0 cards
	 */
	public boolean controllaVittoria() {
		if (tavolo.getListaGiocatori().get(indiceGiocatore).getMano().getNCarteManoGiocatore() == 0) {
			isGiocoFinito = true;
			return true;
		}
		return false;

	}

	public List<Giocatore> getListaGiocatori() {
		return tavolo.getListaGiocatori();
	}

	public Carta getPrimaCartaScarti() {
		return tavolo.getMazzoScarti().controllaPrimaCarta();
	}

}
