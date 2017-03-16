/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hp
 */

import java.util.*;
public class TerminarProceso extends TimerTask{
    private Proceso p;
    private long tiempo;
    
    TerminarProceso() throws Exception{
        throw new Exception("No se puede utilizar el Constructor predeterminado...");
    }
    
    TerminarProceso(Proceso p, long tiempo){
        this.p = p;
        this.tiempo = (p.esperaInicial)+(long)(Math.random() * tiempo  * 0.2);
        System.out.println("\nEl proceso " + p + " muere en " + this.tiempo + ".");
    }
    
    public void run(){
       System.out.println("Matando al proceso " + p);
       p.matar();
        
    }
    
    public long tiempo(){
        return this.tiempo;
    }
}
