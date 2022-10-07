package model.giocatore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class represents the player, both user and NPCs
 * 
 * @author daniele
 *
 */
public class Giocatore {

	private String nickname;

	private int partiteTotali, vittorieTotali, sconfitteTotali;
	protected Mano manoCarte;
	private boolean hasPescato, isUnoSaid;
	private File fileStats = new File("./src/resources/Stats.txt");

	public Giocatore(String nickname) {
		this.nickname = nickname;
		manoCarte = new Mano();
	}

	public String getNickname() {
		return nickname;
	}

	/**
	 * This method takes care of writing the total, won and lost games of the player
	 * in fileStats. Whenever he is called (when a game is over) the method writes
	 * the new amount of the games to the file.
	 * 
	 * @param vittoria if {@code true} means that the user has won, otherwise the
	 *                 user has lost
	 */
	public void setPartite(boolean vittoria) {

		try {
			List<String> righeStats = Files.readAllLines(Paths.get("./src/resources/Stats.txt"));
			String nomeFile = righeStats.get(0);
			String avatarFile = righeStats.get(1);
			String pTotFile = righeStats.get(2);
			partiteTotali = Integer.parseInt(pTotFile);
			String pVinteFile = righeStats.get(3);
			vittorieTotali = Integer.parseInt(pVinteFile);
			String pPerseFile = righeStats.get(4);
			sconfitteTotali = Integer.parseInt(pPerseFile);

			PrintWriter printWriter;

			printWriter = new PrintWriter(new FileWriter(fileStats));
			printWriter.print("");
			printWriter.println(nomeFile);
			printWriter.println(avatarFile);
			printWriter.println(partiteTotali + 1);

			if (vittoria) {
				printWriter.println(vittorieTotali + 1);
				printWriter.println(sconfitteTotali);
			} else {
				printWriter.println(vittorieTotali);
				printWriter.println(sconfitteTotali + 1);
			}

			printWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Mano getMano() {
		return manoCarte;
	}

	/**
	 * To prevent the user from drawing 2 times in a turn, this variable is set by
	 * passinga boolean
	 * 
	 * @param value {@code true} if the user draws
	 */
	public void setHasPescato(boolean value) {
		hasPescato = value;
	}

	public boolean getHasPescato() {
		return hasPescato;
	}

	/**
	 * 
	 * To ensure that the other players can make the user draw 2 cards if he does
	 * not say one when he has 1 card in his hand
	 * 
	 * @param unoSaid {@code true} if the user clicks the "uno" button
	 */
	public void setIsUnoSaid(boolean unoSaid) {

		isUnoSaid = unoSaid;
	}

	public boolean getIsUnoSaid() {
		return isUnoSaid;
	}

}
