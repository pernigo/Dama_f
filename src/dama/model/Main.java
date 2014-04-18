package dama.model;

import dama.view.Field;

/**
 * il main si occupa semplicemente di allocare un oggetto field (cioe il campo da gioco grafico
 * che contiene la parte logica su cui opera) e lo rende visibile
 */
public class Main {

	public static void main(String[] args){
		new Field().setVisible(true);				
	}
	
}
