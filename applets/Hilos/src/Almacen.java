class Almacen {
  public int stock=0;
  private boolean necesitareponer;
  public boolean cerrado=false;
  public int MAXCOMPRA=0;
  public int LIMITEREPONER=0;
  public int CANTIDADREPONER=0;
  public long TIEMPOAPERTURA=0;
  long hora;
  HilosApplet ha;
  
  public Almacen(HilosApplet ha){
   stock = Integer.parseInt(HilosApplet.stock);
   MAXCOMPRA = Integer.parseInt(HilosApplet.maxcompra);
   LIMITEREPONER = Integer.parseInt(HilosApplet.limitereponer);
   CANTIDADREPONER = Integer.parseInt(HilosApplet.cantidadreponer);
   TIEMPOAPERTURA = Integer.parseInt(HilosApplet.tiempoapertura);
   hora=System.currentTimeMillis();
   this.ha = ha;
  }



  public synchronized void comprar(int consumidor, int cantidad) {
    while (necesitareponer == true) {
      try { 
          this.wait();
      } catch (InterruptedException e) { 
      	}
    }
    if(!cerrado){
        
    	if(stock<cantidad){
            cantidad=stock;
      	}
      	stock-=cantidad;        
	String c;
     	c = "Quedan " + stock + " unidades. El Consumidor " + consumidor + " ha leido " +cantidad+" unidades.";
        this.salida(c);
    	if(stock <= LIMITEREPONER){
            necesitareponer = true;
            this.notify();
        }
    }
  }

  public synchronized void reponer(int productor) {
    if (!cerrado){
    	while (necesitareponer == false) {
      		try { 
                    this.wait();
      		} catch (InterruptedException e) {}
    	}
        stock+=CANTIDADREPONER;
	String c;
    	c = "Quedan " + stock + " unidades. El Productor " + productor + " ha grabado " + CANTIDADREPONER + " unidades.";
        this.salida(c);
    necesitareponer = false;
    }
    this.notifyAll();
  }

  public void salida(String texto){
    ha.salida(texto);
  }
}
	