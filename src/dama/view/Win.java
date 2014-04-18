package dama.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dama.model.gameModel;
/**
 * questa classe lancia il messaggio di vittoria 
 */
public class Win extends JFrame {
	/**
	 * g passata come parametro privato 
	 */
	private gameModel g;
	/**
	 * f atopassato come parametro priv
	 */
	private Field f;
	/**
	 * a seconda del valore di w se è 1 stampa a video "WIN" se invece è -1 satma "LOSE"
	 * @param w parammetro responsabile dell'esito della partita
	 * @param f finestra del messaggio
	 */
	public Win(int w, Field f){
		this.f=f;
		
		setSize(400,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
			
		if(w==1){
			setTitle("WIN");
			JLabel title=new JLabel("You're the winner!!");
			title.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(title);
		}
		if(w==-1){
			setTitle("LOSE");
			JLabel title2=new JLabel("You're the loser!!");
			title2.setHorizontalAlignment(SwingConstants.CENTER);
			add(title2,BorderLayout.CENTER);
		}
			
		JPanel choice=new JPanel();
		choice.add(exit(),BorderLayout.EAST);
		choice.add(rep(),BorderLayout.WEST);
		add(choice,BorderLayout.SOUTH);
	
		
	}
/**
 * rep: crea una nuova partita 
 * @return
 */
	private JButton rep() {
		JButton rep=new JButton("Replay");
		rep.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				f.newgame();
				dispose();
			}
		});

		return rep;
	}
/**
 * exit: esce dal gioco
 * @return
 */
	private JButton exit() {
		JButton exit=new JButton("Exit");
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		return exit;
}

}

