import javax.swing.*;

public class Procesos {
    private JTextArea salida;
    private int cc = 0;
    private int np = 0;
    private int cambios = 0;
    private Proceso[] p = new Proceso[51];//Para evitar ArrayOutOfBounds si ingresan 50 procesos
    private Proceso aux;

    public void simular(int nroCiclos, int nroProcesos, JTextArea psalida, boolean simularFIFO, boolean simularRR, boolean simularHRN, boolean simularRNM)  throws Exception {
        this.salida = psalida;
        cc = nroCiclos;
        np = nroProcesos;
        println("Estrategias de asignacion del procesador a procesos en ejecución concurrente.");

        crearProcesos();

        if(simularFIFO){
            reiniciarDatos();
            fifo();
        }

        if(simularRR){
            reiniciarDatos();
            roundRobin();
        }

        if(simularHRN){
            reiniciarDatos();
            HRN();
        }

        if(simularRNM){
            reiniciarDatos();
            RNM();
        }
    }

    private void fifo() {
        println("*******************************************************************");
        println("Secuencia de selección de procesos para su ejecución según FIFO.");
        println("*******************************************************************");
        println("Los procesos son atendidos según su orden en la lista de procesos listos.");
        mostrar();

        for(int i = 0; i < cc; i++){
            int fin = 0;
            for (int j = 0; j < np; j++){
                fin += p[j].estado;
            }
            if(fin == np * 2){
                println();
                println("Todos los procesos han finalizado en " + i + " ciclos de control");
                i = cc;
            }

            for(int j = 0; j < np; j++){
                if(p[j].estado == Proceso.LISTO){
                    println("Procesador asignado al proceso: " + p[j].id);
                    p[j].cuenta++;

                    p[j].siguienteEstado("Fifo");

                    if(p[j].estado == Proceso.INTERRUMPIDO_ES){
                        println("Proceso '" + p[j].id + "' interrumpido por Entrada/Salida.");
                    }else if(p[j].estado == Proceso.TERMINADO){
                        println("Proceso '" + p[j].id + "' finalizado.");
                    }

                    if(p[j].estado != Proceso.LISTO){
                        cambios++;
                        aux = p[j];

                        for(int k = j; k < (np - 1); k++){
                            p[k] = p[k + 1];
                        }

                        p[np - 1] = aux;
                    }

                    j = np;
                }
            }

            for(int j = 0; j < np; j++){
                if(p[j].estado == Proceso.INTERRUMPIDO_ES){
                    p[j].continuaBloqueadoES();
                }
            }
        }

        println("Se han producido " + cambios + " cambios de contexto.");
        mostrar();
    }

    private void roundRobin(){
        println("\n************************************************************************************");
        println("Secuencia de selección de procesos para su ejecución según Round Robin.");
        println("************************************************************************************");
        println("Los procesos son atendidos según su orden en la lista de procesos listos,");
        println("pero disponen de un tiempo limitado (Cuantum) del procesador.");

        mostrar();

        for(int ciclos = 0; ciclos < cc; ciclos++){
            boolean terminar = true;

            for(int k = 0; k < np; k++){
                if(p[k].estado != Proceso.TERMINADO){
                    terminar = false;
                }
            }

            if(terminar){
                println("Todos los procesos han finalizado en " + ciclos + " ciclos de control.");
                ciclos = cc;
            }

            for(int i = 0; i < np; i++){
                if(p[i].estado == Proceso.LISTO){
                    println("Procesador asignado al proceso: " + p[i].id + ".");
                    p[i].cuenta++;

                    p[i].siguienteEstado("RoundRobin");

                    if(p[i].estado == Proceso.LISTO){
                        p[i].estado = Proceso.INTERRUMPIDO_TIEMPO;
                    }

                    if(p[i].estado == Proceso.INTERRUMPIDO_ES){
                        println("Proceso " + p[i].id + " interrumpido por Entrada/Salida.");
                    }else if(p[i].estado == Proceso.TERMINADO){
                        println("Proceso " + p[i].id + " finalizado.");
                    }else if(p[i].estado == Proceso.INTERRUMPIDO_TIEMPO){
                        println("Proceso " + p[i].id + " interrumpido por tiempo.");
                    }

                    if(p[i].estado != Proceso.LISTO){
                        cambios++;
                        Proceso aux = p[i];

                        for (int k = i; k < np - 1; k++){
                            p[k] = p[k + 1];
                        }
                        p[np - 1] = aux;
                    }
                    i = np;
                }
            }
            for(int k = 0; k < np; k++){
                if(p[k].estado == Proceso.INTERRUMPIDO_ES){
//                    print(p[k].id + " Actual " + p[k].estado + " Siguiente: ");
                    p[k].continuaBloqueadoES ();
//                    println(p[k].estado);
                }
            }

            for(int k = 0; k < np; k++){
                if(p[k].estado == Proceso.INTERRUMPIDO_TIEMPO){
                    p[k].continuaBloqueadoTiempo ();
                }
            }
        }
        println("Se han producido " + cambios + " cambios de contexto.");
        mostrar();
    }

