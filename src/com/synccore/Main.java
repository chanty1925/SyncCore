package com.synccore;

import java.util.ArrayList;
import com.synccore.logic.Scheduler;
import com.synccore.models.Proceso;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Pruebas ===\n");
        Scheduler planificador = new Scheduler();

        // =====================================================================
        // PRUEBA 1: FCFS vs SJF (Para ver la diferencia de tiempos)
        // =====================================================================
        System.out.println("================= Ejecutando FCFS =================");
        ArrayList<Proceso> listaFCFS = new ArrayList<>();
        listaFCFS.add(new Proceso(1, 0, 7));
        listaFCFS.add(new Proceso(2, 2, 4));
        listaFCFS.add(new Proceso(3, 4, 1));
        listaFCFS.add(new Proceso(4, 5, 4));
        planificador.ejecutarFCFS(listaFCFS); 
        imprimirResultados(listaFCFS);

        System.out.println("\n================= Ejecutando SJF ==================");
        ArrayList<Proceso> listaSJF = new ArrayList<>();
        listaSJF.add(new Proceso(1, 0, 7));
        listaSJF.add(new Proceso(2, 2, 4));
        listaSJF.add(new Proceso(3, 4, 1));
        listaSJF.add(new Proceso(4, 5, 4));
        planificador.ejecutarSJF(listaSJF);
        imprimirResultados(listaSJF);

        // =====================================================================
        // PRUEBA 2: SRTF (Para forzar interrupciones)
        // =====================================================================
        System.out.println("\n================= Ejecutando SRTF =================");
        ArrayList<Proceso> listaSRTF = new ArrayList<>();
        listaSRTF.add(new Proceso(1, 0, 8));
        listaSRTF.add(new Proceso(2, 1, 4)); // Este debe interrumpir al Proceso 1
        listaSRTF.add(new Proceso(3, 2, 9));
        listaSRTF.add(new Proceso(4, 3, 5));
        planificador.ejecutarSRTF(listaSRTF);
        imprimirResultados(listaSRTF);

        // =====================================================================
        // PRUEBA 3: Prioridades (Menor número = mayor prioridad)
        // =====================================================================
        System.out.println("\n============== Ejecutando PRIORIDADES =============");
        ArrayList<Proceso> listaPrio = new ArrayList<>();
        listaPrio.add(new Proceso(1, 0, 10, 3));
        listaPrio.add(new Proceso(2, 1, 1, 1)); // Llega tarde pero es el principal
        listaPrio.add(new Proceso(3, 2, 2, 4));
        listaPrio.add(new Proceso(4, 3, 1, 5));
        listaPrio.add(new Proceso(5, 4, 5, 2));
        planificador.ejecutarPrioridad(listaPrio);
        imprimirResultados(listaPrio);
        
        // =====================================================================
        // PRUEBA 4: Round Robin con quantum de 3
        // =====================================================================
        System.out.println("\n=============== Ejecutando ROUND ROBIN ============");
        ArrayList<Proceso> listaRR = new ArrayList<>();
        listaRR.add(new Proceso(1, 0, 5));
        listaRR.add(new Proceso(2, 1, 3));
        listaRR.add(new Proceso(3, 2, 1));
        listaRR.add(new Proceso(4, 3, 2));
        listaRR.add(new Proceso(5, 4, 3));
        planificador.ejecutarRoundrobin(listaRR, 3);
        imprimirResultados(listaRR);
    }

    // Metodo para imprimir en consola
    public static void imprimirResultados(ArrayList<Proceso> lista) {
        // Se ordena por PID para que la tabla se lea en orden 1, 2, 3...
        lista.sort((p1, p2) -> Integer.compare(p1.getPid(), p2.getPid()));
        
        System.out.println("PID\tLlegada\tRáfaga\tPrio\t| TF\tTR\tTE");
        System.out.println("---------------------------------------------------------");
        for (Proceso p : lista) {
            System.out.printf("%d\t%d\t%d\t%d\t| %d\t%d\t%d\n", 
                p.getPid(), p.getTiempoArribo(), p.getTiempoRafaga(), p.getPrioridad(),
                p.getTiempoFinalizacion(), p.getTiempoRetorno(), p.getTiempoEspera());
        }
    }
}