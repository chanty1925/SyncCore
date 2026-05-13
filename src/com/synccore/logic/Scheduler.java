package com.synccore.logic;

import com.synccore.models.Proceso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;


public class Scheduler{
    //===========================INICIO ROUND ROBIN===================================================
    public void ejecutarRoundrobin(ArrayList<Proceso> listaProcesos, int quantum){
        Queue<Proceso>colaProcesos = new LinkedList<>(); //cola de procesos para round robin

        int tiempoActual = 0;
        int procesosCompletados = 0;
        int totalProcesos = listaProcesos.size();

        while(procesosCompletados < totalProcesos){
            //agregar los procesos a la cola de procesos
            
                for(int i = 0; i < listaProcesos.size(); i++){      //recorrido de la lista de procesos
                Proceso auxProceso = listaProcesos.get(i);     //accedemos al objeto proceso mediante un auxiliar
                int tiempoProceso = auxProceso.getTiempoArribo();      //el tiempo del proceso
                if(tiempoProceso <= tiempoActual){      //mientras el tiempo del proceso sea menor o igual se pone en la cola
                    colaProcesos.add(auxProceso);      //se pone en la cola ese proceso
                    listaProcesos.remove(i);      //se remueve de la lista
                    i--;        //sirve para no saltarse procesos de la lista
                }
            }

            if(!colaProcesos.isEmpty()){
                //sacamos el proceso de la cola
                Proceso auxProceso = colaProcesos.poll();
                //verificar que tiempo se va ejecutar
                int tiempoAEjecutar = Math.min(auxProceso.getTiempoRestante(), quantum);
                
                System.out.println("[T=" + tiempoActual + "] Entra PID: " + auxProceso.getPid() + " a ejecutarse por " + tiempoAEjecutar + "ms");
                //cambiuar el tiempo restante del proceso
                auxProceso.setTiempoRestante(auxProceso.getTiempoRestante() - tiempoAEjecutar);
                tiempoActual += tiempoAEjecutar;

                //verificamos si durante el tiempo actual hay un proceso que se arrime 
                // antes de acabar el proceso quie se esta trabajando
                for(int i = 0; i < listaProcesos.size(); i++){
                    Proceso nuevo = listaProcesos.get(i);
                    if(nuevo.getTiempoArribo() <= tiempoActual){
                        colaProcesos.add(nuevo);
                        listaProcesos.remove(i);
                        i--;
                    }
                }

                //si el proceso no culmino volvemos a ponerlo en la cola
                if(auxProceso.getTiempoRestante() > 0){
                    System.out.println("    -> PID " + auxProceso.getPid() + " vuelve a la fila. Le restan: " + auxProceso.getTiempoRestante() + "ms");
                    colaProcesos.add(auxProceso);
                }
                else{
                    System.out.println("    -> [T=" + tiempoActual + "] ¡PID " + auxProceso.getPid() + " TERMINADO!");
                    procesosCompletados++;
                    auxProceso.setTiempoFinalizacion(tiempoActual);//guardo el tiempo de finalizacion del proceso
                    auxProceso.calcularMetrica(); // calculo la metrica de una  vez del algoritmo
                }
            }
            //en caso de que la cola este vacia, avanzamos el tiempo en 1
            else{
                tiempoActual++;
            }
        }

    }
    //==========================FIN ROUND ROBIN============================================

    //==========================INICIO FCFS================================================
    public void ejecutarFCFS(ArrayList<Proceso> listaProcesos){
        //organizar la lista de procesos segun su tiempo de llegada o arribo 
        // con la funcion sort Comparator nativo de Java basado en el algoritmo 
        // Timsort(basado en merge/insertion sort) con complejidad O(n log n)
         listaProcesos.sort(Comparator.comparingInt(Proceso::getTiempoArribo));          
         int tiempoActual = 0;
         for(int i = 0; i < listaProcesos.size(); i++){
            Proceso aux = listaProcesos.get(i);
            if(tiempoActual <= aux.getTiempoArribo()){
                tiempoActual +=(aux.getTiempoArribo() - tiempoActual);
            }
            tiempoActual += aux.getTiempoRafaga();
            
            aux.setTiempoFinalizacion(tiempoActual);
            aux.calcularMetrica();
        }
    }
    //=========================FIN FCFS====================================================

