/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rodriguez.Nelson.Fabian.Belady;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JOptionPane;




public class Principal{
    int fallos[] ;
    int marcos [];
    int requerimientos [];
    private int nm;
    String msg;
     
    Pantalla p;
    JPanel dib;
    
    Principal(Pantalla p, JPanel dib){
        fallos = new int[3];
        for(int i = 1; i < fallos.length; i++){
            fallos[i] = 0;
        }
        
        this.p = p;
        this.dib = dib;
    }
     
     public void setMarcos(int n){
        
         this.nm = n;
         
         p.println("El nro de marcos es: " + this.nm);
     }
     
     public void setRequerimientos(int n){
         this.requerimientos = new int[n];
         
         for(int i = 0; i < n; i++){
             this.requerimientos[i] = (int) (Math.random() * 50);
                      
         }         
     }
     
     public void simular(boolean borrarPantalla){
          String prefijo;

         if(borrarPantalla){
             p.cls();
         }
         
         p.println("Vector de requerimientos de páginas para la simulacion:");
         for(int i = 0; i < this.requerimientos.length; i++){
             p.print(String.valueOf(this.requerimientos[i] + "  "));             
         }
         p.println(""); 
         
         for(int k = 0; k < 3; ++k){ // Ciclos de simulacion
             if(k==0){
                 this.marcos = new int[(int)(nm * .8)];
                 prefijo = "Primer Test: ";                 
             }else if (k == 1){
                 this.marcos = new int[this.nm];
                 prefijo = "Segundo Test: ";
             }else{
                    this.marcos =new int[(int)(this.nm * 1.2)];
                    prefijo = "Tercer Test: ";
             }
             
             for(int i = 0; i < this.requerimientos.length; i++){// Ciclo procesa requerimientos
                 boolean encontro = false;
                 
                 for(int j = 0; j < this.marcos.length; j++){// Busco la pagina requerida en los marcos
                     
                     if(this.marcos[j] == this.requerimientos[i]){
                         encontro = true;                        
                         j = this.marcos.length;
                     }
                 }

                 if(!encontro){// Si la pagina no estaba cargada en los marcos
                     this.fallos[k]++; //Cuento el fallo
                     this.insertar(this.requerimientos[i]);
                     p.println("Fallo de página; se debe cargar a memoria real la página: " + this.requerimientos[i]);
                 }else{
                     p.println("Página encontrada en memoria real:" + this.requerimientos[i]);
                 }
             }
          p.println(prefijo + this.fallos[k] + " fallos de pagina.")  ;
          p.println("");
          
          if(this.fallos [0]>= this.fallos [1]&& this.fallos [1]>= this.fallos [2]){   
               msg = ("No se produjo Anomalía en la simulacíon");
            }else{
               msg = ("Se produjo Anomalía Belady en la simulacíon");
            }
          
          
          
              
          p.datos(this.fallos[0],this.fallos[1],this.fallos[2],msg);
          }
         
         this.dibujar(); 
         for(int ik = 0; ik < 3 ; ik++) {this.fallos[ik]=0;}
         
         }

    private void dibujar() {
        int x0,xN,y0,yN;
        double xmin,xmax,ymin,ymax;
        int apAncho,apAlto;
        int[] posX = new int[3];
        for(int i = 0; i < posX.length; i++){
            posX[i] = (i + 1) * 50;
        }
        
        Graphics g = dib.getGraphics();
        
        Dimension d = dib.getSize();
        apAncho = d.width;
	apAlto = d.height;
        
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, apAncho, apAlto);
        
        g.setColor(Color.black);
        for (int i = 0; i < apAncho  - 1; i = i + 5){
            g.drawLine(i, 0, i, apAlto - 1);
        }
        
        for (int i = 0; i < apAlto  - 1; i = i + 5){
            g.drawLine(0, i, apAncho - 1, i);
        }
        
	    double x = 0; 
            double y = 0;
	    x0 = y0 = 0;
	    xN = apAncho-1;
	    yN = apAlto-1;
	    
            xmin = 0.0;
	    xmax = 50.0;
	    ymin = 0.0;
	    ymax = 50.0;        
        
            int yp;
            int xp;
                    
        for(int i = 0; i < posX.length; i++){            
            g.setColor(Color.red);
            xp = posX[i];
           System.out.println("x: " + x +" xp: " + xp);
            
            y = (double)this.fallos[i];
            // Escalamos la coordenada y dentro de los limites de la ventana
            yp = (int)( (y-ymin) * (apAlto-1) / (ymax-ymin) );
            // Reconvertinos el valor cartesiano a punto de pantalla
            yp = apAlto - yp;
                        
            g.fill3DRect(xp - 5 , yp, 10, apAlto - 1 , true);            
            g.setColor(Color.blue);
            g.fillOval(xp, yp, 2, 2);
        }        
	    	          
	   
    }

    private void insertar(int IDPagina) { 
        
        
        for(int i = this.marcos.length - 2; i >= 0 ; i--){   //Corre las páginas un marco a la derecha
           
            this.marcos[i + 1] = this.marcos[i];             // segun FIFO
        }
        
        this.marcos[0] = IDPagina;                              //Inserta la pagina en el primer marco
    }

  
}

