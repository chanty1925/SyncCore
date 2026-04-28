package com.synccore.models;

public class Proceso {
    private int pid;
    private int tiempoArribo;
    private int tiempoRafaga;
    private int tiempoRestante;

    // Constructor: Se ejecuta cuando creas el proceso
    public Proceso(int pid, int tiempoArribo, int tiempoRafaga) {
        this.pid = pid;
        this.tiempoArribo = tiempoArribo;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga; // Al inicio, falta todo por ejecutar
    }

    // Métodos para obtener los datos (Getters)
    public int getPid() { return pid; }
    public int getTiempoArribo() { return tiempoArribo; }
    public int getTiempoRafaga() { return tiempoRafaga; }
    public int getTiempoRestante() { return tiempoRestante; }
}