package dama.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * questa classe lancia un messaggio di chiusura della finestra  
 */
public class Closing extends JFrame {
/**
 * titolo della finestra
 * dimensione della finestra
 * setto un layout 
 * scrivo il messaggio (JLabel) da lanciare, lo centro il testo alla finestra
 * creo un JPanel 
 * ci aggiungo i due bottoni yes e no, lo metto in basso alla finestra 
 */
	public Closing(){
		super("EXIT");
		setSize(400,100);
		setLayout(new BorderLayout());
		
		JLabel title=new JLabel("Are you sure want to exit?");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title,BorderLayout.CENTER);
		
		JPanel yn=new JPanel();
		yn.add(yes(),BorderLayout.EAST);
		yn.add(no(),BorderLayout.WEST);
		add(yn,BorderLayout.SOUTH);
		
	}
/**
 * no: chiude la finestra e non fa niente
 */
	private JButton no() {
		JButton n=new JButton("NO");
		n.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		return n;
	}
/**
 * yes: esce dal programma
 */
	private JButton yes() {
		JButton y=new JButton("YES");
		y.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		return y;
	}
}
