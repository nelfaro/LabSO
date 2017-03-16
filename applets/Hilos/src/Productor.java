class Productor extends Thread {
  private Almacen almacen;
  private int numero;

  public Productor(Almacen a, int numero) {
    almacen = a;
    this.numero = numero;
  }

  public void run() {
      if(almacen.cerrado) System.out.print("Cerro");
    while(!almacen.cerrado){
      if((System.currentTimeMillis() - almacen.hora) > almacen.TIEMPOAPERTURA){
        almacen.cerrado=true;
        String c;
        c = "Fin de la ejecución.";
        almacen.salida(c);
      }	else{
      almacen.reponer(numero);
      try {
        sleep((int)(Math.random() * 100));
      } 
      catch (InterruptedException e) {
      }
      }
    }
  }
}
	
