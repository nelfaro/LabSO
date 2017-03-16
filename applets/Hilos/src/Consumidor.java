class Consumidor extends Thread {
  private Almacen almacen;
  private int numero;

  public Consumidor(Almacen a, int numero) {
    almacen = a;
    this.numero = numero;
  }

  public void run() {
    int value;
    while(!almacen.cerrado){
      value=(int)(Math.random()*almacen.MAXCOMPRA);
      almacen.comprar(numero,value);
    }
  }
}
	