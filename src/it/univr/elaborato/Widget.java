package it.univr.elaborato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Widget extends JFrame {
	private JLabel[] titolo = new JLabel[3];
	/**
	 *  Messo a true per indicare che è stata fatta una scelta
	 */
	public static boolean choice = false;
	/**
	 * Messo a true se la scelta e una spaceship
	 */
	public static boolean spaceship=false;
	/**
	 * Messo a true se la scelta è un oscillatore
	 */
	public static boolean oscillators=false;
	/**
	 * Messo a true se la scelta è un diehard
	 */
	public static boolean diehard = false;
	/**
	 * Messo a true quando viene sceltà la cella su cui iniziare costruire il disegno scelto
	 */
	public static boolean WIDGETSTATE = false;
	private  static final ImageIcon imag0 = new ImageIcon("Imagini/Game_of_life_animated_glider.gif");
	private  static final ImageIcon imag1 = new ImageIcon("Imagini/Game_of_life_blinker.gif");
	private  static final ImageIcon imag2 = new ImageIcon("Imagini/162px-Game_of_life_diehard.svg.png");
	/**
	 * Costruisce il Widget di scelta dei disegni da inserire sul Game Of Live
	 */
	public Widget () {

	super("Widget");
	setBounds(600, 0, 450, 116);
	titolo[0]=new JLabel("Spaceships");
	titolo[1]=new JLabel("Oscillators");
	titolo[2]=new JLabel("diehard");
	titolo[0].setBounds(0, 0, 130, 14);
	titolo[1].setBounds(131,0,130,14);
	titolo[2].setBounds(261, 0, 130, 14);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.getContentPane().setLayout(null);
	this.getContentPane().add(titolo[0]);
	this.getContentPane().add(titolo[1]);
	this.getContentPane().add(titolo[2]);
	this.getContentPane().add(new ButtonSpaceships());
	this.getContentPane().add(new ButtonOscillators());
	this.getContentPane().add(new ButtonDiehard());
	this.setVisible(true);
	}
	
	
	private class ButtonDiehard extends JButton{
		public ButtonDiehard(){
			super(imag2);
			this.setBounds(262, 15, 190, 102);
			this.addActionListener(new ActionListener() {

		        @Override
		        public void actionPerformed(ActionEvent e) {
		        		choice=true;
		        		diehard=true;
		        		Widget.this.setVisible(false);
		        }
		    	        	                   
		});
	}
	}
	
	
	private class ButtonOscillators extends JButton{
		public ButtonOscillators(){
			super(imag1);
			this.setBounds(131, 15, 130, 102);
			this.addActionListener(new ActionListener() {

		        @Override
		        public void actionPerformed(ActionEvent e) {		        		
		        		choice=true;
		        		oscillators=true;
		        		Widget.this.setVisible(false);
		        }
		    	        	                   
		});
	}
	}
	
	private class ButtonSpaceships extends JButton{
		
		public ButtonSpaceships(){
		super(imag0);
		this.setBounds(0, 15, 130, 102);
		this.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	           		choice=true;
	        		spaceship=true;
	        		Widget.this.setVisible(false);
	              	
	        }
	    	        	                   
	});
		
		
		}
		
	}
}



