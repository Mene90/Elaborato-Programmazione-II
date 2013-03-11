package it.univr.elaborato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Cella extends JButton {
	private boolean stato;
	private boolean kill=false;
	private boolean wState = false;
	/**
	 *  La dimensione di una cella, in pixel.
	 */
	public final static int DIMENSIONE = 15;
	
	/**
	 *  creo una cella del GameOfLife nella stato passato come parametro
	 *  @param stato
	 */
	
	public Cella(boolean stato){
		super();
		this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!Widget.choice)
            		kill=true;
            	else{
            		Widget.WIDGETSTATE=true;
            		wState=true;        
            		Widget.choice=false;
            		}
            }            	                   
    });
		this.stato=stato;
		
	}
	/**
	 * cambia lo stato della cella nello stato passato come parametro
	 * @param stato
	 */
	public void cambiaStato(boolean stato){
		this.stato=stato;
	}
	
	/**
	 * ritorna lo stato della cella se la cella e stat ucisa dal utente cioè se kill e true allora 
	 * ritorna sempre false
	 * @return kill? false : this.stato
	 */
	
	public boolean getStato(){
		return kill? false : this.stato;
	}
	/**
	 * ritorna wState che sarà true se e solo se questa è la cella dalla quale bisogna partire
	 * per disegnare un oggetto selezionato nel widget
	 * @return wState
	 */
	public boolean getWState(){
		return wState;
	}
	/**
	 * rimette a false la variabile wState
	 */
	public void restartwWState(){
		wState=false;
	}
	
}