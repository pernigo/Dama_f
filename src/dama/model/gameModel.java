package dama.model;
import dama.controller.Move;
/**
 * Questa classe implementa la matrice del campo da gioco ed elementi per modificarla
 */
public class gameModel {

	/**
	 *matrice del campo da gioco
	 */ 
	private int[][] tab; // matrice 
	/**
	 * array delle mosse dell'utente
	 */
	private int[] m=new int[32]; // mosse utente
	/**
	 * numero di pedine
	 */
	private int white=12,black=12;
	/**
	 * numero di pedine che sono arrivato a mangiare di seguito (parte a contare da zero) 
	 */
	public int countE=0; // contatore multipla utente
	/**
	 * elemento della classe Move 
	 */
	//classe delle mosse contentuta nel model
	public Move moves=new Move(this); 

	
	//variabili statiche pedina
	/**
	 * numero identificativo pedina nera
	 */
	public final static int blackpawn=1;	//black pawn
	/**
	 * numero identificativo pedina bianca
	 */
	public final static int whitepawn=-1;	//white pawn
	/**
	 * numero identificativo casella vuota
	 */
	public final static int empty=17;	//black empty
	/**
	 * numero identificativo damone nero
	 */
	public final static int blackDama=2;	//big black pawn
	/**
	 * numero identificativo damone bianca
	 */
	public final static int whiteDama=-2;	//big white pawn
	/**
	 * numero identificativo del suggerimento (trasparente)
	 */
	public final static int sugg=5;	//suggestion (transparent)
	/**
	 *  costruttore del campo 
	 */
	public gameModel(){
		
		tab=new int[8][8];
		//inzzializzo le prime 3 righe con pedine nere
		/**
		 * inzzializzo le prime 3 righe con pedine nere
		 */
		for(int j=0;j<8;j++)
			for(int i=0;i<3;i++){
				if((i+j)%2==0)
				tab[i][j]=blackpawn;
				else
				tab[i][j]=0;	
			}
		//inzzializzo le righe centrali senza pedine
		/**
		 * inzzializzo le righe centrali senza pedine
		 */
		for(int j=0;j<8;j++)
			for(int i=3;i<5;i++){
				if((i+j)%2==0)
				tab[i][j]=empty;
				else
				tab[i][j]=0;	
		}

		//inzzializzo le ultime 3 righe con pedine bianche
		/**
		 * inzzializzo le ultime 3 righe con pedine bianche
		 */
		for(int j=0;j<8;j++)
			for(int i=5;i<8;i++){
				if((i+j)%2==0)
				tab[i][j]=whitepawn;
				else
				tab[i][j]=0;	
			}
	}
	/**
	 * prende il valore delle coordinate 
	 * @param x valore della coordinata x
	 * @param y valore della coordinata y
	 * @return il valore contenuto in quelle coordinate
	 */
	public int getNumberAt(int x,int y){
		return tab[x][y];
	}
	/**
	 * setta il vaore delle coordinate
	 * @param x valore della coordinata x
	 * @param y valore della coordinata y
	 * @param n con un valore che graficamente sara  una pedina con diverse caratteristiche a seconda del valore
	 */
	public void setNumberAt(int x,int y,int n){
		tab[x][y]=n;
	}
	
	//questi due metodi modificano l'array che contiene le posizioni delle mosse
	
	/**
	 * prende il valore delle coordinate ogni punto e mappato in modo univoco gli uni dagli altri
	 * @param x valore della coordinata x
	 * @param y valore della coordinata y
	 * @return il tipo di mossa che c'e in quella coordinata
	 */
	public int getMoveAt(int x,int y){	
		return m[(x*8+y)/2];
	}
	/**
	 * setta il vaore delle coordinate
	 * @param x valore della coordinata x
	 * @param y valore della coordinata y
	 * @param n Ã¨ identifica una mossa 
	 */
	public void setMoveAt(int x,int y,int n){
		m[(x*8+y)/2]=n;
	}
	/**
	 * inizializza l'array delle mosse a zero
	 */
	public void reset(){
		m=new int[32];
	}
	/**
	 * decrementa il numero di pedine bianche
	 */
	public void Weaten(){
		white--;
	}
	/**
	 *  decrementa il numero di pedine nere
	 */
	public void Beaten(){
		black--;
	}
	/**
	 * @return il numero di pedine bianche
	 */
	public int getWhite(){
		return white;
	}
	/**
	 * @return il numero delle pedine nere
	 */
	public int getBlack(){
		return black;
	}
	/**
	 * @return -1 se le pedine bianche sono finite, 1 se le pedine nere sono finite, 0 altrimenti
	 * 			
	 */
	public int win(){
		if(white==0)
			return -1;
		if(black==0)
			return 1;
		return 0;
	}
/**
 * perdita forzata: imposta logicamente il numero delle  pedine nere a zero
 */
	public void ForceLose() {
		black=0;
	}
	
}
