package model.carte;

/**
 * An enum with every value for each card and a method to get that points.
 *
 * @author daniele
 *
 */
public enum Valore {
	ZERO 				(0),
	UNO  				(1),
	DUE 				(2),
	TRE 				(3),
	QUATTRO 			(4),
	CINQUE  			(5),
	SEI 				(6),
	SETTE 				(7),
	OTTO 				(8),
	NOVE				(9),
	PESCA2 				(20),
	STOP				(20), 
	INVERTI				(20),
	CAMBIACOLORE		(50), 
	CAMBIACOLOREPIU4	(50);
	
	private final int PUNTI;
	
	Valore(int points) {
		this.PUNTI=points;
	}
	public int getPunti() {
		return PUNTI;
	}
	
}

