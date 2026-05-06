package com.synccore;

import java.util.ArrayList;

import com.synccore.logic.Scheduler;
import com.synccore.models.Proceso;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SyncCore: Simulador de Procesos Iniciado ===");
        
        System.out.println("=================Ejecutando Round Robin=================");
        ArrayList<Proceso> ListaPrueba = new ArrayList<>();
        ListaPrueba.add(new Proceso(1, 0, 3));
        ListaPrueba.add(new Proceso(2, 1, 3));
        ListaPrueba.add(new Proceso(3, 2, 8));

        Scheduler rr = new Scheduler();
        rr.ejecutarRoundrobin(ListaPrueba, 3);
        System.out.println("Acabado");
    }
}