package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import view.menu.Menu;
import view.tavolo.TavoloView;

/**
 * This class represents the main frame of Juno, the main container for all
 * components
 * 
 * @author daniele
 *
 */
public class Finestra extends JFrame {

	private static final long serialVersionUID = 688682402377023573L;
	private TavoloView tavoloGui;
	private Menu menuGui;
	
	private static Finestra instance;

	/**
	 * Using the Singleton Pattern, if there's no instance of this class, this
	 * method creates one
	 * 
	 * @return an instance of Finestra class
	 * 
	 */
	public static Finestra getInstance(){
		if (instance == null)
			instance = new Finestra();
		return instance;
	}

	/**
	 * Due to the singleton pattern, this builder is private. Creates a JFrame at
	 * the screen's centre, adds to it a JPanel (menuGui)
	 * 
	 */
	private Finestra(){
		super("JUNO");
		tavoloGui = new TavoloView();
		menuGui = new Menu();
		add(menuGui);
		// add(tavoloGUI);
		this.setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1280, 800);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}

	/**
	 * Used to call action on TavoloView from the controller
	 * 
	 * @return the instance of TavoloView
	 */
	public TavoloView getTavoloGUI() {
		return tavoloGui;
	}

	/**
	 * Used to call action on TavoloView from the controller
	 * 
	 * @return the instance of Menu
	 */
	public Menu getMenuGui() {
		return menuGui;
	}

}