    //=========================INICIO SJF=================================================
    public void ejecutarSJF(ArrayList<Proceso> listaProceso){
        //organizar la lista de procesos segun su tiempo de llegada o arribo 
        // con la funcion sort Comparator nativo de Java basado en el algoritmo 
        // Timsort(basado en merge/insertion sort) con complejidad O(n log n)
        listaProceso.sort(Comparator.comparingInt(Proceso::getTiempoArribo));//se ordena segun su tiempo de arribo
        //creaamos una cola de prioridad segun el tiempo de rafaga de los procesos ya ordenados
        //por el tiempo de arribo con la funcion Comparator
        //=========IMPORTANTE=================================//
        //(Comparator.comparingInt(Proceso::getTiempoRafaga).thenComparing(Proceso::getTiempoArribo));
        //esta linea del Queue le asigna dos condiciones a la cola, diciendo que en
        //en caso de haber dos procesos de igual tiempo de rafaga, se revisara el tiempo por el cual se llego
        PriorityQueue<Proceso> colaListos = new PriorityQueue<>(Comparator.comparingInt(Proceso::getTiempoRafaga).thenComparing(Proceso::getTiempoArribo));
        int tiempoActual = 0;
        int procesosCompletados = 0;
        int totalProcesos = listaProceso.size();
        while(procesosCompletados < totalProcesos){
            
            while(!listaProceso.isEmpty()){
                Proceso aux = listaProceso.get(0);
                if(aux.getTiempoArribo() <= tiempoActual){
                    colaListos.add(aux);
                    listaProceso.remove(0);
                }
                else{
                    break;
                }
            }
            if(!colaListos.isEmpty()){
                Proceso auxProceso = colaListos.poll();
                tiempoActual = auxProceso.getTiempoRafaga() + tiempoActual;
                procesosCompletados++;
                auxProceso.setTiempoFinalizacion(tiempoActual);
                auxProceso.calcularMetrica();
            }
            else{
                Proceso auxProceso = listaProceso.get(0);
                tiempoActual = auxProceso.getTiempoArribo();
            }
        }
    }
    //=============================FIN SJF============================================


    //==============================INICIO PRIORIDAD===============================================
    public void ejecutarPrioridad(ArrayList<Proceso> listaProceso){
        listaProceso.sort(Comparator.comparingInt(Proceso::getTiempoArribo));
        PriorityQueue<Proceso> colaProceso = new PriorityQueue<>(Comparator.comparingInt(Proceso::getPrioridad).thenComparing(Proceso::getTiempoArribo));
        int tiempoActual = 0;
        int procesosCompletados = 0;
        int totalProcesos = listaProceso.size();

        while(procesosCompletados < totalProcesos){
            while(!listaProceso.isEmpty()){
                Proceso aux = listaProceso.get(0);
                if(aux.getTiempoArribo() <= tiempoActual){
                    colaProceso.add(aux);
                    listaProceso.remove(0);
                }
                else{
                    break;
                }
            }
            if(!colaProceso.isEmpty()){
                Proceso aux = colaProceso.poll();
                tiempoActual = aux.getTiempoRafaga() + tiempoActual;
                procesosCompletados++;
                aux.setTiempoFinalizacion(tiempoActual);
                aux.calcularMetrica();
            }
            else{
                Proceso aux = listaProceso.get(0);
                tiempoActual = aux.getTiempoArribo();
            }
        }
    }
    //=======================FIN PRIORIDAD==========================================

    //================================INICIO SRTF==========================================
    public void ejecutarSRTF(ArrayList<Proceso> listaProcesos){
        int tiempoActual = 0;
        int procesosCompletados = 0;
        int totalProcesos = listaProcesos.size();
        listaProcesos.sort(Comparator.comparingInt(Proceso::getTiempoArribo));
        PriorityQueue <Proceso> colaProcesos = new PriorityQueue<>(Comparator.comparing(Proceso::getTiempoRestante).thenComparing(Proceso::getTiempoArribo));
        while(procesosCompletados < totalProcesos){
            while(!listaProcesos.isEmpty()){
                Proceso aux = listaProcesos.get(0);
                if(aux.getTiempoArribo() <= tiempoActual){
                    colaProcesos.add(aux);
                    listaProcesos.remove(0);
                }
                else{
                    break;
                }
            }
            if(!colaProcesos.isEmpty()){
                Proceso aux = colaProcesos.poll();
                tiempoActual++;
                aux.setTiempoRestante(aux.getTiempoRestante() - 1);
                if(aux.getTiempoRestante() > 0){
                    colaProcesos.add(aux);
                }
                else{
                    procesosCompletados++;
                    aux.setTiempoFinalizacion(tiempoActual);
                    aux.calcularMetrica();
                }
            }
            else{
                Proceso prox = listaProcesos.get(0);
                tiempoActual = prox.getTiempoArribo();
            }
        }
    }
    //=============================FIN SRTF=========================================

}