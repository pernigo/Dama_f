package dama.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dama.model.gameModel;
import dama.view.Field;

/**
 * prepara l'array delle mosse da fare e controlla se si puo fare
 */
public class PrepareMove implements ActionListener {
	/**
	 * campo da gioco
	 */
	private gameModel g; 
	/**
	 * coordinate delle righe, colonne e della casella selezionata
	 */
	private int i,j,x;
	/**
	 * finestra del campo da gioco
	 */
	private Field f;
	/**
	 * prepara l'array delle mosse da fare e controlla se si puo fare
	 * @param g questa è la classe g passata come parametro per agire su di essa
	 * @param i coordinata i riga 
	 * @param j coordinata j colonna
	 * @param f questa è la classe f passata come parametro per agire su di essa
	 * passate al costruttore PrepareMove
	 */
	public PrepareMove(gameModel g,int i,int j,Field f){
	this.g=g;
	this.i=i;
	this.j=j;
	this.f=f;
	this.x=g.getNumberAt(i,j);
	}
	/**
	 * azzero le mosse
	 * rimmuovo i suggerimenti
	 * 
	 * se posso mangiare: eat[]
	 * se posso fare la mossa: m[]
	 * 
	 * g.setMoveAt: salvo la mossa che posso fare
	 * g.setNumberAt: mostro graficamente il suggerimento  
	 * 
	 * infine aggiorno la finestra
	 */
	public void actionPerformed(ActionEvent e) {
		g.reset();	//azzero mosse
		f.removeSugg();
		//se non sono allìestremo destro e la pedina a destra e libera avanza
		boolean[] m=g.moves.canMove(i,j);
		boolean[] eat=g.moves.canEat(i,j,x);
		
		if(m[0]){
			g.setMoveAt(i-1,j+1,1);
			g.setNumberAt(i-1, j+1,gameModel.sugg);
		}
		if(m[1]){
			g.setMoveAt(i-1,j-1,-1);
			g.setNumberAt(i-1,j-1,gameModel.sugg);
		}
				
		if(eat[0]){
			g.setMoveAt(i-2,j+2,2);
			g.setNumberAt(i-2,j+2,gameModel.sugg);
		}
		
		if(eat[1]){
			g.setMoveAt(i-2,j-2,-2);
			g.setNumberAt(i-2,j-2,gameModel.sugg);
		}
		
		if(x==gameModel.whiteDama){						

			if(m[2]){	
				g.setMoveAt(i+1,j+1,10); 
				g.setNumberAt(i+1,j+1,gameModel.sugg);
			}
			if(eat[2]){
				g.setMoveAt(i+2,j+2,20);
				g.setNumberAt(i+2,j+2,gameModel.sugg);
			}
			
			if(m[3]){
				g.setMoveAt(i+1,j-1,-10);
				g.setNumberAt(i+1,j-1,gameModel.sugg);
			}
			if(eat[3]){
				g.setMoveAt(i+2,j-2,-20);
				g.setNumberAt(i+2,j-2,gameModel.sugg);
			}
		}
		f.update();// aggiorno la finestra 
	}


}
