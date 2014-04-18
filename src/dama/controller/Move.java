package dama.controller;

import dama.model.gameModel;

/**
 * questa classe implementa il controllo delle mosse dell'utente e del computer
 * e la mangiata multipla dell'utente
 */
public class Move {
	/**
	 * campo da gioco logico come campo privato
	 */
	private gameModel g;
	/**
	 * flag per le mangiate multiple
	 */
	public boolean multiE;
	
	/**
	 * @param g riceve il campo da gioco su cui opera e lo salva nel suo campo privato
	 */
	public Move(gameModel g){
		this.g=g;
	}
	
	/**
	 * 
	 * @param i:coordinata x della pedina
	 * @param j:coordinata y della pedina
	 * @return:un array di boolean che rappresenta le direzioni in cui 
	 * la pedina puo muovere, piu precisamente, dal punto di vista della pedina:
	 * 0=in alto a destra
	 * 1=in alto a sinistra				  1   0
	 * in piu solo per le dame				X
	 * 2=in basso a destra				  3   2
	 * 3=in basso a sinistra
	 * 
	 * il controllo delle mosse avviene banalmente guardando
	 * se nella direzione presa in considerazione ce una cella vuota e se sono nei limiti del campo
	 */
	public boolean[] canMove(int i,int j){
		int t=g.getNumberAt(i,j);
		int x=abs(t)==1?t:t/2;
		boolean[] can=new boolean[abs(t)*2];
		
		if((i>0 && x==gameModel.whitepawn) || (i<7 && x==gameModel.blackpawn)){
			if(j<7 && g.getNumberAt(i+x,j+1)==gameModel.empty)
				can[0]=true;
			if(j>0 && g.getNumberAt(i+x,j-1)==gameModel.empty)
				can[1]=true;	
		}
		if ((i<7 && t==gameModel.whiteDama) || (i>0 && t==gameModel.blackDama)){
			if(j<7 && g.getNumberAt(i-x,j+1)==gameModel.empty)
				can[2]=true;
			if(j>0 && g.getNumberAt(i-x,j-1)==gameModel.empty)
				can[3]=true;			
		}
		
		return can;
	}
	
	/**
	 * 
	 * @param i:coordinata x della pedina
	 * @param j:coordinata y della pedina
	 * @param t:tipo della pedina che puo mangiare
	 * @return:un array di boolean che rappresenta le direzioni in cui 
	 * la pedina puo mangiare, piu precisamente, dal punto di vista della pedina:
	 * 0=in alto a destra
	 * 1=in alto a sinistra				  1   0
	 * in piu solo per le dame				X
	 * 2=in basso a destra				  3   2
	 * 3=in basso a sinistra
	 * 
	 * 
	 * il controllo delle mangiate avviene banalmente guardando
	 * se nella direzione presa in considerazione ce una cella della pedina di tipo opposto e inferiore
	 * di quella presa in considerazione (cioe se sono una dama nera puo essere una dama o una pedina bianca)
	 * e se sono nei limiti del campo per la mangiata 
	 */
	public boolean[] canEat(int i,int j,int t){
		//su x ottengo -1 se la pedina e bianca e 1 se la pedina e nera in modo da sapere in che verso andare
		int x=abs(t)==1?t:t/2;
		boolean[] can=new boolean[abs(t)*2];
		
		if((i>1 && (t==gameModel.whitepawn || t==gameModel.whiteDama)) || (i<6 && (t==gameModel.blackpawn || t==gameModel.blackDama))){
			if(j<6 && (g.getNumberAt(i+x,j+1)==-t || g.getNumberAt(i+x,j+1)==(-t/2))  && g.getNumberAt(i+(x*2),j+2)==gameModel.empty){
				can[0]=true;
			}
			if(j>1 && (g.getNumberAt(i+x,j-1)==-t || g.getNumberAt(i+x,j-1)==(-t/2)) && g.getNumberAt(i+(x*2),j-2)==gameModel.empty)
				can[1]=true;			
		}
		if ((i<6 && t==gameModel.whiteDama) || (i>1 && t==gameModel.blackDama)){
			if(j<6 && (g.getNumberAt(i-x,j+1)==-t || g.getNumberAt(i-x,j+1)==-t/2)&& g.getNumberAt(i-(x*2),j+2)==gameModel.empty)
				can[2]=true;
			if(j>1 && (g.getNumberAt(i-x,j-1)==-t || g.getNumberAt(i-x,j-1)==-t/2 )&& g.getNumberAt(i-(x*2),j-2)==gameModel.empty)
				can[3]=true;			
		}
		
		return can;
	}
	
	/**
	 * @param x intero qualsiasi
	 * @return il valore assoluto dell'intero passato come parametro
	 */
	private int abs(int x) {
		if(x<0)
			return -x;
		return x;
	}
	
	/**
	 * 
	 * @param x coordinata x della pedina
	 * @param y coordinata y della pedina
	 * @param t tipo della pedina nella coordinata data
	 * 
	 * (questa funzione viene chiamata solo dopo una mangiata effettuata dall'utente)
	 * se la pedina nella coordinata data puo mangiare (richiamo funzione canEat)
	 * viene attivata graficamente e logicamente la mossa di mangiata
	 * e alzata il flag della mangiata multipla
	 */
	public void multiEat(int x,int y,int t){
		
		if(canEat(x,y,t)[0]){
			g.setMoveAt(x-2,y+2,2);
			g.setNumberAt(x-2,y+2,gameModel.sugg);
			multiE=true;
		}
		
		if(canEat(x,y,t)[1]){
			g.setMoveAt(x-2,y-2,-2);
			g.setNumberAt(x-2,y-2,gameModel.sugg);
			multiE=true;
		}
		
		if(t==gameModel.whiteDama){		
			if(canEat(x,y,t)[2]){
				g.setMoveAt(x+2,y+2,20);
				g.setNumberAt(x+2,y+2,gameModel.sugg);
				multiE=true;
				}	
			if(canEat(x,y,t)[3]){
				g.setMoveAt(x+2,y-2,-20);
				g.setNumberAt(x+2,y-2,gameModel.sugg);
				multiE=true;
				}
		}	
	}
}