    private void HRN() {
        int aux1 = 0; // Para saber si terminaron los procesos
        int aux2 = 0; // Para saber si hubo intercambio de contexto y debe reordenarse
                           // la lista de procesos.

        println("**********************************************************************");
        println("Secuencia de selección de procesos para su ejecuión según HRN.");
        println("**********************************************************************");
        println("\n Los procesos son atendidos según su prioridad en la lista de procesos listos,");
        println("la prioridad depende de la relación de respuesta: (TE + TS) / TS, donde ");
        println("TE = Tiempo de espera y TS = Tiempo de Servicio.");
        println("\n");
        for(int i = 0; i < cc; i++){
            aux1 = 0;
            for(int j = 0; j < np; j++){
                aux1 = aux1 + p[j].estado;
            }
            if(aux1 == (np * 2)){
                println("\nTodos los procesos han finalizado en " + i + " ciclos de control.");
                i = cc;
            }
            if(i == 0){
                for(int k = 0; k < np; k++){
                    p[k].cuenta = 1;
                }
            }

            if(i < cc){
                for(int k = 0; k < np; k++){
                    //System.out.println("Ciclo " + i + " " + p[k].cuenta);
                    p[k].pri = (i / p[k].cuenta); //Calcula la prioridad de los procesos.
                }
            }

            if(aux2 == 1){ // Si hubo cambio de contexto, ordenar los procesos seg�n prioridad.
                aux = null;
                for(int k = 0; k < np; k++){
                    for(int l = k; l < (np - 1); l++){
                        if(p[l + 1].pri > p[l].pri){
                            aux = p[l];
                            p[l] = p[l + 1];
                            p[l + 1] = aux;
                        }
                    }
                }
            }

            for(int j = 0; j < np; j++){
                if(p[j].estado == Proceso.LISTO){
                    aux2 = 0;

                    println("Procesador asignado al proceso " + p[j].id + ".");
                    p[j].cuenta++;
                    p[j].siguienteEstado ("HRN");

                    if(p[j].estado == Proceso.INTERRUMPIDO_ES){
                        println("Proceso " + p[j].id + " interrumpido por Entrada/Salida.");
                    }else if(p[j].estado == Proceso.TERMINADO){
                        println("Proceso " + p[j].id + " finalizado.");
                    }

                    if(p[j].estado != Proceso.LISTO){ // Intercambio de contexto
                        cambios++;
                        aux = p[j];
                        for(int k = j; k < np - 1; k++){
                            p[k] = p[k + 1];
                        }
                        p[np - 1] = aux;
                        aux2 = 1; //Indica que hubo intercambio de contexto y debe reordenarse la lista de procesos seg�n prioridades.
                    }
                    j = np;
                }

                for(int k = 0; k < np; k++){
                    if(p[k].estado == Proceso.INTERRUMPIDO_ES){
                        p[k].continuaBloqueadoES ();
                    }
                }
            }
        }
        for(int k = 0; k < np; k++){
            p[k].cuenta--;
        }
        println("Se han producido " + cambios + " cambios de contexto.");
        mostrar();
    }

