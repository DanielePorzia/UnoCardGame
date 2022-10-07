import controller.Controller;
import model.tavolo.TavoloDaGioco;
import view.Finestra;

@SuppressWarnings("unused")

/**
 * This is the main class, the one who calls the entire game
 * @author daniele
 *
 */
public class JUno {

	public static void main(String[] args){
		
		Finestra f = Finestra.getInstance();
		TavoloDaGioco tg = TavoloDaGioco.getInstance();
		Controller c = new Controller(f,tg);
	}
}
