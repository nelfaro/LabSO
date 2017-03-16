/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class Proceso implements Runnable {
   private int _idProceso;
   private Proceso Coordinador;
   int esperaInicial;// es el tiempo que va a esperar el proceso antes de inicar la ejecucion
   boolean enEjecucion;// para controlar la ejecucion del hilo
   Thread t;// el hilo
   //static Region regiones[];// arreglo de regiones
   private boolean muriendo;
   private boolean seMuere;


public void solicitarRegion(Proceso p, int idReg) {
    if (Simulador.regiones[idReg].enUso()) {
        p.suspender();
        encolar(p, idReg, 1);
    } else {
        SimuladorApplet.sim.mostrar("Se concede la región " + idReg + " al Proceso " + p);
        Simulador.regiones[idReg].ocupar(p.idProceso());
    }
    //si esta libre la region la marcamos como ocupada
    //si no ponemos a dormir el proceso y la encolamos
}
   
   void encolar(Proceso pro, int idReg, int modo){
       pro.suspender();
       if (modo ==1){
       
       int[] cola = new int[2];
        cola[0]=pro.idProceso();
        cola[1]=idReg;
       SimuladorApplet.sim.mostrar("Se encola proceso: "+pro);
       Simulador.colaDeProcesos.add(cola);
      
        }else if(modo ==2){
           //se encola en las colas por regiones

            }else{

           //igual que modo 2 pero tien en cuenta las prioridades
       }
   }
public void desencolar(int idReg, int modo){

 if (modo ==1){
       int aux[]=new int[2];
//      Iterator it = Simulador.colaDeProcesos.iterator();
//      while(it.hasNext()){
//        it.}
       boolean encontrado = false;
       for(int i =0; i<Simulador.colaDeProcesos.size()-1; i++){

           aux=(int[])Simulador.colaDeProcesos.get(i);

           if (idReg==aux[1]){
                encontrado = true;
                SimuladorApplet.sim.mostrar("Se desencola el proceso " +(char) ('A' +aux[0])+" y se concede la región "+idReg);
                Simulador.colaDeProcesos.remove(i);
                Simulador.procesos[aux[0]].reanudar();
                Simulador.regiones[idReg].ocupar(aux[0]);
            }
           if (encontrado){ break;}
       }
      
        }else if(modo ==2){
           //se encola en las colas por regiones

            }else{

           //igual que modo 2 pero tien en cuenta las prioridades
       }

}
 public void liberarRegion(int idReg){
       //marca como libre la region y desencola un proceso de esa region
    Simulador.regiones[idReg].liberar();
    this.desencolar(idReg, 1);
    System.out.print("Llamando a controlarProcesos desde liberarRegion.");
    this.controlarProcesos();
   }


public void run() {
    if (!this.esCoord()) {
        try {
            Thread.sleep(this.esperaInicial);
        } catch (InterruptedException ie) {
            if (this.muriendo) {
                
                return;
            }
        }
    }
    SimuladorApplet.sim.mostrar("Iniciando Proceso " + this /*+ " despues de la espera inicial."*/);
    while (t != null) {
        if (this.enEjecucion) {
            /*
             * si es coordinador hacer lo que hace el coordinador
             * si no es coordinador
             * Pedir regiones y demás yerbas...
             *
             */
            // this.sleep(this.esperaInicial);
            if (this.esCoord()) {
                
                this.controlarProcesos();
                Thread.yield();

                //espera si es coordinador
            } else {
                
                int reg = this.buscoRegion();
                // Si no está en ejecución, es porque está suspendido
                // entonces sale del bucle (cualquier duda http://download.oracle.com/javase/tutorial/index.html)
                if (!this.enEjecucion) {
                    continue;
                };

                // Usará la región por un un tiempo de hasta el 30% de duración de la simulación.
                int tiempoDeUso = (int) (Math.random() * Simulador.tduracion * 0.3);
                try {
                    System.out.println(this + " usara la región " + reg + " durante " + tiempoDeUso);
                    Thread.sleep(tiempoDeUso);
                } catch (InterruptedException ie) {
                    if (this.muriendo) {
                        return;
                    }
                }
                if (this.muriendo) {
                    this.terminar();
                    SimuladorApplet.sim.mostrar("El proceso " + this + " está muriendo...");
                    return;
                }
                SimuladorApplet.sim.mostrar(" El proceso " + this + " libera la región " + reg);
                this.Coordinador.liberarRegion(reg);

                //calcula la region y cuanto tiempo de uso aleatoriamente
                //pide la region al coordinador
                //duerme por el tiempo de uso
                //avisa al coordinador que libera la region
            }
            // Main.sim.mostrar(this.toString());
        }

    }
    if (!this.muriendo) {
       
        SimuladorApplet.sim.mostrar(" El Proceso " + this + " ha finalizado.");
    }
    t = null;
}


    public Proceso(int id, Proceso coord, int tie, boolean seMuere){
        this.esperaInicial = (int)(Math.random() * tie * (200));
        this.Coordinador=coord;
        this._idProceso=id;
        this.enEjecucion = true;
        this.seMuere = seMuere;
        t = new Thread(this);
        
        System.out.println("Creado el proceso " + _idProceso);        
    }


    public void iniciar(){   
        System.out.println("Iniciando hilo " + this);
        t.start();
    }
    public void suspender(){
        System.out.println(this + "  Suspendido...");
        this.enEjecucion = false;
    }

    public void reanudar(){
        System.out.println("  Reanudando....");
        this.enEjecucion = true;
    }

    public void terminar(){
        //if  (! this.esCoord()){
        this.enEjecucion = false;
        t = null;
    
    }
    public void idProceso(int idProc){

        this._idProceso=idProc;

    }
    
public boolean esCoord(){

        return this.Coordinador== null;
    }

     public int idProceso(){

       return this._idProceso;

    }
     public String toString(){
         
         String s=new String();
         s = " "+(char) ('A' + this._idProceso);
         if (this.esCoord()){
         s+=" Coordinador ";
         }
         return s;
     }
     
    public void matar(){
        SimuladorApplet.sim.mostrar("El proceso " + this + " termina de manera inesperada.");
        this.muriendo = true;
        t.interrupt();
    }

    private int buscoRegion() {
        int reg = -1;
        for(int i = 0; i < Simulador.regiones.length ; i++){
            if (Simulador.regiones[i].ocupandoRegion(this._idProceso)){
                reg = Simulador.regiones[i].idRegion();
            }
        }
        
        if (reg == -1){
                reg = (int) (Math.random() * (Simulador.regiones.length - 1));
                SimuladorApplet.sim.mostrar(" El proceso " + this + " solicita la región " + reg);
                this.Coordinador.solicitarRegion(this, reg);
        }
        return reg;
                
    }

    private void controlarProcesos() {
        // Recorre las regiones para verificar que
        // los procesos que las ocupan siguen "vivos"
        
        for(Region r : Simulador.regiones){
            for(Proceso p : Simulador.procesos){
                if (p.muriendo && r.ocupandoRegion(p.idProceso())){
                    SimuladorApplet.sim.mostrar("----------------------------------------------");
                    SimuladorApplet.sim.mostrar("El proceso " + p + " ya no se está ejecutando");
                    SimuladorApplet.sim.mostrar("y mantiene bloqueada la región " + r.idRegion() + " .");
                    SimuladorApplet.sim.mostrar("Se fuerza la liberación de dicha región.");
                    this.liberarRegion(r.idRegion());
                    SimuladorApplet.sim.mostrar("----------------------------------------------");
                }
            }
        }
    }   
}

