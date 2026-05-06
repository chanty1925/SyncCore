package com.synccore.logic;

import com.synccore.models.Proceso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler{
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
                }
            }
            //en caso de que la cola este vacia, avanzamos el tiempo en 1
            else{
                tiempoActual++;
            }
        }

    }
}