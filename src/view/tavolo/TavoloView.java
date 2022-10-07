package view.tavolo;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.EffettoSonoro;
import view.Music;
import view.carta.CartaDxView;
import view.carta.CartaSxView;
import view.carta.CartaUpView;
import view.player.NewGiocatoreGui;

/**
 * 
 * This class is the main one in the view, as it represents everything you see
 * and hear when the game table is displayed
 * 
 * @author daniele
 *
 */
public class TavoloView extends JPanel {

	private static final long serialVersionUID = -3855968925617937366L;

	private Image image;

	private Music audio = new Music();
	private EffettoSonoro sUno;
	private EffettoSonoro sError;
	private NewGiocatoreGui gGui = new NewGiocatoreGui();
	private TagAvatarStaticGui staticGui;

	private Tutorial tutorial = new Tutorial();
	private JLabel manoDx = new JLabel();
	private JLabel manoSx = new JLabel();
	private JLabel manoUp = new JLabel();
	private ManoDown manoDown = new ManoDown();

	private TastoColore tastoColore = new TastoColore();

	private JLabel mazzoScarti = new JLabel();
	private JButton mazzoPesca = new JButton();

	private JButton tastoUno = new JButton();
	private JButton aOn = new JButton();
	private JButton aOff = new JButton();

	private JButton passaTurno = new JButton();

