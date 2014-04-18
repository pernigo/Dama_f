package dama.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dama.controller.*;
import dama.model.gameModel;

/**
 * questa classe implementa graficamente il campo da gioco e il pannello dei messaggi
 * e si occupa del ridisegno alternativo del campo
 *
 */
public class Field extends JFrame{
	/**
	 * campo da gioco privatosu cui opero
	 */
	private gameModel g;
	/**
	 * intelligenza artficiale che opera sul campo
	 */
	private Engine computer;
	/**
	 * Menu bar in cui memorizzo il numero di pedine dei giocatori
	 */
	private JMenuBar menuBar;
	
	/**
	 * creo la finestra
	 * associo un nome "Dama",
	 * una dimensione 704x704,
	 * la rendo non modificabile in grandezza
	 * aggiungo il listener per la chiusura
	 * inzizzializzo un nuovo gioco
	 * inizzializzo la barra superiore
	 */
	public Field(){
		
		setTitle("Dama");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		setSize(704,704);
		setResizable(false);
		
		addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0){}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
				new Closing().setVisible(true);
			}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
				
		newgame();
		setBar(this);
	}

	/**
	 * Questa funzione inizzializza il JMenuBar indicando il colore dei giocatori e il numero delle loro
	 * pedine e con il pulsante per potersi arrendere
	 * @param f viene passato la finestra su cui agisce l'actionlistener cioe l'oggetto stesso
	 */
	private void setBar(final Field f){
		menuBar=new JMenuBar();
		
		JButton surr= new JButton("Surrender");
		surr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new Win(-1,f).setVisible(true);	
			}
		});
		
		menuBar.setLayout(new GridLayout(1,3));
		JLabel p=new JLabel("Player pawns: "+g.getWhite());
		p.setForeground(Color.WHITE);
		p.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel c=new JLabel("Computer pawns: "+g.getBlack());
		c.setHorizontalAlignment(SwingConstants.CENTER);
		
		menuBar.setBackground(new Color( 186, 128, 92, 255));
		menuBar.add(c);
		menuBar.add(surr);
		menuBar.add(p);
		setJMenuBar(menuBar);
	}
	
	/**
	 * quando ho finito di fare una mossa questa funzione viene richiamata
	 * cancello i suggerimenti grafici
	 * 
	 * controllo attraverso win se le pedine nere sono finite
	 * se si: 
	 * lancio una finestra di vittoria
	 * altrimenti:
	 * aggiorno il campo graficamente
	 * faccio fare la mossa al computer dalla classe engine 
	 * e ricontrollo se il computer ha vinto e se si lancio un messaggio di sconfitta all'utente
	 * aggiorno il campo graficamente
	 */
	public void end(){
		removeSugg();
		
		if(g.win()==1){
			new Win(g.win(),this).setVisible(true);
		}else{
		update();
				
		computer.move();
		
		if(g.win()!=0)
			new Win(g.win(),this).setVisible(true);
		
		update();
		}
	}
		
	/**
	 * questa funziona si occupa di cancellare il contenuto della finestra
	 * ridisegnarne il contenuto aggiornato dalla matrice modello di gioco e del menubar
	 */
	public void update(){
		this.getContentPane().removeAll();
		createField();
		//menuBar.getComponent(p).setText("Computer "+g.getBlack());
		setBar(this);
		invalidate();
		validate();
	}
	
	/**
	 * questa funzione controlla in tutto il campo se ci sono suggerimenti grafici 
	 * se ci sono li trasforma in caselle vuote nere
	 */
	public void removeSugg(){
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(g.getNumberAt(i,j)==gameModel.sugg)
					g.setNumberAt(i,j,gameModel.empty);
			}
	}
	
	/**
	 * questa funzione si occupa di ridisegnare tutti i bottoni della dama
	 * creando una griglia 8x8 e aggiungendo tutte le celle 
	 */
	private void createField(){
		setLayout(new GridLayout(8,8));
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				add(new Cell(g,i,j,this));
			}
	}
	
	/**
	 * questa funzione crea un nuovo gioco allocando un nuovo gameModel
	 * e una nuova intellgenza artificiale, poi lancio un messaggio di nuoco gioco iniziato
	 * e aggiorno graficamente il campo
	 */
	public void newgame(){
		g=new gameModel();
		computer=new Engine(g);
		update();
	}
	
}