    private void RNM() {
        println("\n***************************************************************************");
        println("Secuencia de selección de procesos para su ejecución según RNM");
        println("***************************************************************************");
        println("\n Los procesos son atendidos según su nivel en la lista de procesos listos, ");
        println("pero disponen de un tiempo limitado (cuantum) del procesador. Si son ");
        println("interrumpidos por Entrada/Salida permanecen en la subcola del nivel donde están");
        println("pero, si son interrumpidos por tiempo, pasan a un nivel superior que es atendido");
        println("cuando ya no hay procesos listos en los niveles inferiores; de alli el nombre");
        println("de 'Retroalimentación de Niveles Múltiples.'");
        println();
        mostrar();

        int aux1 = 0;
        int aux2 = 0;
        for(int j = 0; j < cc; j++){

            aux1 = 0;
            for(int k = 0; k < np; k++){
                if(p[k].estado != Proceso.TERMINADO){
                    aux1 = 1;
                }
            }
            if(aux1 == 0){
                println("\nTodos los procesos han finalizado en " + j + " ciclos de control.");
                j = cc;
            }

            if(aux2 == 1){// SI hubo cambio de contexto, los procesos de menor nivel se ponen primero
                for(int k = 0; k < np; k++){
                    for(int l = k; l < np - 1; l++){
                        if(p[l + 1].nivel < p[l].nivel){
                            aux = p[l];
                            p[l] = p[l + 1];
                            p[l + 1] = aux;
                        }
                    }
                }
            }

            for(int i = 0; i < np; i++){
                if(p[i].estado == Proceso.LISTO){
                    aux2 = 0;
                    println("Procesador asignado al procesos " + p[i].id + ".");

                    p[i].cuenta++;
                    p[i].siguienteEstado ("RNM");
                    if(p[i].estado == Proceso.LISTO){
                        p[i].estado = Proceso.INTERRUMPIDO_TIEMPO;
                    }

                    if(p[i].estado == Proceso.INTERRUMPIDO_ES){
                        println("Proceso " + p[i].id + " interrumpido por Entrada/Salida.");
                    }else if(p[i].estado == Proceso.TERMINADO){
                        println("Proceso " + p[i].id + " finalizado.");
                    }else if(p[i].estado == Proceso.INTERRUMPIDO_TIEMPO){
                        println("Proceso " + p[i].id + " interrumpido por Tiempo.");
                        p[i].nivel++;
                    }

                    if(p[i].estado != Proceso.LISTO){
                        cambios++;
                        aux = p[i];
                        for(int k = i; k < np; k++){
                            p[k] = p[k + 1];
                        }
                        p[np - 1] = aux;
                        aux2 = 1; // Indica que hubo intercambio de contexto y debe reordenarse la lista de procesos
                    }

                    i = np;
                }
            }

            for(int k = 0; k < np; k++){
                if(p[k].estado == Proceso.INTERRUMPIDO_ES){
                    p[k].continuaBloqueadoES ();
                }else if(p[k].estado == Proceso.INTERRUMPIDO_TIEMPO){
                    p[k].continuaBloqueadoTiempo ();
                }
            }
        }

        println("Se han producido " + cambios + " cambios de contexto.");
        mostrar();
    }

    private void crearProcesos() {
            for(int i = 0; i < 50; i++){
                p[i] = new Proceso();
            }
                p[0].id = 'a';
                p[1].id = 'b';
                p[2].id = 'c';
                p[3].id = 'd';
                p[4].id = 'e';
                p[5].id = 'f';
                p[6].id = 'g';
                p[7].id = 'h';
                p[8].id = 'i';
                p[9].id = 'j';
                p[10].id = 'k';
                p[11].id = 'l';
                p[12].id = 'm';
                p[13].id = 'n';
                p[14].id = 'o';
                p[15].id = 'p';
                p[16].id = 'q';
                p[17].id = 'r';
                p[18].id = 's';
                p[19].id = 't';
                p[20].id = 'u';
                p[21].id = 'v';
                p[22].id = 'w';
                p[23].id = 'x';
                p[24].id = 'y';
                p[25].id = 'z';
                p[26].id = '1';
                p[27].id = '2';
                p[28].id = '3';
                p[29].id = '4';
                p[30].id = '5';
                p[31].id = '6';
                p[32].id = '7';
                p[33].id = '8';
                p[34].id = '9';
                p[35].id = 'A';
                p[36].id = 'B';
                p[37].id = 'C';
                p[38].id = 'D';
                p[39].id = 'E';
                p[40].id = 'F';
                p[41].id = 'G';
                p[42].id = 'H';
                p[43].id = 'I';
                p[44].id = 'J';
                p[45].id = 'K';
                p[46].id = 'L';
                p[47].id = 'M';
                p[48].id = 'N';
                p[49].id = 'O';
        }

    private void reiniciarDatos(){
            for(int i = 0; i < np; i++){
                p[i].reInicio();
                cambios = 0;
            }

            Proceso proaux = null;

            for(int j = 0; j < np; j++){
                for(int i = 0; i < np - 1; i++){
                    if(p[i].id > p[i + 1].id){
                        proaux = p[i];
                        p[i] = p[i + 1];
                        p[i + 1] = proaux;
                    }
                }
            }
        }

    private void mostrar(){
            println("\nListado de Procesos");
            println("*********************");
            println("Estado: 0 -> Proceso listo para ejecutar.");
            println("Estado: 1 -> Proceso interrumpido en espera por E/S.");
            println("Estado: 2 -> Proceso terminado.");
            println("Estado: 3 -> Proceso interrumpido por tiempo (no aplicable en FIFO ni en HRN).");

            println("Proceso\tCiclos Utilizados\tPrioridad\tNivel\tEstado");
            for (int i = 0; i < np; i++){
                println("" + p[i].id + "\t\t\t" +            p[i].cuenta+"\t\t    "+p[i].pri+"\t\t  "+p[i].nivel+"\t  "+p[i].estado);
            }
            println();
        }

    private void mostrarEstados(){
            for (int z = 0; z < np; z++){
                println(p[z].id);
            }
            println();
            for (int z = 0; z < np; z++){
                println(p[z].estado);
            }
            println();
        }

    private void print(Object o){
            String s = o.toString();
            salida.append(s);
        }

    private void println(Object o){
            String s = o.toString ();
            salida.append(s + "\n");
        }

    private void println(){
            salida.append("\n");
        }

   

}