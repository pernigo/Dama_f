package dama.view;
import dama.controller.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import dama.model.gameModel;
/**
 * Questa classe si occupa delle caratteristiche delle caselle
 */
public class Cell extends JButton {
	
	//icone per pedina statica
	/**
	 * pedina nera prende la sua immagine
	 */
	private final static ImageIcon b=new ImageIcon("images/blackped.png");
	/**
	 * pedina bianca prende la sua immagine
	 */
	private final static ImageIcon w=new ImageIcon("images/whiteped.png");
	/**
	 * damone nero prende la sua immagine
	 */
	private final static ImageIcon bb=new ImageIcon("images/Bblackped.png");
	/**
	 * damone bianco prende la sua immagine
	 */
	private final static ImageIcon bw=new ImageIcon("images/Bwhiteped.png");
	/**
	 * immagine della casella nera
	 */
	private final static ImageIcon eb=new ImageIcon("images/black.png");
	/**
	 * immagine casella bianca
	 */
	private final static ImageIcon ew=new ImageIcon("images/white.png");
	/**
	 * immagine del suggerimento
	 */
	private final static ImageIcon s=new ImageIcon("images/suggestion.png");
	/**
	 * secondo le coordinate della casella scelta si hanno caratteristiche diverse
	 * @param g questa e la classe g passata come parametro per agire su di essa
	 * @param i coordinata i riga 
	 * @param j coordinata j colonna
	 * @param f questa e la classe f passata come parametro per agire su di essa
	 * 
	 * case gameModel.blackpawn: prende l'immagine della pedina nera
	 * case gameModel.whitepawn: prende l'immagine della pedina bianca, se c'e stata mangiata multipla blocco la possibilità
	 * 		di poter selezionare un'altra pedina 
	 * case gameModel.blackDama:prende l'immagine del damone nera
	 * case gameModel.whiteDama: prende l'immagine del damone bianca, se c'e stata mangiata multipla blocco la possibilità
	 * 		di poter selezionare un'altra pedina 
	 * case gameModel.empty: prende l'immagine della casella nera
	 * case gameModel.sugg: prende l'immagine del suggerimento e richiama DoMove per fare la mossa
	 * default:setIcon(ew): prende l'immagine della casella bianca
	 */
	public Cell(gameModel g,int i,int j,Field f){
		int x=g.getNumberAt(i,j);
		
		switch(x){
			case gameModel.blackpawn: setIcon(b);
			break;		
			case gameModel.whitepawn: setIcon(w);
				if(!g.moves.multiE)
					addActionListener(new PrepareMove(g,i,j,f)); // se c'è stata mangiata multipla blocco le bianche
			break;		
			case gameModel.blackDama: setIcon(bb);
			break;		
			case gameModel.whiteDama: setIcon(bw);
				if(!g.moves.multiE)
					addActionListener(new PrepareMove(g,i,j,f));
			break;			
			case gameModel.empty: setIcon(eb);
			break;
			case gameModel.sugg:setIcon(s);
				addActionListener(new DoMove(g,i,j,f)); // richiama DoMove che fa la mossa
			break;
			default:setIcon(ew);
			break;
			
		}
	}
	
}	

