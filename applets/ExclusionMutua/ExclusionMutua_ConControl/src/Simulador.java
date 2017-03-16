/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
import java.util.*;
import java.applet.*;
import javax.swing.*;



public class Simulador extends Applet {
    public static ArrayList colaDeProcesos;
    public static Region regiones[];
    public static Proceso procesos[];
    public static int tduracion;
    
    private JTextArea salida;
    private java.util.Timer timer;

   public void iniciarSimulacion(int cantPro, int cantReg, int tiempo, JTextArea salida){
       regiones = new Region[cantReg];
       colaDeProcesos = new ArrayList(cantPro);
       procesos = new Proceso[cantPro];
       this.salida = salida;
       this.timer = new java.util.Timer();
       Simulador.tduracion = tiempo * 1000;

       for (int i = 0; i < regiones.length; i++) {//se crean las regiones
           regiones[i] = new Region(i);
           mostrar(regiones[i].toString());

       }
       //Genero un array con los id de los procesos que han de morir antes de terminar.
       Random r = new Random();
       int[] seMueren = new int[(int) (procesos.length * 0.2)];
       for (int i = 0; i < seMueren.length; i++) {
           seMueren[i] = r.nextInt(cantPro);
       }

       int coor = (int) (Math.random() * procesos.length);//calcula el id del coordinador
       procesos[coor] = new Proceso(coor, null, tiempo, false);

       boolean morir;
       for (int i = 0; i < procesos.length; i++) {//se crean las regiones
           morir = false;
           for (int j = 0; j < seMueren.length; j++) {
               //System.out.println(j);
               if (seMueren[j] == i) {
                   morir = true;
               }
           }

           if (i != coor) {
               procesos[i] = new Proceso(i, procesos[coor], tiempo, morir);
               if (morir) {
                   agregarTerminarProceso(new TerminarProceso(procesos[i], Simulador.tduracion));
               }

           }
           mostrar(procesos[i].toString());
       }
       
       for (Proceso p : procesos) {
           p.iniciar();
       }

       try {
           Thread.sleep(Simulador.tduracion);
       } catch (InterruptedException ie) {
           System.out.println(" Termina Simulador " + ie.getMessage());
       }
       for (int i = 0; i < procesos.length; i++) {
           procesos[i].terminar();
       }

 }


public void mostrar(String s){
       // System.out.println(s);
    this.salida.append(s+" \n");

}
public void agregarTerminarProceso(TerminarProceso t){
    this.timer.schedule(t, t.tiempo());
}

}
