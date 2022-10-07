package view.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * This class represents a summary of all player's stat: Avatar, nickname, total
 * games, win games, lost games.
 * 
 * It has two button to back to the home and to reset player's data
 * 
 * @author daniele
 *
 */
public class StatsView extends JLabel {

	private static final long serialVersionUID = 9113474456970147055L;
	private Image image;
	private JButton back = new JButton();
	private JButton reset = new JButton();

	private JLabel avatar = new JLabel();
	private JLabel nicknameTrue = new JLabel();
	private JLabel pTot = new JLabel();
	private JLabel pWin = new JLabel();
	private JLabel pLost = new JLabel();

	public StatsView() {

		try {
			image = ImageIO.read(new File("./src/resources/img/StatsMenu.png"));

			// Il tasto back per tornare alla home
			back.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/back.png"))));

			// Tasto reset per azzerare i dati
			reset.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/azzeraDati.png"))));

		} catch (IOException e) {
			e.printStackTrace();
		}

		setIcon(new ImageIcon(image));
		setLayout(null);
		setBounds(340, 100, 600, 600);

		// Setto i bounds ed il look per il tasto back
		back.setBounds(95, 90, 50, 50);
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		add(back);

		// Setto i bounds e il look per il tasto reset
		reset.setBounds(110, 150, 200, 100);
		reset.setBorderPainted(false);
		reset.setFocusPainted(false);
		add(reset);

		// Avatar
		JLabel avatarName = new JLabel("Avatar: ");
		avatarName.setBounds(130, 250, 200, 30);
		avatarName.setFont(new Font("Helvetica", 1, 30));

		// Nickname
		JLabel nickname = new JLabel("Nickname: ");
		nickname.setBounds(130, 300, 200, 30);
		nickname.setFont(new Font("Helvetica", 1, 30));

		// Partite Totali
		JLabel pTotali = new JLabel("Partite Totali: ");
		pTotali.setBounds(130, 350, 200, 30);
		pTotali.setFont(new Font("Helvetica", 1, 30));

		// Partite Vinte
		JLabel pVinte = new JLabel("Partite Vinte: ");
		pVinte.setBounds(130, 400, 200, 30);
		pVinte.setFont(new Font("Helvetica", 1, 30));

		// Partite perse
		JLabel pPerse = new JLabel("Partite Perse: ");
		pPerse.setBounds(130, 450, 250, 30);
		pPerse.setFont(new Font("Helvetica", 1, 30));

		leggiFile();

		// Aggiungi tutti i component
		add(avatarName);
		add(nickname);
		add(pTotali);
		add(pVinte);
		add(pPerse);

		// Setta stili e posizioni di avatar, nickname, partite totali, vinte e perse
		avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		avatar.setBackground(Color.BLACK);
		avatar.setBounds(350, 160, 110, 110);
		avatar.setOpaque(true);
		add(avatar);

		nicknameTrue.setBounds(350, 300, 200, 30);
		nicknameTrue.setFont(new Font("Helvetica", 1, 30));
		add(nicknameTrue);

		pTot.setBounds(350, 350, 200, 30);
		pTot.setFont(new Font("Helvetica", 1, 30));
		add(pTot);

		pWin.setBounds(350, 400, 200, 30);
		pWin.setFont(new Font("Helvetica", 1, 30));
		add(pWin);

		pLost.setBounds(350, 450, 200, 30);
		pLost.setFont(new Font("Helvetica", 1, 30));
		add(pLost);
	}

	/**
	 * This method reads the statsFile and if there's words on it (so it's not the
	 * first game), set labels' text with the read infos otherwise set's the avatar
	 * to a placeholder
	 */
	private void leggiFile() {

		try {
			// Leggi le righe del file Stats
			List<String> righeStats = Files.readAllLines(Paths.get("./src/resources/Stats.txt"));

			// Se non è la prima partita, dunque ci sono delle statistiche, leggile
			if (righeStats.size() > 0) {
				// Seleziona il nickname
				nicknameTrue.setText(righeStats.get(0));

				// Leggi il path dell'avatar
				Image imageAvatar = ImageIO.read(new File(righeStats.get(1)));
				Image imageScaled = imageAvatar.getScaledInstance(100, 100, 16);
				avatar.setIcon(new ImageIcon(imageScaled));

				// Leggi partite totali
				pTot.setText(righeStats.get(2));

				// Leggi partite vinte
				pWin.setText(righeStats.get(3));

				// Leggi partite perse
				pLost.setText(righeStats.get(4));
			}

			// Se non ci sono caratteri nel file Stats, ovvero se non si è mai fatta neanche
			// una partita
			else {
				avatar.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/placeholder.png"))));
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Used to call action on back button from the controller
	 * 
	 * @return instance of the back JButton
	 */
	public JButton getBack() {
		return back;
	}

	/**
	 * Used to call action on reset button from the controller
	 * 
	 * @return instance of the reset JButton
	 */
	public JButton getReset() {
		return reset;
	}

	/**
	 * Used to modify the avatar from the controller
	 * 
	 * @return instance of the avatar JLabel
	 */
	public JLabel getAvatar() {
		return avatar;
	}

	/**
	 * Used to modify the nickname from the controller
	 * 
	 * @return instance of the nickname JLabel
	 */
	public JLabel getNickname() {
		return nicknameTrue;
	}

	/**
	 * Used to modify total games from the controller
	 * 
	 * @return instance of total games JLabel
	 */
	public JLabel getPTot() {
		return pTot;
	}

	/**
	 * Used to modify win games from the controller
	 * 
	 * @return instance of win games JLabel
	 */
	public JLabel getPWin() {
		return pWin;
	}

	/**
	 * Used to modify lost games from the controller
	 * 
	 * @return instance of lost games JLabel
	 */
	public JLabel getPLost() {
		return pLost;
	}

}
