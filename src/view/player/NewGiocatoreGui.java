package view.player;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * This class represents a sort of popup label that it's showed only at the very
 * first time this game is played or when the user resets all data. Let the user
 * insert a nickname and a avatar
 * 
 * @author daniele
 *
 */
public class NewGiocatoreGui extends JLabel {

	private static final long serialVersionUID = -256648088253061133L;
	private JButton browse = new JButton("Browse");
	private JButton salva = new JButton("Salva");
	JTextArea name = new JTextArea();

	public NewGiocatoreGui() {
		setOpaque(true);
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		setBounds(440, 350, 400, 100);
		setLayout(null);

		JLabel jname = new JLabel("Inserisci il tuo nome: ");
		jname.setBounds(20, 20, 170, 20);
		jname.setFont(new Font("Helvetica", 1, 15));

		add(jname);

		name.setBounds(180, 20, 200, 20);

		add(name);

		JLabel jfoto = new JLabel("Seleziona il tuo avatar: ");
		jfoto.setBounds(20, 50, 170, 20);
		jfoto.setFont(new Font("Helvetica", 1, 15));

		add(jfoto);

		browse.setBounds(200, 50, 80, 20);
		add(browse);

		salva.setBounds(300, 70, 80, 20);
		add(salva);

	}

	/**
	 * This metod is used from the controller to write the user's nickname on his
	 * tag
	 * 
	 * @return the {@code String} representing the user's nickname
	 */
	public String getNomeGiocatore() {
		return name.getText();
	}

	/**
	 * This button, when pressed, through the controller writes on a txt file the
	 * user details
	 * 
	 * @return the instance of salva button
	 */
	public JButton getSalva() {
		return salva;
	}

	/**
	 * This button, when pressed, through the controller opens a browsing popup to
	 * let the user choose the avatar
	 * 
	 * @return the instance of browse button
	 */
	public JButton getBrowse() {
		return browse;
	}
}
