/*
(* TEORIA DE COLAS *)
(* Análisis del rendimiento de un subsistema de disco *)
(* Caso 1: El subsistema de disco solo puede dar servicio a una petición a la vez. *)
(* Referencias y aclaraciones:
mu: Tasa promedio de servicio para un servidor.
mu = 1/Es(s)
Es(s): Tiempo de espera de servicio para un cliente.
lambda: Tasa promedio de llegadas de clientes al sistema de colas.
lambda = 1/Es(tau)
Es(tau): Tiempo de espera entre llegadas.
Es(tau) = 1/lambda
mu > lambda: Restricción para que la longitud de la cola de peticiones no crezca indefinidamente.
tau: Intervalo entre llegadas.
Pn: Probabilidad de que haya "n" clientes en el sistema de colas en estado estable.
P0: Probabilidad de que el sistema se encuentre ocioso.
P0 = 1-(lambda/mu)
Pri: Probabilidad de que hayan "i" peticiones pendientes.
Pri = (1-(lambda/mu))(lambda/mu)^i
(i=0,1,2,3,..)
Es(i): N° promedio de peticiones pendientes.
Es(i) = (lambda/mu)(1-(lambda/mu))^(-1) *)
 */
package subsistdiscounapet;
public class Analisis {
    double tau,Pn,P0,muG,lambdaG,petPenG;
    private java.util.ArrayList<Double> listaCoef;
    private java.util.ArrayList<Double> listaPetPend;
    private java.util.ArrayList<double[]> listaPuntos;
    public Analisis(double mu,double lambda, int petPen){
        this.listaCoef=new java.util.ArrayList();
        this.listaPetPend= new java.util.ArrayList();
        this.listaPuntos= new java.util.ArrayList<double[]>();
        muG=mu; lambdaG=lambda; petPenG=petPen;
    }
    public String salida(){
        this.getListaCoef().clear();
        this.getListaPetPend().clear();
        this.getListaPuntos().clear();
       String aux= "Análisis del rendimiento de un subsistema \n"+
              "de disco que solo puede dar servicio a una "+
              "petición a la vez.\n"+
              "Colocar los valores máximos para mu, lambda \ny el número de peticiones pendientes.\n************************************\n\n";
              for(int i=10;i<=muG;i+=10)
                  for(int j=1; j<lambdaG;j+=2)
                      aux+= caso1(i,j,3);
       aux+="\n\n*************************************";
       aux+="\n*******    RESUMEN FINAL   *******";
       aux+="\n*************************************";
       aux+="\n\n Lista de Coeficientes Mu/Lambda:\n{";
       for(int i=0;i<this.listaCoef.size();i++){
           if(i%10==0 && i!=0) aux+="\n";
           if(i!=(this.listaCoef.size()-1))
               aux+=" "+((double)this.listaCoef.get(i))+";";
           else
               aux+=" "+((double)this.listaCoef.get(i))+"}";
       }
       aux+="\n\n Lista de Peticiones Pendientes:\n{";
       for(int i=0;i<this.listaPetPend.size();i++){
           if(i%10==0 && i!=0) aux+="\n";
           if(i!=(this.listaPetPend.size()-1))
               aux+=" "+((double)this.listaPetPend.get(i))+";";
           else
               aux+=" "+((double)this.listaPetPend.get(i))+"}";
       }
       aux+="\n\n Lista de Pares Ordenados:\n{";
       for(int i=0;i<this.listaPuntos.size();i++){
           if(i%10==0 && i!=0) aux+="\n";
           if(i!=(this.listaPuntos.size()-1))
               aux+=" ("+this.listaPuntos.get(i)[0]+", "+this.listaPuntos.get(i)[1]+");";
           else
               aux+=" ("+this.listaPuntos.get(i)[0]+", "+this.listaPuntos.get(i)[1]+")};";
       }
       return aux;
    }
    private double pri(double mu,double lambda,int i){
        return ((1-(lambda*Math.pow(mu,-1))) * Math.pow((lambda*Math.pow(mu,-1)),i));
    }
    private double es(double mu,double lambda){
        return(lambda*Math.pow(mu,-1))* Math.pow((1-(lambda*Math.pow(mu,-1))),(-1));
    }
    private String caso1(double mu,double lambda,int i){
        String salida="";        
        double coef;
        if((lambda/mu)<1){
            salida+="Para que la cola no crezca indefinidamente\n debe ser mayor MU que LAMBDA.\n";
            coef=(lambda/mu);//TODO
            salida+="Analisis de rendimiento de un subsistema de disco.\n";
            salida+="Resultado del Caso1:\n";
            salida+="El subsistema de disco solo puede dar\n servicio a una peticion a la vez.\n";
            salida+="Los valores de mu, lambda y el coeficiente\n son los siguientes:\n";
            salida+=""+mu+", "+lambda+", "+coef+"\n";
            salida+="La cola no crecera eindefinidamente debido\n a que Mu es mayor que Lambda.\n";
            salida+="La probabilidad de tener 0,1,2,3,4,... peticiones\n";
            salida+="pendientes son las siguientes: {";
            for(int j=0;j<=i;j++)
                if(j!=i)
                    salida+=""+pri(mu,lambda,j)+" ,";
                else
                    salida+=""+pri(mu,lambda,j);
            salida +="}\n";
           // salida+="pendientes son las siguientes:"+ pri(mu,lambda,i) +"\n";
            salida+="El promedio de peticiones pendintes\n es el siguiente: "+es(mu,lambda) +"\n";
            this.getListaCoef().add(coef);
            this.getListaPetPend().add(es(mu,lambda));
            double[] punto=new double[2];
            punto[0]=coef;
            punto[1]=es(mu,lambda);
            this.getListaPuntos().add(punto);
            salida+="*****************************************\n";
        }
        return salida;
    }

    /**
     * @return the listaCoef
     */
    public java.util.ArrayList getListaCoef() {
        return listaCoef;
    }


    /**
     * @return the listaPetPend
     */
    public java.util.ArrayList getListaPetPend() {
        return listaPetPend;
    }

    /**
     * @return the listaPuntos
     */
    public java.util.ArrayList<double[]> getListaPuntos() {
        return listaPuntos;
    }

  
}
