package it.univr.elaborato;

import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JButton;

public class Mondo extends JFrame{
	
	private final static int XCELL = 40;
	private final static int YCELL = 40;
 	private final Cella[][] celle = new Cella[XCELL][YCELL];
 	private final boolean[][] statoNuovaGenerazione = new boolean [XCELL][YCELL];
	private final static Random random = new Random();
	private Controllore[] controllori;
	private Generatore[] generatori;
	private static JSlider slider = new JSlider(0,500,0);
	private static JButton choice = new JButton("Choice");
	private static JButton start = new JButton("Start");
	private static JButton stop = new JButton("Stop");
	private static JButton next = new JButton("Next");
	private static JButton clear = new JButton("Clear");
	private static Point punto = new Point();
	private static boolean controllo = true;
	private static Integer riga = 0;
	private int numOfThread;
	
	/**
	 * costruisce il mondo dove vivono le celle dell Game of life le quali vengono gentite
	 * da un numero di thread pari al numero passato come parametro
	 * @param numOfThread
	 */
	
	public Mondo(int numOfThread){
		
		super("Game of Life");
		this.numOfThread=numOfThread;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		daiLaVita();
		fissaLeDimensioni();
		disegna();
		
		slider.setBackground(Color.WHITE);
		this.getContentPane().add(slider);
		this.getContentPane().add(choice);
		this.getContentPane().add(start);
		this.getContentPane().add(next);
		this.getContentPane().add(clear);
		
		choice.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	new Widget();
	        }              
		});
		
		start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Start();
				Mondo.this.getContentPane().add(stop);
				Mondo.this.getContentPane().remove(start);							
			}	
		});
		
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				statoSuccessivo(Mondo.this.numOfThread);
			}
		});
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				for (int i=0; i<XCELL; i++)
					for(int j=0;j<YCELL; j++)
							celle[i][j].cambiaStato(false);						
			}	
		});
		
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Stop();
				Mondo.this.getContentPane().add(start);
				Mondo.this.getContentPane().remove(stop);
				
			}
		});
		
		
		this.setVisible(true);		
			
		while(true){	
			sleepFor(500-slider.getValue());
			
			try{
			attesaInizio();
			}catch(InterruptedException e){};
			
			statoSuccessivo(numOfThread);
			
		}			
	}
	/**
	 * Genera la nuova generazione delle celle del Game of life usando un numero di thread pari a quello 
	 * passato come parametro
	 * @param numOfThread
	 */
	
	private void statoSuccessivo(int numOfThread) {
		startControllori(numOfThread);
		waitControllori();
		
		if(Widget.WIDGETSTATE){
			if(Widget.spaceship){
				Spaceship(punto.y,punto.x);
				Widget.WIDGETSTATE=false;
				celle[punto.y][punto.x].restartwWState();
				Widget.spaceship=false;
				}
			if(Widget.oscillators){
				oscillators(punto.y,punto.x);
				Widget.WIDGETSTATE=false;
				celle[punto.y][punto.x].restartwWState();
				Widget.oscillators=false;
			}
			if(Widget.diehard){
				diehard(punto.y,punto.x);
				Widget.WIDGETSTATE=false;
				celle[punto.y][punto.x].restartwWState();
				Widget.diehard=false;
			}
		}
		
		startGeneratori(numOfThread);
		waitGeneratori();	
		
	}

	/**
	 * Mette il programma ("l'intero mondo") in attesa
	 * @throws InterruptedException
	 */
	private synchronized void attesaInizio() throws InterruptedException {
		if(controllo){
			controllo=false;
			wait();
		}	
	}
	/**
	 * Sblocca il programma bloccato sulla wait()
	 */
	private synchronized void Start() {		
		notify();
	}
	/**
	 * Pome la variabile controllo a true, il programma sarà cosi messo in attesa al ciclo successivo
	 */
	private synchronized void Stop(){
		controllo=true;
	}
	/**
	 * Costruisce un diehard partendo dalla riga e colonna passati come parametro
	 * @param riga
	 * @param colonna
	 */
	private void diehard(int riga ,int colonna){
		try{
			statoNuovaGenerazione[riga-1][colonna+6]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}	
		try{	
			statoNuovaGenerazione[riga][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga][colonna+1]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+1][colonna+1]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+1][colonna+5]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}	
		try{	
			statoNuovaGenerazione[riga+1][colonna+6]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+1][colonna+7]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
	}
	/**
	 * Costruisce un oscillatore partendo dalla riga e colonna passati come parametro 
	 * @param riga
	 * @param colonna
	 */
	
	private void oscillators(int riga, int colonna){
		try{
			statoNuovaGenerazione[riga][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{	
			statoNuovaGenerazione[riga+1][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+2][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
	}
	
	/**
	 * Costruisce una spaceship partendo dalla riga e colonna passati come parametro
	 * @param riga
	 * @param colonna
	 */
	private void Spaceship(int riga, int colonna){
		try{
			statoNuovaGenerazione[riga][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+1][colonna+1]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+2][colonna-1]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+2][colonna]=true;
		}catch(java.lang.ArrayIndexOutOfBoundsException e){}
		try{
			statoNuovaGenerazione[riga+2][colonna+1]=true;
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){}
	}
	/**
	 * Inizzializa la matrice bidimensionale celle[][] in cui lo stato di ogni cella è inizializzato 
	 * in modo random
	 */
	private void daiLaVita() {

		for (int i=0; i<XCELL; i++)
			for(int j=0;j<YCELL; j++){
				if(random.nextBoolean())
					celle[i][j] = new Cella(true); 
				else	
					celle[i][j] = new Cella(false);	
			}
	}
	
	/**
	 * fissa le dimensioni del mondo e dei diversi oggetti disegnati sul mondo
	 */
	private void fissaLeDimensioni() {
		int XSIZE = Cella.DIMENSIONE * celle[0].length;
	    int YSIZE = Cella.DIMENSIONE * celle.length;
	    slider.setBounds(0,YSIZE, XSIZE-200, 50);
	    choice.setBounds(slider.getWidth()+100, YSIZE, 100, 25);
	    start.setBounds(slider.getWidth(),YSIZE,100,25);
	    stop.setBounds(slider.getWidth(),YSIZE,100,25);
	    next.setBounds(slider.getWidth(),YSIZE+25,100,25);
	    clear.setBounds(slider.getWidth()+100, YSIZE+25, 100, 25);
	    this.setSize(XSIZE, YSIZE+slider.getHeight());
	   	this.setResizable(false);
	}
	/**
	 * Disegna le celle sull mondo 
	 */
	
	private void disegna() {
		int riga = 0;
		int colonna=0;
		for(Cella[] rigacelle : celle){
				colonna=0;
			for(Cella cella : rigacelle)
				diseganAux(cella,riga,colonna++);
		    riga++;
		}	
		
	}
	
	/**
	 * disegna e colora la cella passata come parametro nella posizione riga e colonna 
	 * anche questi passati come parametro
	 * @param cella
	 * @param riga
	 * @param colonna
	 */
	
	private void diseganAux(Cella cella,int riga, int colonna){
		cella.setBounds(colonna*Cella.DIMENSIONE, riga*Cella.DIMENSIONE, Cella.DIMENSIONE, Cella.DIMENSIONE);
		cella.setBackground(cella.getStato()? Color.YELLOW : Color.GRAY);
		this.getContentPane().add(cella);
	}
	/**
	 * crea un numero di controllori pari a numofthreads
	 * @param numofthreads
	 */
	
	private void startControllori(int numofthreads) {
		controllori = new Controllore[numofthreads];
		for(int i=0 ; i<controllori.length;i++){
			controllori[i] = new Controllore();
			controllori[i].start();
		}
		
	}
	/**
	 * Aspetta che tutti i controllori abbiano finito poi reinizializza la riga a zero
	 */	
	private void waitControllori() {
		for(int i= 0 ; i<controllori.length;i++){
		try{	
		controllori[i].join();
		}
		catch(InterruptedException e){}
		}
		riga=0;
	}
	/**
	 * crea un numero di generatori pari a numofthreads
	 * @param numofthreads
	 */
	private void startGeneratori(int numofthreads) {
		generatori = new Generatore[numofthreads];
		for(int i= 0 ; i<numofthreads;i++){
			generatori[i] = new Generatore();
			generatori[i].start();
		}
	}

	/**
	 * Aspetta che tutti i generatori abbiano finito poi reinizializza la riga a zero
	 */	
	
	private void waitGeneratori() {
		for(int i= 0 ; i<generatori.length;i++){
			try{	
			generatori[i].join();
			}
			catch(InterruptedException e){}
			}
		riga=0;
	}
	/**
	 * Dorme per il numero di millisecondi indicato.
	 * 
	 * @param milliseconds
	 */
	
	private void sleepFor(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {}
	}
	/**
	 * Calcola il nuovo stato delle celle che si trovano sulla riga passata come parametro
	 * @param riga
	 */
	
	private void regolaVita(int riga) {
	
		for(int colonna=0; colonna<YCELL; colonna++){
				if(celle[riga][colonna].getWState())
					punto.setLocation(colonna, riga);
				calcolaNuovaGenerazione(riga,colonna);
		}
								
	}
	/**
	 * cambia lo stato delle celle sulla riga passata come parametro 
	 * in base al valore della matrice statoNuovaGenerazione
	 * @param riga
	 */
	private void cambiaStato (int riga) {
			for(int colonna=0; colonna<YCELL; colonna++){
				celle[riga][colonna].cambiaStato(statoNuovaGenerazione[riga][colonna]);
				celle[riga][colonna].setBackground(celle[riga][colonna].getStato()? Color.YELLOW : Color.GRAY);
			}		
	}
	/**
	 * calcola il nuovo stato della cella nella posizione riga , colonna e lo salva
	 * nella matrice statoNuovaGenerazione
	 * @param riga
	 * @param colonna
	 */
	private void calcolaNuovaGenerazione(int riga, int colonna) {
		int contCelle=0;
		for(int dx=-1;dx<=1;dx++)
			for(int dy =-1;dy<=1;dy++){
				if(!(dx==0 && dy==0) && riga+dx>=0 && colonna+dy>=0 && riga+dx<XCELL && colonna+dy<YCELL && celle[riga+dx][colonna+dy].getStato())
					contCelle++;
			}
							
			if(celle[riga][colonna].getStato()){
				if(contCelle<=1 || contCelle>=4)
					statoNuovaGenerazione[riga][colonna]=false;	
				else
					statoNuovaGenerazione[riga][colonna]=true;
			}		
			else if(contCelle==3)
				statoNuovaGenerazione[riga][colonna]=true;
			else 
				statoNuovaGenerazione[riga][colonna]=false;	
	}

	
	private class Controllore extends Thread{
		int rigaInterna;	
		@Override
		public void run() {
			
			while(riga<XCELL){
				synchronized(riga){
					rigaInterna=riga++;
				}
				if(rigaInterna<XCELL){
				    regolaVita(rigaInterna);
				}
			}
		}						
	}
			
	private class Generatore extends Thread{
		 int rigaInterna;
		@Override
		public void run() {
			 
			while(riga<XCELL){
				
				synchronized(riga){
					rigaInterna=riga++;
				}
				if(rigaInterna<XCELL){
					cambiaStato(rigaInterna);	
				}
			}
		}
}
}


