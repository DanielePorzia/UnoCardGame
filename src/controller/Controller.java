package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.carte.CambiaColore;
import model.carte.Carta;
import model.carte.CartaEffetto;
import model.carte.Colore;
import model.carte.QuattroPiuCambiaColore;
import model.carte.Stop;
import model.giocatore.Giocatore;
import model.tavolo.TavoloDaGioco;
import view.EffettoSonoro;
import view.Finestra;
import view.carta.CartaATerraView;
import view.carta.CartaGiocatoreView;
import view.menu.Menu;
import view.tavolo.PannelloVittoria;
import view.tavolo.TavoloView;

/**
 * This class is the main class to let the two packages (model and view) to
 * communicate. It has a method which adds this listener to all components which
 * needs one. There is also a method which, depending on which button is
 * pressed, does an action.
 * 
 * @author daniele
 *
 */
public class Controller implements ActionListener {

	private Finestra f;
	private TavoloDaGioco tg;
	private TavoloView t;
	private Menu m;
	private File fileAvatar;
	private File fileStats = new File("./src/resources/Stats.txt");

	public Controller(Finestra f, TavoloDaGioco tg) {
		this.f = f;
		this.tg = tg;
		t = f.getTavoloGUI();
		m = f.getMenuGui();

		addListenerToComponents();

	}

	/**
	 * This method allows every visual component to have a listener
	 */
	public void addListenerToComponents() {

		// Adding listeners to menu's buttons
		m.getGioca().addActionListener(this);
		m.getStatistiche().addActionListener(this);
		m.getEsci().addActionListener(this);

		// Adding listeners to statsview's button
		m.getSView().getBack().addActionListener(this);
		m.getSView().getReset().addActionListener(this);

		// Adding listeners to tableGUI's buttons
		t.getAOn().addActionListener(this);
		t.getAOff().addActionListener(this);
		t.getTastoUno().addActionListener(this);
		t.getMazzoPesca().addActionListener(this);

		t.getTastoColore().getRosso().addActionListener(this);
		t.getTastoColore().getGiallo().addActionListener(this);
		t.getTastoColore().getBlu().addActionListener(this);
		t.getTastoColore().getVerde().addActionListener(this);
		t.getPassaTurno().addActionListener(this);

		// Adding listeners to NewGiocatoreGui's buttons
		t.getGGui().getBrowse().addActionListener(this);
		t.getGGui().getSalva().addActionListener(this);

		// Adding listener to the tutorial button "ok"
		t.getTutorial().getDone().addActionListener(this);

	}

