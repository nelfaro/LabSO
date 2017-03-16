
package subsistdiscovariaspet;
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
              "de disco que puede dar servicio a varias "+
              "peticiones a la vez.\n"+
              "Colocar los valores máximos para mu, lambda \ny el número de peticiones pendientes.\n************************************\n\n";
              for(int i=10;i<=muG;i+=10)
                  for(int j=1; j<lambdaG;j+=2)
                      aux+= caso2(i,j,3);
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
        return  (Math.pow(lambda*Math.pow(mu, -1),i)*((Math.pow(Math.E,(lambda*Math.pow(mu, -1))))*(Math.pow(factorial(i),-1))));
                //((1-(lambda*Math.pow(mu,-1))) * Math.pow((lambda*Math.pow(mu,-1)),i));
    }
    private int factorial(int i){
        int aux=1;
        for (int j=1;j<=i;j++)
            aux=aux*j;
        return aux;
    }
    private double es(double mu,double lambda){
        return(lambda*Math.pow(mu,-1));
    }
    private String caso2(double mu,double lambda,int i){
        String salida="";        
        double coef;
        if((lambda/mu)<1){
            salida+="Para que la cola no crezca indefinidamente\n debe ser mayor MU que LAMBDA.\n";
            coef=(lambda/mu);//TODO
            salida+="Analisis de rendimiento de un subsistema de disco.\n";
            salida+="Resultado del Caso2:\n";
            salida+="El subsistema de disco puede dar\n servicio a varias peticiones a la vez.\n";
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
