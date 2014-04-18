package dama.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dama.model.gameModel;
import dama.view.Field;
/**
 * Questa classe permette di eseguire le mosse preparate
 */
public class DoMove implements ActionListener {
	private gameModel g;
	private int i,j;
	private Field f;
	private short eat;
	
	public DoMove(){
	};
	/**	
	 * @param g questa è la classe g passata come parametro per agire su di essa
	 * @param i coordinata i riga 
	 * @param j coordinata j colonna
	 * @param f questa è la classe f passata come parametro per agire su di essa
	 */
	public DoMove(gameModel g,int i,int j,Field f){
		this.g=g;
		this.i=i;
		this.j=j;
		this.f=f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
/**
 * Faccio tutti i casi possibili che si possono fare su quella casella
 * caso 1: Mossa a destra, ma se era possibile mangiare viene soffiata dal metodo blow
 * 		la mossa a destra viene effettuata cambiati logicamente il valore della casella matrice che costruisce il
 *		campo.
 * caso -1:Mossa a sinistra, ma se era possibile mangiare viene soffiata dal metodo blow
 * 		la mossa a sinistra viene effettuata cambiati logicamente il valore della casella matrice che costruisce il
 *		campo.
 * caso 2:Mangiata destra, cambia logicamente il valore della casella della matrice del campo e setta le coordinate
 * 		 e mette vuote le caselle precedenti della mangiata, eat serve per la mangiata multipla
 * caso -2:Mangiata sinistra, cambia logicamente il valore della casella della matrice del campo e setta le coordinate
 * 		 e mette vuote le caselle precedenti della mangiata, eat serve per la mangiata multipla
 * 
 * I primi 4 casi valgono anche per i damoni, perchè scambio le coordinate del valore selezionato  
 * 
 * caso 10: mangiata a destra VERSO IL BASSO per i damoni  
 * caso -10:mangiata a sinistra VERSO IL BASSO per i damoni
 * caso 2:Mangiata destra VERSO IL BASSO, cambia logicamente il valore della casella della matrice del campo e setta le coordinate
 * 		 e mette vuote le caselle precedenti della mangiata, eat serve per la mangiata multipla
 * caso -2:Mangiata sinistra VERSO IL BASSO, cambia logicamente il valore della casella della matrice del campo e setta le coordinate
 * 		 e mette vuote le caselle precedenti della mangiata, eat serve per la mangiata multipla
 *Poi:
 *	Se arriva in fondo diventa damone
 *	rimuove i suggerimenti
 *	inizializza l'array delle mosse
 *	se ho mangiato e sono nel limite delle mangiate 
 *  richiamo multi eat di moves 
 *  
 *  se multiE= true e sono dentro i limiti del numero di mangiate mangio ancora, lancio il messaggio 
 *  della mangiata, aggiorno la finestra, multiE=false, e inizializzo eat a zero  
 */
		switch(g.getMoveAt(i,j)){
		//mossa a destra
		case 1:
			if(!blow(i+1,j-1))
			g.setNumberAt(i,j,g.getNumberAt(i+1,j-1));	//scambio caselle
			g.setNumberAt(i+1,j-1,gameModel.empty);
			break;
		//mossa a sinistra
		case -1:
			if(!blow(i+1,j+1))
				g.setNumberAt(i,j,g.getNumberAt(i+1,j+1));
			g.setNumberAt(i+1,j+1,gameModel.empty);
			break;
		//mangiare a destra
		case 2:	
			g.setNumberAt(i,j,g.getNumberAt(i+2,j-2));
			eat=(short) g.getNumberAt(i+2,j-2); //serve per la mangiata multipla
			g.setNumberAt(i+1,j-1,gameModel.empty);
			g.setNumberAt(i+2,j-2,gameModel.empty);
			g.Beaten();
			break;
		//mangiare a sinistra
		case -2:			
			g.setNumberAt(i,j,g.getNumberAt(i+2,j+2));
			eat=(short) g.getNumberAt(i+2,j+2);
			g.setNumberAt(i+1,j+1,gameModel.empty);
			g.setNumberAt(i+2,j+2,gameModel.empty);
			g.Beaten();
			break;
		//mossa a destra
		case 10:
			if(!blow(i-1,j-1))
				g.setNumberAt(i,j,gameModel.whiteDama);	//scambio caselle
			g.setNumberAt(i-1,j-1,gameModel.empty);
			break;
		//mossa a sinistra
		case -10:
			if(!blow(i-1,j+1))
				g.setNumberAt(i,j,gameModel.whiteDama);
			g.setNumberAt(i-1,j+1,gameModel.empty);
			break;
		//mangiare a destra
		case 20:					
			g.setNumberAt(i,j,gameModel.whiteDama);
			g.setNumberAt(i-1,j-1,gameModel.empty);
			g.setNumberAt(i-2,j-2,gameModel.empty);
			eat=gameModel.whiteDama;
			g.Beaten();
			break;
		//mangiare a sinistra
		case -20:					
			g.setNumberAt(i,j,gameModel.whiteDama);
			g.setNumberAt(i-1,j+1,gameModel.empty);
			g.setNumberAt(i-2,j+2,gameModel.empty);
			eat=gameModel.whiteDama;
			g.Beaten();
			break;
		default:
			break;
		}
		ctrlDama(); // da pedina a damone
		f.removeSugg(); 
		g.reset(); // per sicurezza, Inizialzizza l'array (vuoto)
		if(eat!=0 && g.countE<2)
			g.moves.multiEat(i,j,eat);
			
		if(g.moves.multiE && g.countE<2){
			f.update();
			g.countE++;
			//importante:dopo aver aggiornato tolgo le variabili cosicche gli altri pulsanti non fanno nulla
			g.moves.multiE=false;
			eat=0;
		}
		else{
			g.moves.multiE=false;
			eat=0;
			g.countE=0;
			f.end();
		}

	}
	//questa funzione controlla se ci sono pedine da mangiare e viene richiamata solo se faccio una mossa non di mangiata
	/**
	 * rimuove i suggerimenti dal campo altrimenti i controlli di mangiata non andrebbero a buon fine
	 * controllo se nel movimento fatto la pedina poteva mangiare, se si ritorna VERO e quindi non viene spostata la 
	 * pedina ma solo cancellata (vedi sopra nei case) 
	 * 
	 * @param x coordinata x
	 * @param y coordinata y
	 */
	private  boolean blow(int x, int y) {
		f.removeSugg(); // rimuove i suggerimenti dal campo altrimenti i controlli di mangiata non andrebbero a buon fine
		for(boolean b:g.moves.canEat(x, y, g.getNumberAt(x, y)))
			if(b){
				g.Weaten();
				return true;
			}
		return false;
		
}
	//se una pedina bianca e arrivata in fondo si trasformain damone
	/**
	 * Se una pedina bianca e arrivata in fondo si trasforma in damone
	 */
	private void ctrlDama(){
		for(int j=0;j<8;j++)
			if(g.getNumberAt(0,j)==gameModel.whitepawn)
				g.setNumberAt(0,j,gameModel.whiteDama );
	}
	

}

