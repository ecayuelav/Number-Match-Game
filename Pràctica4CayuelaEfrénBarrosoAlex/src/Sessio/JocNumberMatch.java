package Sessio;
import javax.swing.*;
import java.util.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.*;
public class JocNumberMatch extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton[][] Taulell;
	private JButton[][] TaulellCopia;
	private JButton afegir, reiniciar, novaPartida;
	private JPanel panel, panelfuncions;
	private int botonesPulsados=0;
	private JButton primerBotonPulsado;
	private int primeroi, primeroj;
	private int intents =2;
	private int numComponents;
	
	public JocNumberMatch (String tl) {
    	super(tl);
    	initComponents();
    }
	
	private void initComponents() {
		panel = new JPanel();
        panelfuncions = new JPanel();
		Taulell = new JButton[15][9];
		TaulellCopia = new JButton[15][9];
		panel.setLayout(new GridLayout(15,9));
		
		
	Random rnd = new Random();
	int n, cont=0;
	for (int i = 0; i < Taulell.length; i++) {
        for (int j = 0; j < Taulell[i].length; j++) {
            if (cont < 32) {
                n = rnd.nextInt(9) + 1;
                Taulell[i][j] = new JButton(n + "");
                cont++;
                panel.add(Taulell[i][j]);
                Taulell[i][j].addActionListener(this);
                
            } else {
                Taulell[i][j] = new JButton("");
                panel.add(new Label());
                
            }
        }
        
    }
		numComponents=32;
		copia();
		
        
        
        afegir = new JButton("Afegir");
        reiniciar = new JButton("Reiniciar");
        novaPartida = new JButton("Nova Partida");
        
        panelfuncions.setLayout(new GridLayout(1,3));
        panelfuncions.add(afegir);
        panelfuncions.add(reiniciar);
        panelfuncions.add(novaPartida);
        

        
        
        
        this.setLayout(new BorderLayout());
        this.getContentPane().add(panelfuncions, BorderLayout.SOUTH);
        this.getContentPane().add(panel, BorderLayout.NORTH);
        
        afegir.addActionListener(this);
        reiniciar.addActionListener(this);
        novaPartida.addActionListener(this);
        
        
        
        
    }
	
	
	
	public void actionPerformed(ActionEvent act) {
		if (act.getSource() == reiniciar) {
			Reiniciar();
			for (int fil = 0; fil < Taulell.length; fil++) {
	            for (int col = 0; col < Taulell[fil].length; col++) {
	                Taulell[fil][col].setText(TaulellCopia[fil][col].getText());
	            }
	        }
			actualitzarPanel(Taulell);
			intents=2;
			afegir.setEnabled(true);
			numComponents=32;
		}
		if (act.getSource() == afegir) {
			afegir();
			intents--;
			if(intents==0) {
				afegir.setEnabled(false);
			}
		}
		if (act.getSource() == novaPartida) {
			Random rnd = new Random();
			Reiniciar();
			for (int fil = 0; fil < Taulell.length; fil++) {
	            for (int col = 0; col < Taulell[fil].length; col++) {
	                Taulell[fil][col].setText(TaulellCopia[fil][col].getText());
	                if(!Taulell[fil][col].getText().isEmpty()) {
	            		Taulell[fil][col].setText((rnd.nextInt(9)+1)+"");	
	            	}
	            }
	        }
			
			copia();
			actualitzarPanel(Taulell);
			numComponents=32;
			intents=2;
			afegir.setEnabled(true);
		}
		
        if (botonesPulsados < 2) {
            for (int i = 0; i < Taulell.length; i++) {
                for (int j = 0; j < Taulell[i].length; j++) {
                	
        			
                    if (act.getSource() == Taulell[i][j]) {
                    	

                        JButton botonPulsado = Taulell[i][j];
                        // Verifica si ya se ha pulsado este botón
                        if (botonPulsado == primerBotonPulsado) {
                        	botonPulsado.setBackground(null);
                            return; // Salir si es el mismo botón
                        }
                        
                        botonesPulsados++;
                        
                        if (botonesPulsados == 1) {
                            primerBotonPulsado = botonPulsado;
                            primeroi=i;
                            primeroj=j;
                            primerBotonPulsado.setBackground(Color.YELLOW);
                            
                        } else { 
                            
                        	System.out.println(Integer.parseInt(primerBotonPulsado.getText()));
                        	System.out.println(Integer.parseInt(botonPulsado.getText()));
                            
                            if(primerBotonPulsado != null) {
                            	if(Integer.parseInt(primerBotonPulsado.getText())==Integer.parseInt(botonPulsado.getText())||Integer.parseInt(primerBotonPulsado.getText())+Integer.parseInt(botonPulsado.getText())==10) {
                            		if(parellaVertical(Taulell, primeroi, primeroj, i, j)||parellaDiagonal(Taulell, primeroi, primeroj, i, j)||parellaHoritzontal(Taulell, primeroi, primeroj, i, j)) {
                                		primerBotonPulsado.setEnabled(false);
                                    	botonPulsado.setEnabled(false);
                                    	numComponents-=2;
                                    	if(filaBuida(Taulell, primeroi)) {
                                    		
                                    		eliminarFila(Taulell,primeroi);
                                    		
                                    		
                                    	}
                                    	if(filaBuida(Taulell, i)) {
                                    		
                                    		eliminarFila(Taulell,i);
                                    		
                                    	}
                                	}
                            	}
                            }
                            
                            botonesPulsados = 0;
                            primerBotonPulsado.setBackground(null);
                            primerBotonPulsado = null;
                        }

                        if (numComponents==0) {
                            JOptionPane.showMessageDialog(this, "Felicitats!!!");
                            afegir.setEnabled(false);
                        }

                    }
                }
            }
        }
 }
	
	private void afegir() {
	    int quants = 0;
	    JButton[] aux = new JButton[64];
	    
	    for (int i = 0; i < Taulell.length; i++) {
	        for (int j = 0; j < Taulell[i].length; j++) {
	            if (!Taulell[i][j].getText().isEmpty() && Taulell[i][j].isEnabled()) {
	                aux[quants] = new JButton(Taulell[i][j].getText()); 
	                quants++;
	            }
	        }
	    }


	    int cont = 0;
	    for (int i = 0; i < Taulell.length && cont < quants; i++) {
	        for (int j = 0; j < Taulell[i].length && cont < quants; j++) {
	            if (Taulell[i][j].getText().isEmpty()) {
	                Taulell[i][j] = aux[cont];
	                Taulell[i][j].addActionListener(this);
	                cont++;
	            }
	        }
	    }
	    numComponents+=quants;
	    actualitzarPanel(Taulell);
	}

	private void actualitzarPanel(JButton[][] taulell) {
		panel.removeAll();
	    
	    for (int i = 0; i < taulell.length; i++) {
	        for (int j = 0; j < taulell[i].length; j++) {
	        	if (!taulell[i][j].getText().isEmpty()) {
	                panel.add(taulell[i][j]); 
	                
	            }else {
	            	panel.add(new Label());
	            }
	        	 
	        }
	    }
	    
	   
	    panel.revalidate();
	    panel.repaint();
	}
	private void actualitzarPanel(JButton[][] taulell, int filaEliminar) {
		boolean [][]copia= new boolean[15][9];
		for(int i=0;i<taulell.length;i++) {
			for(int j=0;j< taulell[i].length;j++) {
				copia [i][j]= taulell[i][j].isEnabled();
			}
		}
		panel.removeAll();
		
	    for (int i = 0; i < taulell.length; i++) {
	        for (int j = 0; j < taulell[i].length; j++) {
	        	if (!taulell[i][j].getText().isEmpty()) {
	                panel.add(taulell[i][j]); 
	                if(i>=filaEliminar) {
	                	taulell[i][j].setEnabled(copia[i+1][j]);
	                }else {
	                	taulell[i][j].setEnabled(copia[i][j]);
	                }
	                
	            }else {
	            	panel.add(new Label());
	            }
	        	 
	        }
	    }
	    
	    
	    panel.revalidate();
	    panel.repaint();
	}

	
	private void eliminarFila(JButton[][] taulell, int fila) {
	    for (int i = fila; i < taulell.length - 1; i++) {
	        for (int j = 0; j < taulell[i].length; j++) {
	            // Mueve el botón de la fila de arriba hacia abajo
	            taulell[i][j].setText(taulell[i + 1][j].getText());
	        }
	    }
	    for (int j = 0; j < taulell[0].length; j++) {
	        // Borra el texto del botón
	        taulell[taulell.length - 1][j].setText("");
	    }
	    actualitzarPanel(taulell,fila);
	}

	private static boolean parellaVertical(JButton[][] taulell, int fil1, int col1, int fil2, int col2) {
		if(fil1>fil2) {
    		int auxfil = fil2;
    		int auxcol = col2;
    		fil2 = fil1;
    		col2 = col1;
    		fil1 = auxfil;
    		col1 = auxcol;
    		
    	}
	    if (col1 == col2) {
	        for (int i = fil1 + 1; i < fil2; i++) {
	            if (taulell[i][col1].isEnabled()) {
	                return false;
	            }
	        }
	    } else {
	        return false;
	    }

	    return true;
	}
	
	private static boolean parellaDiagonal(JButton[][] taulell, int fil1, int col1, int fil2, int col2) {
		
	    
	    if (Math.abs(fil1 - fil2) != Math.abs(col1 - col2)) {
	        return false;
	    }

	    
	    int filIncrement;
	    if (fil2 > fil1) {
	        filIncrement = 1;
	    } else {
	        filIncrement = -1;
	    }

	    int colIncrement;
	    if (col2 > col1) {
	        colIncrement = 1;
	    } else {
	        colIncrement = -1;
	    }

	    
	    int i = fil1 + filIncrement;
	    int j = col1 + colIncrement;

	    
	    while (i != fil2 || j != col2) {
	        
	        if (taulell[i][j].isEnabled()) {
	            return false;
	        }
	        
	        i += filIncrement;
	        j += colIncrement;
	    }

	    
	    return true;
	}

	
	private static boolean parellaHoritzontal(JButton[][] taulell, int fila1, int columna1, int fila2, int columna2) {
		
	    if (fila1 == fila2) {
	    	if(columna1>columna2) {
	    		int auxfil = fila2;
	    		int auxcol = columna2;
	    		fila2 = fila1;
	    		columna2 = columna1;
	    		fila1 = auxfil;
	    		columna1 = auxcol;
	    		
	    	}
	        for (int j = columna1 + 1; j < columna2; j++) {
	            if (taulell[fila1][j].isEnabled()) {
	                return false;
	            }
	        }
	    } else if (fila1 + 1 == fila2|| fila2+1 == fila1) {
	    	if(fila1>fila2) {
	    		int auxfil = fila2;
	    		int auxcol = columna2;
	    		fila2 = fila1;
	    		columna2 = columna1;
	    		fila1 = auxfil;
	    		columna1 = auxcol;
	    		
	    	}
	        int ultimNoEmparellat1 = quinUltimNoEmparellat(taulell, fila1);
	        int primerNoEmparellat2 =quinPrimerNoEmparellat(taulell, fila2);
	        if (ultimNoEmparellat1 != columna1 || primerNoEmparellat2 != columna2) {
	            return false;
	        }
	    } else {
	        return false;
	    }

	    return true;
	}
	
	//funció per trobar el ultim nombre no emparellat d'una fila, és a dir, seria 4 --> 7594XXXX
	private static int quinUltimNoEmparellat(JButton[][] taulell, int fila) {
	    int ultimNoEmparellat = taulell[fila].length - 1;
	    while (ultimNoEmparellat >= 0 && !taulell[fila][ultimNoEmparellat].isEnabled()) {
	        ultimNoEmparellat--;
	    }
	    return ultimNoEmparellat;
	}
	//funció per trobar el primer nombre no emparellat d'una fila, és a dir, seria 4 --> XXXXX4566
	private static int quinPrimerNoEmparellat(JButton[][] taulell, int fila) {
	    int primerNoEmparellat = 0;
	    while (primerNoEmparellat < taulell[fila].length && !taulell[fila][primerNoEmparellat].isEnabled()) {
	        primerNoEmparellat++;
	    }
	    return primerNoEmparellat;
	}
	
	private boolean filaBuida(JButton[][] taulell, int fila) {
	    for (int j = 0; j < taulell[fila].length; j++) {
	        if (!taulell[fila][j].getText().isEmpty() && taulell[fila][j].isEnabled()) {
	            return false; // retorna si i ha un botó habilitat
	        }
	    }
	    return true; // si no hi ha botons habilitats arriba aquí
	}
	
	
	private void copia() {
        for (int i = 0; i < Taulell.length; i++) {
            for (int j = 0; j < Taulell[i].length; j++) {
                TaulellCopia[i][j] = new JButton(Taulell[i][j].getText());
                TaulellCopia[i][j].setText(Taulell[i][j].getText());
            }
        }
        
    }
	

    public void Reiniciar() {

        botonesPulsados = 0;
        primerBotonPulsado = null;


        for (int i = 0; i < Taulell.length; i++) {
            for (int j = 0; j < Taulell[i].length; j++) {

                Taulell[i][j].setEnabled(true);
            }
        }
        
    }
	
	
	public static void main (String [] args) {
        EventQueue.invokeLater(
                new Runnable () {
                    public void run () {
                        JFrame jf = new JocNumberMatch("JOC DEL NUMBER MATCH");
                        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        jf.pack();
                        jf.setVisible(true);
                    }
                }
        
        );
    }
}
