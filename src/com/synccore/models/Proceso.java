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

    // (Getters)
    public int getPid() { 
        return pid; 
    }
    public int getTiempoArribo() { 
        return tiempoArribo; 
    }
    public int getTiempoRafaga() { 
        return tiempoRafaga; 
    }
    public int getTiempoRestante() { 
        return tiempoRestante; 
    }
    //Setters
    public void setPid (int pid){
        this.pid = pid;
    }
    public void setTiempoArribo (int tiempoArribo){
        this.tiempoArribo = tiempoArribo;
    }
    public void setTiempoRafaga (int tiempoRafaga){
        this.tiempoRafaga = tiempoRafaga;
    }
    public void setTiempoRestante (int tiempoRestante){
        this.tiempoRestante = tiempoRestante;
    }

    //Metodo para reducir tiempo segun el quantum
    public void restarTiempo (int quantum){
        this.tiempoRestante -= quantum;
        if(tiempoRestante < 0)this.tiempoRestante = 0;
    }

}