	/**
	 * Creates an instance of TavoloView which also sets component's detail (as
	 * bounds or background)
	 */
	public TavoloView() {
		
		add(tutorial);
		tutorial.setVisible(false);
		// Dentro al try tutto ciò che potrebbe scaturire un'eccezione
		try {
			
			image = ImageIO.read(new File("./src/resources/img/Tavolo.png"));
			aOn.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/AudioOn.png"))));
			aOff.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/AudioOff.png"))));
			tastoUno.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/UnoButton.png"))));
			mazzoPesca.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/cards/mazzoPesca.png"))));

			passaTurno.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Passaturno.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Fuori dal try, tutto ciò che non può generare un'eccezione
		aOn.setBounds(1180, 15, 90, 90);
		aOn.setBorderPainted(false);
		aOn.setFocusPainted(false);

		aOff.setBounds(1180, 15, 90, 90);
		aOff.setBorderPainted(false);
		aOff.setFocusPainted(false);

		tastoUno.setBounds(740, 380, 140, 105);
		tastoUno.setBorderPainted(false);
		tastoUno.setFocusPainted(false);

		mazzoPesca.setBounds(645, 330, 80, 150);
		mazzoPesca.setBorderPainted(false);
		mazzoPesca.setFocusPainted(false);

		mazzoScarti.setBounds(535, 380, 85, 95);

		tastoColore.setVisible(false);
		add(tastoColore);

		passaTurno.setBorderPainted(false);
		passaTurno.setFocusPainted(false);
		passaTurno.setBounds(535, 500, 170, 60);
		passaTurno.setVisible(false);
		add(passaTurno);

		manoSx.setBounds(130, 280, 250, 220);
		manoSx.setLayout(null);

		manoUp.setBounds(450, 220, 400, 90);
		manoUp.setLayout(null);
		
		manoDx.setBounds(880, 280, 250, 215);
		manoDx.setLayout(null);
		
		gGui.setVisible(false); // Imposta la visibilità della gui delle statistiche a false, per non vederla
								// sul menu all'apertura del gioco
		add(gGui);

		setBounds(0, 0, 1280, 800);
		setLayout(null);

	}

	/**
	 * Method wich initialize the table gui, adding all the components. This action
	 * is not performed inside the builder because, if it's the first game (and you
	 * have to add nickname and avatar) an empty table is showed behind, as if
	 * there's no player to play with. Once nickname and avatar are added, all the
	 * components get added in order to start the game.
	 */
	public void inizializzaTavolo() {
		add(mazzoPesca);
		add(mazzoScarti);
		add(tastoUno);
		add(aOn);

		playMusic();
		add(staticGui);

		add(manoDown);
		add(manoSx);
		add(manoUp);
		add(manoDx);

	}

	public void createStaticGui() {
		staticGui = new TagAvatarStaticGui();
	}

	/**
	 * The following 3 methods do the same thing each for a different player: Based
	 * on how many cards the player has in hand when this method is called, the
	 * cards are drawn in the correct number and position
	 * 
	 * @param n
	 */
	public void visualizzaCarteSx(int n) {

		CartaSxView.reset();
		manoSx.removeAll();
		for (int i = 0; i < n; i++) {
			manoSx.add(new CartaSxView());
		}
	}

	public void visualizzaCarteDx(int n) {
		CartaDxView.reset();
		manoDx.removeAll();
		for (int i = 0; i < n; i++) {
			manoDx.add(new CartaDxView());
		}
	}

	public void visualizzaCarteUp(int n) {
		CartaUpView.reset();
		manoUp.removeAll();
		for (int i = 0; i < n; i++) {
			manoUp.add(new CartaUpView());
		}
	}

	/**
	 * This method is called when a player pushes the uno button and has 1 card in
	 * his hand
	 */
	public void playUnoSound() {
		sUno = new EffettoSonoro("UNO!");
		sUno.play();
	}

	/**
	 * This method is called when a player pushes the uno button and has more than 1
	 * card in his hand
	 */
	public void playErrorSound() {
		sError = new EffettoSonoro("error");
		sError.play();
	}

	/**
	 * This method is called at the beginning of the game, and everytime the
	 * "AudioOff" button is clicked
	 */
	public void playMusic() {
		audio.play();
	}

	/**
	 * This method is called everytime the "AudioOn" button is clicked
	 */
	public void stopMusic() {
		audio.stop();
	}

	/**
	 * this is the label where's the tutorial in on
	 * 
	 * @return thei nstance of tutorial to get the click of ok button from the
	 *         controller
	 */
	public Tutorial getTutorial() {
		return tutorial;
	}

	/**
	 * This is the static gui where's name and avatars are.
	 * 
	 * @return the instance to be used by the controller
	 */
	public TagAvatarStaticGui getStaticGui() {
		return staticGui;
	}

	/**
	 * This label cointains 4 buttons to choice the color after a wild card has been
	 * thrown
	 * 
	 * @return the instance of jlabel
	 */
	public TastoColore getTastoColore() {
		return tastoColore;
	}

	/**
	 * This button represents the player's choice to skip his turn
	 * 
	 * @return the instance of Jbutton
	 */
	public JButton getPassaTurno() {
		return passaTurno;
	}

	/**
	 * This Label represents the user's space where his cards will be added from the
	 * controller
	 * 
	 * @return instance of manoUp layeredpane
	 */
	public ManoDown getManoDown() {
		return manoDown;
	}

	/**
	 * This Label represents the left player's space where his cards will be added
	 * from the controller
	 * 
	 * @return instance of manoSx label
	 */
	public JLabel getManoSx() {
		return manoSx;
	}

	/**
	 * This Label represents the upper player's space where his cards will be added
	 * from the controller
	 * 
	 * @return instance of manoUp label
	 */
	public JLabel getManoUp() {
		return manoUp;
	}

	/**
	 * This Label represents the right player's space where his cards will be added
	 * from the controller
	 * 
	 * @return instance of manoDx label
	 */
	public JLabel getManoDx() {
		return manoDx;
	}

	/**
	 * The istance of thaht gui is used from the controller to read the input
	 * nickname and the avatar file
	 * 
	 * @return the instance of gGui label
	 */
	public NewGiocatoreGui getGGui() {
		return gGui;
	}

	/**
	 * This button plays the "Uno" sound when pressed if the player has 1 card in
	 * his hand, the "error" sound otherwise. Check the {@code SuonoUno} class for
	 * the sound.
	 * 
	 * @return the instance of tastoUno button
	 */
	public JButton getTastoUno() {
		return tastoUno;
	}

	/**
	 * This button, when pressed, toggles off the music and adds to audioOff button
	 * to TavoloView. Check the {@code Music} class for the music
	 * 
	 * @return the instance of audioOn button
	 */
	public JButton getAOn() {
		return aOn;
	}

	/**
	 * This button, when pressed, toggles on the music and adds to audioOn button to
	 * TavoloView Check the {@code Music} class for the music.
	 * 
	 * @return the instance of AudioOff button
	 */
	public JButton getAOff() {
		return aOff;
	}

	/**
	 * This button, when pressed, removes the first card of the deck and adds it to
	 * the player's hand.
	 * 
	 * @return the instance of mazzoPesca button
	 */
	public JButton getMazzoPesca() {
		return mazzoPesca;
	}

	/**
	 * This Label simply shows the last discarded card
	 * 
	 * @return the instance of mazzoScarti label
	 */
	public JLabel getMazzoScarti() {
		return mazzoScarti;
	}

	@Override
	public void paint(Graphics g) {
		((Graphics2D) g).drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponents(g);
	}
}
