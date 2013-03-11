package it.univr.elaborato;
import javax.swing.JOptionPane;


public class GameOfLife{

	/**
	 *  Main in cui viene richiesto con quante Thread far partire il Game of life
	 *  @param args argomenti da linea di comando (non usati)
	 */
	public static void main(String[] args) {
		Integer numOfThread=0;
		while(numOfThread==0){
		try{
		numOfThread=Integer.parseInt(JOptionPane.showInputDialog("Quante thread?"));
		if(numOfThread==0)
			JOptionPane.showMessageDialog(null, "Valore non valido riprova", "Errore", JOptionPane.ERROR_MESSAGE);
			
		}catch(java.lang.NumberFormatException e){
		numOfThread=0;
		JOptionPane.showMessageDialog(null, "Valore non valido riprova", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		}
		new Mondo(numOfThread);
	}
	
	

}
