/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class Region {
   private int _idRegion;
   private boolean _enUso;
   private int _idProceso; // El proceso que tenga asignado esta región;
   private String aux="";

    public Region(int idReg){
        this._idRegion=idReg;
        this._enUso=false;
        this._idProceso = -1;
    }

    public void liberar(){
            SimuladorApplet.sim.mostrar("Se libero la región "+this.idRegion());
            this._enUso=false;
            this._idProceso = -1;
    }

     public void ocupar(int idProceso){
            this._idProceso = idProceso;
            this._enUso=true;

        }
    public boolean enUso (){
        return this._enUso;
    }
public void idRegion(int idReg){

        this._idRegion=idReg;
        this._enUso= false;

    }

public int idRegion(){

        return this._idRegion;

    }
   public String toString(){
         String s=new String();
         if (this._enUso==false){
             this.aux=("Libre");
         }else{this.aux=("Ocupado");}
         s = "Id Región: "+this._idRegion+ "    Estado de uso: "+ this.aux;
         return s;
     }
   
 public boolean ocupandoRegion(int idProceso){
     return this._idProceso == idProceso;
 }  

}
