package dama.controller;

import java.util.Random;
import dama.model.gameModel;

/**
 * Questa classe implementa l'intelligenza artificiale del computer
 */
public class Engine{
	
	/**
	 * contenie privatamente il modello di gioco su cui opera
	 */
	private gameModel g;
	/**
	 * un oggetto random per scegliere alcune mosse casualmente
	 */
	private Random random=new Random();
	/**
	 * un array di interi per memorizzare le mosse possibili
	 */
	private int[] mov=new int[64];
	/**
	 * un array di interi per memorizzare le mangiate possibili in determinate coordinate 
	 */
	private int[] eat=new int[64];
	/**
	 * intero coordinata x finale dopo una mangiata
	 */
	private int end_x=-1;
	/**
	 * intero coordinata y finale dopo una mangiata
	 */
	private int end_y=-1;
	/**
	 * boolean alzato se e possibile fare una mangiata multipla
	 */
	private boolean MEat;
	
	/**
	 * il costruttore riceve come parametro il gameModel
	 * @param g modello di gioco su cui l'intelligenza deve agire
	 */
	public Engine(gameModel g){
		this.g=g;
	}

	/**
	 * aggiorna gli array delle mangiate e mosse possibili
	 * inizializza un booleano e che viene alzato se viene eseguita una mangiata
	 * 
	 * entro in un ciclo che esegue la prima mangiata possibile:
	 * salva le posizioni finali della mangiata, scambia le caselle e decrementa il numero di pedine bianche; 
	 * poi continua a mangiare finche e possibile
	 * 
	 * se non c'� stata una mangiata:
	 * controllo se l'array delle mosse e zero, se si forzo la perdita del computer ed esco dalla funzione
	 * controllo casualmente all'interno dell'array delle mosse se ce una mossadisponibile
	 * ed eseguo la prima che trovo (il damone fa una mossa casuale in una direzione possibile)
	 * 
	 * controllo se ci sono pedine da trasformae in dame
	 * azzero le mosse e le mangiate possibili
	 */
	public void move(){
		
		update();
		boolean e=false;
		
		do{
		for(int n=0;n<64;n++){
			int x=n/8;
			int y=n%8;
			if(eat[n]==1){
				end_x=x+2;
				end_y=y-2;
				g.setNumberAt(x+2,y-2,g.getNumberAt(x,y));
				g.setNumberAt(x,y,gameModel.empty);
				g.setNumberAt(x+1,y-1,gameModel.empty);
				g.Weaten();
				e=true;
				break;
			}
		
			if(eat[n]==2){
				end_x=x+2;
				end_y=y+2;
				g.setNumberAt(x+2,y+2,g.getNumberAt(x,y));
				g.setNumberAt(x,y,gameModel.empty);
				g.setNumberAt(x+1,y+1,gameModel.empty);
				g.Weaten();
				e=true;
				break;
			}
			if(eat[n]==4){
				end_x=x-2;
				end_y=y+2;
				g.setNumberAt(x-2,y+2,g.getNumberAt(x,y));
				g.setNumberAt(x,y,gameModel.empty);
				g.setNumberAt(x-1,y+1,gameModel.empty);
				g.Weaten();
				e=true;
				break;
			}
			if(eat[n]==5){
				end_x=x-2;
				end_y=y-2;
				g.setNumberAt(x-2,y-2,g.getNumberAt(x,y));
				g.setNumberAt(x,y,gameModel.empty);
				g.setNumberAt(x-1,y-1,gameModel.empty);
				g.Weaten();
				e=true;
				break;
			}
		}
		
		ctrlDama();
		ctrlMEat();
		}while(MEat);
		
		if(!e){
			boolean b=false;
			for (int elements:mov)
				if(elements!=0){
					b=true;
					break;
				}
			if(!b){
				g.ForceLose();
				return;
			}
				
		int n=random.nextInt(64);
		while(mov[n]==0){
			n=random.nextInt(64);
		}
		int x=n/8;
		int y=n%8;
		
		if(mov[n]==1){
			g.setNumberAt(x+1,y-1,g.getNumberAt(x,y));
			g.setNumberAt(x,y,gameModel.empty);
		}
		
		if(mov[n]==2){
			g.setNumberAt(x+1,y+1,g.getNumberAt(x,y));
			g.setNumberAt(x,y,gameModel.empty);
		}
		if(mov[n]==3){
			int c=random.nextInt(100)%2==0?1:-1;		
			g.setNumberAt(x+1,y+c,g.getNumberAt(x,y));
			g.setNumberAt(x,y,gameModel.empty);
		}
		
		//fa una mossa in una direzione a caso
		if(mov[n]>3){
			int c=random.nextInt(100)%2==0?1:-1;
			int c2=random.nextInt(100)%2==0?1:-1;

			while(true){
				c=random.nextInt(100)%2==0?1:-1;
				c2=random.nextInt(100)%2==0?1:-1;
				if((x+c2<=7 && x+c2>=0 && y+c<=7 && y+c>=0) && g.getNumberAt(x+c2,y+c)==gameModel.empty)
					break;
			}
			g.setNumberAt(x+c2,y+c,g.getNumberAt(x,y));
			g.setNumberAt(x,y,gameModel.empty);	
			
		}
		
		}
		
		ctrlDama();
		mov=new int[64];
		eat=new int[64];
	}
	
/**
 * inzzializzo il flag della manguiata multipla a false e azzero le mangiate possibili
 * controllo che le posizioni finali della mangiata non siano nulle
 * se si proseguo altrimenti esco
 * controllo che pedina a eseguito la mangiata e la salvo
 * se la pedina che ha appena mangiato puo ancora mangiare viene salvata la mossa dei mangiata nell'array 
 * annulo le posizioni finali di mangiata e alzo il flag della mangiata multipla
 * che fa continuare il do while
 */
	private void ctrlMEat() {
		MEat=false;
		eat=new int[64];
		
		if(end_x+end_y!=-2){
		int t=g.getNumberAt(end_x, end_y);
		
		if((t==gameModel.blackpawn || t==gameModel.blackDama) ){
			if(g.moves.canEat(end_x,end_y,t)[0])//posso mangiare a destra
				eat[end_x*8+end_y]=2;
			
			if(g.moves.canEat(end_x,end_y,t)[1])//a sinistra
				eat[end_x*8+end_y]=1;
		}
		if(t==gameModel.blackDama){
			if(g.moves.canEat(end_x,end_y,gameModel.blackDama)[2]){
				eat[end_x*8+end_y]=4;
			}
			if(g.moves.canEat(end_x,end_y,gameModel.blackDama)[3]){
				eat[end_x*8+end_y]=5;
			}
		}
		end_x=-1;
		end_y=-1;
		MEat=true;
		}
		
	}
	
/**
 * In tutto il campo da gioco conrollo le mosse possibili e quelle piu sicure dalle pedine nere e le salvo negli array
 * eat se e una mangiata
 * mov se e una mossa
 * e i movimenti sono codificati in 1 a sinistra,2 a destra
 * per i damoni 4 a sinistra indietro e 5 a destra indietro
 * il salvatggio della mossa viene fatto in modo che se puo muovere sia a detsra che a sinistra
 * entrambe le mosse verranno prese in considerazione
 */
	private void update(){

		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(g.getNumberAt(i,j)==gameModel.blackpawn || g.getNumberAt(i,j)==gameModel.blackDama){
					if(g.moves.canEat(i,j,g.getNumberAt(i,j))[0])//posso mangiare a destra
						eat[i*8+j]=2;
					if(g.moves.canEat(i,j,g.getNumberAt(i,j))[1])//a sinistra
						eat[i*8+j]=1;
					if(g.moves.canMove(i,j)[1] && safe(i+1,j-1,g.getNumberAt(i,j)))//a sinistra
						mov[i*8+j]+=1;
					if(g.moves.canMove(i,j)[0] && safe(i+1,j+1,g.getNumberAt(i,j)))//a destra
						mov[i*8+j]+=2;	
				}
				
				if(g.getNumberAt(i,j)==gameModel.blackDama){
					if(g.moves.canEat(i,j,gameModel.blackDama)[2]){
						eat[i*8+j]=4;
						break;
					}
					if(g.moves.canEat(i,j,gameModel.blackDama)[3]){
						eat[i*8+j]=5;
						break;
					}
					if(g.moves.canMove(i,j)[2] && safe(i-1,j+1,g.getNumberAt(i,j)))
						mov[i*8+j]+=4;
					if(g.moves.canMove(i,j)[3] && safe(i-1,j+1,g.getNumberAt(i,j)))
						mov[i*8+j]+=5;	
				}
			}
			
		}
	
	/**
	 * se una pedina nera e arrivata in fondo viene trasformata in dama
	 */
	private void ctrlDama(){
		for(int j=0;j<8;j++)
			if(g.getNumberAt(7,j)==gameModel.blackpawn)
				g.setNumberAt(7,j,gameModel.blackDama );
	}
	
	/**
	 * 
	 * @param x coordinata x pedina
	 * @param y coordinata y pedina
	 * @param t tipo di pedina
	 * @return true se la mossa da fare andra in una cella sicura e false se non e sicura
	 */
	private boolean safe(int x, int y,int t){
		if(x<7 && y>0 && y<7 && ((g.getNumberAt(x+1,y+1)<0 && g.getNumberAt(x-1,y-1)==gameModel.empty) || (g.getNumberAt(x+1,y-1)<0)&& g.getNumberAt(x-1,y-1)==gameModel.empty))
			return false;
		if(t==gameModel.blackDama && x<7 && x>0 && y>0 && y<7 && ((g.getNumberAt(x-1,y+1)==gameModel.whiteDama && g.getNumberAt(x+1,y-1)==gameModel.empty) || (g.getNumberAt(x-1,y-1)==gameModel.whiteDama && g.getNumberAt(x+1,y+1)==gameModel.empty)))
			return false;
		return true;
	}
}