	/**
	 * Thanks to this method, each time a button is pressed, something happens
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Se si preme il tasto gioca nel menù
		if (e.getSource().equals(m.getGioca())) {

			// Se è la prima volta che si gioca
			if (fileStats.length() == 0) {

				f.add(t);
				t.getGGui().setVisible(true);
				f.remove(m);

			}
			// Se non è la prima partita
			else {
				try {
					List<String> righeStats = Files.readAllLines(Paths.get("./src/resources/Stats.txt"));
					String nome = righeStats.get(0);
					tg.addGiocatore(0, new Giocatore(nome));
					t.getTutorial().setVisible(true);
					t.createStaticGui();
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				f.add(t);
				f.remove(m);
				tg.getManager().distribuisciCarte();
				t.inizializzaTavolo();

				// Imposta alla mano del giocatore sia il controller che il giocatore stesso,
				// per prenderne poi la lista di carte
				t.getManoDown().setGiocatore(tg.getListaGiocatori().get(0));
				t.getManoDown().setController(this);

				// Aggiungi carte in mano ai giocatori
				// All'inizio della partita tutti hanno 7 carte
				t.getManoDown().visualizzaCarte();
				t.visualizzaCarteSx(tg.getListaGiocatori().get(1).getMano().getNCarteManoGiocatore());
				t.visualizzaCarteUp(tg.getListaGiocatori().get(2).getMano().getNCarteManoGiocatore());
				t.visualizzaCarteDx(tg.getListaGiocatori().get(3).getMano().getNCarteManoGiocatore());

				// Aggiungi la vista della prima carta del mazzo scarti
				t.getMazzoScarti().add(new CartaATerraView(tg.getMazzoScarti().controllaPrimaCarta()));

				f.repaint();
				return;
			}
		}

		// Se si preme il tasto browse per selezionare un avatar all'inizio della prima
		// partita
		else if (e.getSource().equals(t.getGGui().getBrowse())) {
			JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
			chooser.setDialogTitle("Seleziona Avatar");

			FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
			chooser.setFileFilter(imageFilter);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.showOpenDialog(f);
			fileAvatar = chooser.getSelectedFile();
			return;
		}

		// Se, solo dopo aver selezionato nickname e avatar, si preme salva
		else if (e.getSource().equals(t.getGGui().getSalva())) {

			if (t.getGGui().getNomeGiocatore().length() > 0 && fileAvatar != null) {

				try {
					PrintWriter printWriter = new PrintWriter(new FileWriter(fileStats));
					printWriter.println(t.getGGui().getNomeGiocatore().replaceAll("\\s+", ""));
					printWriter.println(fileAvatar.getAbsolutePath());
					printWriter.println(0);
					printWriter.println(0);
					printWriter.println(0);
					printWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				tg.addGiocatore(0, new Giocatore(t.getGGui().getNomeGiocatore()));

				t.remove(t.getGGui());

				tg.getManager().distribuisciCarte();
				t.createStaticGui();
				t.inizializzaTavolo();

				t.getManoDown().setGiocatore(tg.getListaGiocatori().get(0));
				t.getManoDown().setController(this);
				t.getTutorial().setVisible(true);

				// All'inizio della partita tutti hanno 7 carte

				t.getManoDown().visualizzaCarte();
				t.visualizzaCarteSx(tg.getListaGiocatori().get(1).getMano().getNCarteManoGiocatore());
				t.visualizzaCarteUp(tg.getListaGiocatori().get(2).getMano().getNCarteManoGiocatore());
				t.visualizzaCarteDx(tg.getListaGiocatori().get(3).getMano().getNCarteManoGiocatore());

				// Aggiungi la vista della prima carta del mazzo scarti
				t.getMazzoScarti().add(new CartaATerraView(tg.getMazzoScarti().controllaPrimaCarta()));

				t.repaint();
			}
			return;
		}

		// Se si preme il tasto statistiche nel menu iniziale
		else if (e.getSource().equals(m.getStatistiche())) {
			m.getGioca().setVisible(false);
			m.getStatistiche().setVisible(false);
			m.getEsci().setVisible(false);
			m.getSView().setVisible(true);
			return;
		}

		// Se si preme sul tasto back delle statistiche
		else if (e.getSource().equals(m.getSView().getBack())) {
			m.getGioca().setVisible(true);
			m.getStatistiche().setVisible(true);
			m.getEsci().setVisible(true);
			m.getSView().setVisible(false);
			return;
		}

		// Se si preme sul tasto reset delle statistiche
		else if (e.getSource().equals(m.getSView().getReset())) {
			m.getSView().getNickname().setText("");
			m.getSView().getPTot().setText("");
			m.getSView().getPWin().setText("");
			m.getSView().getPLost().setText("");

			try {
				m.getSView().getAvatar()
						.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/placeholder.png"))));
				PrintWriter printWriter = new PrintWriter(new FileWriter(fileStats));
				printWriter.print("");
				printWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		// Se si preme il tasto esci nel menu iniziale
		else if (e.getSource().equals(m.getEsci())) {
			System.exit(0);
		}

		// Se si clicca sul tasto audio on per metterlo off
		else if (e.getSource().equals(t.getAOn())) {
			t.add(t.getAOff());
			t.remove(t.getAOn());
			t.stopMusic();
			return;
		}

		// Se si clicca sul tasto audio off per metterlo on
		else if (e.getSource().equals(t.getAOff())) {
			t.add(t.getAOn());
			t.remove(t.getAOff());
			t.playMusic();
			return;
		}

		// Se si preme il tasto ok del tutorial
		else if (e.getSource().equals(t.getTutorial().getDone())) {
			t.getTutorial().setVisible(false);
			t.repaint();
			return;
		}

		// Se si clicca sul mazzoPesca per pescare una carta
		else if (e.getSource().equals(t.getMazzoPesca())) {

			// se lo user non ha pescato ed è il suo turno
			if (!tg.getListaGiocatori().get(0).getHasPescato() && tg.getManager().getIndiceCorrente() == 0) {

				// pesca la prima carta e visualizzala
				tg.getListaGiocatori().get(0).getMano().aggiungiCarta(tg.getMazzoPesca().prendiPrimaCarta());
				t.getManoDown().visualizzaCarte();

				// rendi visibile il tasto passa turno
				t.getPassaTurno().setVisible(true);

				tg.getListaGiocatori().get(0).setHasPescato(true);
				t.repaint();
			}
			return;
		}

		// Se il giocatore preme il tasto passaTurno
		else if (e.getSource().equals(t.getPassaTurno())) {

			t.getPassaTurno().setVisible(false);
			tg.getListaGiocatori().get(0).setHasPescato(false);
			t.getStaticGui().resetColorPics();
			t.repaint();

			prossimoTurno();
		}

		// Se si preme sul tasto uno
		else if (e.getSource().equals(t.getTastoUno())) {

			// se lo user è in diritto di dire "uno", ovvero ha 1 carta
			if (tg.getListaGiocatori().get(0).getMano().getNCarteManoGiocatore() == 1) {

				tg.getListaGiocatori().get(0).setIsUnoSaid(true);
				t.playUnoSound();
			}
			// se si preme quando si ha più di una carta in mano
			else
				t.playErrorSound();
			return;
		}

		// Se premo sul tasto rosso del tastoColore
		else if (e.getSource().equals(t.getTastoColore().getRosso())) {

			// il colore dominante viene impostato a rosso
			tg.getManager().impostaColoreDaLancio(Colore.ROSSO);

			t.getTastoColore().setVisible(false);
			t.getMazzoScarti().removeAll();

			// Dipendentemente se il cambiacolore lanciato è un +4 o quello normale,
			// viene creata una view (solo una view, non viene aggiunta al mazzo)
			// della carta colorata del colore dominante, per rendere comprensibile
			// il colore dominante
			if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore)
				t.getMazzoScarti().add(new CartaATerraView("CCRosso"));
			else
				t.getMazzoScarti().add(new CartaATerraView("CC4Rosso"));

			t.getPassaTurno().setVisible(false);
			tg.getListaGiocatori().get(0).setHasPescato(false);
			prossimoTurno();
			return;
		}

		// Se premo sul tasto giallo del tastoColore
		else if (e.getSource().equals(t.getTastoColore().getGiallo())) {

			// viene impostato il colore dominante a giallo
			tg.getManager().impostaColoreDaLancio(Colore.GIALLO);

			t.getTastoColore().setVisible(false);
			t.getMazzoScarti().removeAll();

			// Dipendentemente se il cambiacolore lanciato è un +4 o quello normale,
			// viene creata una view (solo una view, non viene aggiunta al mazzo)
			// della carta colorata del colore dominante, per rendere comprensibile
			// il colore dominante
			if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore)
				t.getMazzoScarti().add(new CartaATerraView("CCGiallo"));
			else
				t.getMazzoScarti().add(new CartaATerraView("CC4Giallo"));

			t.getPassaTurno().setVisible(false);
			tg.getListaGiocatori().get(0).setHasPescato(false);
			prossimoTurno();
			return;
		}

		// Se premo sul tasto blu del tastoColore
		else if (e.getSource().equals(t.getTastoColore().getBlu())) {

			// colore dominante impostato su blu
			tg.getManager().impostaColoreDaLancio(Colore.BLU);

			t.getTastoColore().setVisible(false);
			t.getMazzoScarti().removeAll();

			// Dipendentemente se il cambiacolore lanciato è un +4 o quello normale,
			// viene creata una view (solo una view, non viene aggiunta al mazzo)
			// della carta colorata del colore dominante, per rendere comprensibile
			// il colore dominante
			if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore)
				t.getMazzoScarti().add(new CartaATerraView("CCBlu"));
			else
				t.getMazzoScarti().add(new CartaATerraView("CC4Blu"));
			t.getPassaTurno().setVisible(false);
			tg.getListaGiocatori().get(0).setHasPescato(false);
			prossimoTurno();
			return;
		}

		// Se premo sul tasto verde del tastoColore
		else if (e.getSource().equals(t.getTastoColore().getVerde())) {

			// colore dominante impostato su verde
			tg.getManager().impostaColoreDaLancio(Colore.VERDE);

			t.getTastoColore().setVisible(false);
			t.getMazzoScarti().removeAll();

			// Dipendentemente se il cambiacolore lanciato è un +4 o quello normale,
			// viene creata una view (solo una view, non viene aggiunta al mazzo)
			// della carta colorata del colore dominante, per rendere comprensibile
			// il colore dominante
			if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore)
				t.getMazzoScarti().add(new CartaATerraView("CCVerde"));
			else
				t.getMazzoScarti().add(new CartaATerraView("CC4Verde"));
			t.getPassaTurno().setVisible(false);
			tg.getListaGiocatori().get(0).setHasPescato(false);
			prossimoTurno();
			return;
		}

	}

	/**
	 * This is the method that manages the clicks on each user card, defining the
	 * various actions and coordinating model and view
	 * 
	 * @param cartaCliccata is the card which is clicked by the user
	 */
	public void cartaCliccata(CartaGiocatoreView cartaCliccata) {

		// Il primo controllo serve a capire se lo user ha il diritto di tirare carte
		if (tg.getManager().getIndiceCorrente() == 0) {

			// Il secondo controllo è per vedere se la carta può essere lanciata
			if (tg.getManager().isCartaValida(cartaCliccata.getCarta())) {

				// lancia al carta (modifica il model)
				tg.getListaGiocatori().get(0).getMano().rimuoviCarta(cartaCliccata.getCarta());
				tg.getMazzoScarti().appoggiaCarta(cartaCliccata.getCarta());
				tg.getManager().setColoreDaTerra();
				tg.getManager().setValoreDaTerra();

				// aggiungi la view della carta a terra (modifica la view)
				t.getMazzoScarti().removeAll();
				t.getMazzoScarti().add(new CartaATerraView(tg.getManager().getPrimaCartaScarti()));

				t.getManoDown().visualizzaCarte();

				// Se lo user finisce le carte
				if (tg.getManager().controllaVittoria()) {

					// calcola i punti totali del vincitore
					int punti = 0;
					for (Giocatore g : tg.getManager().getListaGiocatori())
						for (Carta c : g.getMano().getListCarte()) {
							punti += c.getPunti();
						}

					// aggiorna il dato delle partite vinte e totali
					tg.getListaGiocatori().get(0).setPartite(true);
					t.removeAll();

					// aggiungi suono vittoria e pannello vittoria che mostra i dati del player e i
					// punti fatti
					EffettoSonoro vittoria = new EffettoSonoro("Vittoria");
					vittoria.play();
					t.add(new PannelloVittoria(true, String.valueOf(punti)));

					t.repaint();
					return;

				}
				t.repaint();

				// se un cambiacolore viene lanciato, mostra la scelta del colore regnante
				if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CambiaColore
						|| tg.getMazzoScarti().controllaPrimaCarta() instanceof QuattroPiuCambiaColore) {

					t.getTastoColore().setVisible(true);
					return;
				}
				// se è una carta effetto, applicane l'effetto
				else if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto) {
					tg.getManager().controllaEffettoCartaATerra();
				}

				t.getPassaTurno().setVisible(false);
				tg.getListaGiocatori().get(0).setHasPescato(false);

				// rimuovi il quadrato rosso allo user per segnalarne il turno
				t.getStaticGui().resetColorPics();

				prossimoTurno();

			}
		}
	}

	/**
	 * This is the method that manages the opponents' turn, both from the model side
	 * (calling the appropriate method) and from the view side by changing the gui
	 */
	public void prossimoTurno() {
		if (tg.getManager().getPartitaFinita())
			return;
		// Se lo user non ha detto uno con una carta in mano ed il turno del giocatore
		// di fronte a lui
		if (!tg.getListaGiocatori().get(0).getIsUnoSaid()
				&& tg.getListaGiocatori().get(0).getMano().getNCarteManoGiocatore() == 1
				&& tg.getManager().getIndiceCorrente() == 2) {

			// al 10% fai pescare 2 carte
			Random rand = new Random();
			int unoNotSaid = rand.nextInt(10);
			switch (unoNotSaid) {
			case 0:
				tg.getListaGiocatori().get(0).getMano().aggiungiCarta(tg.getMazzoPesca().prendiPrimaCarta());
				tg.getListaGiocatori().get(0).getMano().aggiungiCarta(tg.getMazzoPesca().prendiPrimaCarta());
				break;
			}
			tg.getListaGiocatori().get(0).setIsUnoSaid(false);
			t.getManoDown().visualizzaCarte();
		}

		// avanza l'indice del turno
		tg.getManager().prossimoGiocatore();

		// se è stata lanciata una carta effetto, modifica la view delle carte dei
		// giocatori
		if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto) {
			t.getManoDown().visualizzaCarte();
			t.visualizzaCarteSx(tg.getListaGiocatori().get(1).getMano().getNCarteManoGiocatore());
			t.visualizzaCarteUp(tg.getListaGiocatori().get(2).getMano().getNCarteManoGiocatore());
			t.visualizzaCarteDx(tg.getListaGiocatori().get(3).getMano().getNCarteManoGiocatore());
			t.repaint();
		}

		// colora il bordo dell'avatar del giocatore di turno di rosso
		switch (tg.getManager().getIndiceCorrente()) {
		case 0 -> t.getStaticGui().setUserVisualTurn();
		case 1 -> t.getStaticGui().setPic1VisualTurn();
		case 2 -> t.getStaticGui().setPic2VisualTurn();
		case 3 -> t.getStaticGui().setPic3VisualTurn();
		}

		// il giocatore di turno, annuncia il proprio turno
		if (tg.getManager().getIndiceCorrente() != 0) {
			EffettoSonoro presentazione = new EffettoSonoro(
					t.getStaticGui().getNameOpponent(tg.getManager().getIndiceCorrente()) + "/saluto");
			presentazione.play();
		}

		// chiama il metodo nel model che gestisce le azioni dell'avversario
		tg.getManager().turnoAvversario();

		// crea un timer di 5 secondi prima di far visualizzare cosa ha fatto
		// l'avversario
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

				if (tg.getManager().getIndiceCorrente() != 0) {

					// A seconda di ciò che fa il personaggio, fai dire qualcosa
					switch (tg.getManager().getAzioneEffettuata()) {
					case 0:
						EffettoSonoro emozionePesca = new EffettoSonoro(
								t.getStaticGui().getNameOpponent(tg.getManager().getIndiceCorrente()) + "/pescato");
						emozionePesca.play();
						break;
					case 1:
						EffettoSonoro emozioneCrudele = new EffettoSonoro(
								t.getStaticGui().getNameOpponent(tg.getManager().getIndiceCorrente()) + "/crudele");
						emozioneCrudele.play();
						break;
					case 2:
						break;
					}
					t.getStaticGui().resetColorPics();
				}
				t.repaint();
				// mostra la carta lanciata a terra
				t.getMazzoScarti().removeAll();
				t.getMazzoScarti().add(new CartaATerraView(tg.getManager().getPrimaCartaScarti()));

				// visualizza il numero di carte corrette in mano all'avversario
				switch (tg.getManager().getIndiceCorrente()) {
				case 1 -> t.visualizzaCarteSx(tg.getListaGiocatori().get(1).getMano().getNCarteManoGiocatore());
				case 2 -> t.visualizzaCarteUp(tg.getListaGiocatori().get(2).getMano().getNCarteManoGiocatore());
				case 3 -> t.visualizzaCarteDx(tg.getListaGiocatori().get(3).getMano().getNCarteManoGiocatore());
				}

				if (tg.getMazzoScarti().controllaPrimaCarta() instanceof CartaEffetto) {
					t.getManoDown().visualizzaCarte();
					t.visualizzaCarteSx(tg.getListaGiocatori().get(1).getMano().getNCarteManoGiocatore());
					t.visualizzaCarteUp(tg.getListaGiocatori().get(2).getMano().getNCarteManoGiocatore());
					t.visualizzaCarteDx(tg.getListaGiocatori().get(3).getMano().getNCarteManoGiocatore());
					t.repaint();
				}

				// se l'avversazio ha lanciato un cambiacolore, chiama il metodo che
				// rende la carta del colore selezionato, così da rendere comprensibile
				// cosa lanciare
				ifCambioColoreATerra();

				// se il giocatore ha finito le carte
				if (tg.getManager().controllaVittoria()) {

					// aggiorna il contatore delle partite totali e perse
					tg.getListaGiocatori().get(0).setPartite(false);
					t.removeAll();

					// parte il suono della sconfitta e il pannello con i dati riassuntivi
					EffettoSonoro sconfitta = new EffettoSonoro("Sconfitta");
					sconfitta.play();
					t.add(new PannelloVittoria(false, "0"));

					t.repaint();
					return;
				}

				// Controlla l'effetto della carta solo se il giocatore ne ha lanciata una
				// Questo previene situazioni in cui se viene tirata una carta effetto (inverti
				// giro ad esempio)ed il giocatore successivo non lancia nessuna carta, alla 
				// fine del suo turno non viene di nuovo invertito il giro
				if (tg.getManager().controllaAzione() != 0) {
					tg.getManager().controllaEffettoCartaATerra();
					t.repaint();
				}

				// se l'inidice è quello dello user, ma a terra c'è uno stop, passa il turno
				if (tg.getManager().getIndiceCorrente() == 0
						&& (tg.getMazzoScarti().controllaPrimaCarta() instanceof Stop)) {
					prossimoTurno();
				}
				// altrimenti se l'indice attuale non è quello dello user, passa il turno
				else if (tg.getManager().getIndiceCorrente() != 0) {
					prossimoTurno();
				}

			}
		}, 5000);

	}

	/**
	 * This method simply repaints the wild card in the discard pile depending on
	 * which is the color chosen by the opponents
	 */
	public void ifCambioColoreATerra() {

		if (tg.getMazzoScarti().controllaPrimaCarta().getClass().equals(CambiaColore.class)) {
			t.getMazzoScarti().removeAll();
			if (tg.getManager().getColoreRegnante().equals(Colore.ROSSO)) {
				t.getMazzoScarti().add(new CartaATerraView("CCRosso"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.GIALLO)) {
				t.getMazzoScarti().add(new CartaATerraView("CCGiallo"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.BLU)) {
				t.getMazzoScarti().add(new CartaATerraView("CCBlu"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.VERDE)) {
				t.getMazzoScarti().add(new CartaATerraView("CCVerde"));
			}
		}

		else if (tg.getMazzoScarti().controllaPrimaCarta().getClass().equals(QuattroPiuCambiaColore.class)) {
			t.getMazzoScarti().removeAll();
			if (tg.getManager().getColoreRegnante().equals(Colore.ROSSO)) {
				t.getMazzoScarti().add(new CartaATerraView("CC4Rosso"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.GIALLO)) {
				t.getMazzoScarti().add(new CartaATerraView("CC4Giallo"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.BLU)) {
				t.getMazzoScarti().add(new CartaATerraView("CC4Blu"));
			}

			else if (tg.getManager().getColoreRegnante().equals(Colore.VERDE)) {
				t.getMazzoScarti().add(new CartaATerraView("CC4Verde"));
			}
		}
	}

}