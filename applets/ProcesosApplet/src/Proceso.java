
public class Proceso {
    public static final int LISTO = 0;      //Estado listo
    public static final int INTERRUMPIDO_ES = 1;        //Estado interrumpido por E/S
    public static final int INTERRUMPIDO_TIEMPO = 3;    //Estado interrumpido por tiempo
    public static final int TERMINADO = 2;  //Estado terminado

    public static final int FIFO = 0;
    public static final int RR = 1;
    public static final int HRN = 2;
    public static final int RNM = 3;

    public char id;
    public int estado;
    public int cuenta;
    public int pri;
    public int nivel;

    Proceso(){
        id = ' ';
        estado = 0;
        cuenta = 0;
        pri = 0;
        nivel = 0;
    }

    Proceso(char id){
        this.id = id;
        estado = 0;
        cuenta = 0;
        pri = 0;
        nivel = 0;
    }

    public void siguienteEstado(String s){
        //Math.random() devuelve un n�mero al "azar" mayor o igual a 0.0 y menor que 1.0
        int i1 = 0;
        int i2 = 0;

        if(s == "Fifo"|| s == "HRN"){
            i1 = 2;
        }else if(s == "RoundRobin" || s == "RNM"){
            i1 = 3;
        }else{
            throw new IllegalArgumentException("Los valores permitidos son: Fifo, Hrn, RoundRobin y RNM.");
        }
        //TODO: 
        i2 = (int) (Math.random() * i1) + 1; //El cast (int) trunca.
        //System.out.println(i1 + " " + i2);
        this.estado = i2;
        }

    public void continuaBloqueadoES() throws RuntimeException{
        //TODO: 
        int i = (int) (Math.random () * 4);
        if(i == Proceso.INTERRUMPIDO_TIEMPO){
            i = Proceso.LISTO;
        }
        if(i == Proceso.TERMINADO){
            i = Proceso.INTERRUMPIDO_ES;
        }
        if(i > 3){
            throw new RuntimeException("Esta mal el random...");
        }
        this.estado = i;
    }

    public void continuaBloqueadoTiempo() throws RuntimeException{
        //TODO: 
        int i = (int)(Math.random() * 4);

        if(i == Proceso.INTERRUMPIDO_ES){
            i = Proceso.LISTO;
        }
        if(i == Proceso.TERMINADO){
            i = Proceso.INTERRUMPIDO_TIEMPO;
        }

        if(i > 3){
            throw new RuntimeException("Esta mal el random...");
        }

        this.estado = i;
    }

    public void reInicio(){
        this.estado = 0;
        this.cuenta = 0;
        this.pri = 0;
        this.nivel = 0;
    }
}
