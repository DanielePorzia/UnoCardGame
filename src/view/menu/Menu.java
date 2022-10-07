package view.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * This class represents the game's menu, the first panel to be added at the
 * JFrame(Finestra)
 * 
 * @author daniele
 *
 */
public class Menu extends JPanel {


	private static final long serialVersionUID = 5407464013584483452L;
	private Image image;

	private JButton gioca = new JButton();
	private JButton stats = new JButton();
	private JButton esci = new JButton();

	private StatsView sView = new StatsView();

	/**
	 * This constructor creates an instance of menu GUI, also creates three buttons
	 * "Gioca", "Statistiche" and "Esci" Has an hidden Label (StatsView) which
	 * becomes visible if "Statistiche" button is pressed.
	 */
	public Menu() {

		try {
			image = ImageIO.read(new File("./src/resources/img/Sfondo.png"));

			gioca.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Gioca.png"))));
			gioca.setBounds(50, 300, 400, 200);
			gioca.setBorderPainted(false);
			gioca.setFocusPainted(false);

			stats.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Statistiche.png"))));
			stats.setBounds(435, 300, 400, 200);
			stats.setBorderPainted(false);
			stats.setFocusPainted(false);

			esci.setIcon(new ImageIcon(ImageIO.read(new File("./src/resources/img/Esci.png"))));
			esci.setBounds(830, 300, 400, 200);
			esci.setBorderPainted(false);
			esci.setFocusPainted(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(0, 0, 1280, 800);
		setLayout(null);
		add(gioca);
		add(stats);
		add(esci);
		sView.setVisible(false);
		add(sView);
		
		
	}
	


	/**
	 * Used to call action on StatisticheView from the controller when the "return"
	 * button (inside Statsview Class)is pressed
	 * 
	 * @return
	 */
	public StatsView getSView() {
		return sView;
	}

	/**
	 * Used to call action on Menu from the controller when the "gioca" button is
	 * pressed
	 * 
	 * @return the instance of Gioca button
	 */
	public JButton getGioca() {
		return gioca;
	}

	/**
	 * Used to call action on Menu from the controller when the "statistiche" button
	 * is pressed
	 * 
	 * @return the instance of Statistiche button
	 */
	public JButton getStatistiche() {
		return stats;
	}

	/**
	 * Used to call action on Menu from the controller when the "esci" button is
	 * pressed
	 * 
	 * @return the instance of Esci button
	 */
	public JButton getEsci() {
		return esci;
	}

	@Override
	public void paint(Graphics g) {
		((Graphics2D) g).drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponents(g);
	}

}
