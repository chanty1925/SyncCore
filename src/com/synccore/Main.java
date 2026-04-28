package com.synccore;

import com.synccore.models.Proceso;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SyncCore: Simulador de Procesos Iniciado ===");
        
        // Creamos un proceso de prueba para ver si reconoce la clase Proceso
        Proceso p1 = new Proceso(1, 0, 8);
        System.out.println("Test: Proceso " + p1.getPid() + " creado con exito.");
    }
}