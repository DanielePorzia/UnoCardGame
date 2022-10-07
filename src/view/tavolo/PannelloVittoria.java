package view.tavolo;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * This class represents the panel that is displayed every time a player runs
 * out of cards in hand, and therefore the game is over.
 * 
 * @author daniele
 *
 */
public class PannelloVittoria extends JLabel {

	private static final long serialVersionUID = -473656793985321025L;

	private Image image;

	private JButton end = new JButton();

	private JLabel esitoPartita;

	private JLabel nPunti = new JLabel();
	private JLabel nTot = new JLabel();
	private JLabel nWin = new JLabel();
	private JLabel nLost = new JLabel();

	private JLabel totPunti;
	private JLabel partiteTotali;
	private JLabel partiteVinte;
	private JLabel partitePerse;

	private JLabel puntiTotali = new JLabel("Punti Totali:");
	private JLabel pTot = new JLabel("P. Totali");
	private JLabel pWin = new JLabel("P. Vinte");
	private JLabel pLost = new JLabel("P. Perse");

	/**
	 * This constructor builds the panel with the correct result
	 * 
	 * @param vittoria    is a boolean value. True: user wins False: user lost
	 * @param puntiAmount is the total sum of the points of the cards that the other
	 *                    players have in their hand
	 */
	public PannelloVittoria(boolean vittoria, String puntiAmount) {

		try {
			if (vittoria)
				esitoPartita = new JLabel("HAI VINTO!");
			else
				esitoPartita = new JLabel("HAI PERSO!");

			image = ImageIO.read(new File("./src/resources/img/StatsMenu.png"));

			List<String> righeStats = Files.readAllLines(Paths.get("./src/resources/Stats.txt"));

			end.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/EsciPiccolo.png"))));

			totPunti = new JLabel(puntiAmount, SwingConstants.CENTER);
			totPunti.setFont(new Font("Helvetica", 1, 30));
			nPunti.add(totPunti);

			String pTotali = righeStats.get(2);
			partiteTotali = new JLabel(pTotali, SwingConstants.CENTER);
			partiteTotali.setFont(new Font("Helvetica", 1, 30));
			nTot.add(partiteTotali);

			String pVinte = righeStats.get(3);
			partiteVinte = new JLabel(pVinte, SwingConstants.CENTER);
			partiteVinte.setFont(new Font("Helvetica", 1, 30));
			nWin.add(partiteVinte);

			String pPerse = righeStats.get(4);
			partitePerse = new JLabel(pPerse, SwingConstants.CENTER);
			partitePerse.setFont(new Font("Helvetica", 1, 30));
			nLost.add(partitePerse);

		} catch (IOException e) {
			e.printStackTrace();
		}

		setIcon(new ImageIcon(image));
		setLayout(null);
		setBounds(340, 100, 600, 600);

		esitoPartita.setFont(new Font("Helvetica", 1, 50));
		esitoPartita.setBounds(160, 170, 400, 50);
		add(esitoPartita);

		// Setto i bounds ed il look per il tasto end
		end.setBounds(190, 90, 200, 100);
		end.setBorderPainted(false);
		end.setFocusPainted(false);
		add(end);

		// Settings scritte Statiche

		puntiTotali.setFont(new Font("Helvetica", 1, 35));
		puntiTotali.setBounds(200, 280, 400, 50);
		add(puntiTotali);

		pTot.setBounds(118, 386, 200, 30);
		pTot.setFont(new Font("Helvetica", 1, 30));
		add(pTot);

		pWin.setBounds(260, 386, 200, 30);
		pWin.setFont(new Font("Helvetica", 1, 30));
		add(pWin);

		pLost.setBounds(378, 386, 200, 30);
		pLost.setFont(new Font("Helvetica", 1, 30));
		add(pLost);

		// Settings contenitori di scritte dinamiche

		nPunti.setBounds(200, 325, 200, 50);
		nPunti.setLayout(new GridLayout());
		add(nPunti);

		nTot.setBounds(143, 420, 50, 50);
		nTot.setLayout(new GridLayout());
		add(nTot);

		nWin.setBounds(280, 420, 50, 50);
		nWin.setLayout(new GridLayout());
		add(nWin);

		nLost.setBounds(410, 420, 50, 50);
		nLost.setLayout(new GridLayout());
		add(nLost);

		end.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				;
			}
		});

	}

}
