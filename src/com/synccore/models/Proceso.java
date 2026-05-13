package com.synccore.models;

public class Proceso {
    private int pid;
    private int tiempoArribo;
    private int tiempoRafaga;
    private int tiempoRestante;
    private int tiempoFinalizacion;
    private int tiempoRetorno;
    private int tiempoEspera;
    private int prioridad;

    // Se ejecuta cuando se crea el proceso
    public Proceso(int pid, int tiempoArribo, int tiempoRafaga) {
        this.pid = pid;
        this.tiempoArribo = tiempoArribo;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga;
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
    public int getTiempoFinalizacion(){
        return tiempoFinalizacion;
    }
    public int getTiempoRetorno(){
        return tiempoRetorno;
    }
    public int getTiempoEspera(){
        return tiempoEspera;
    }
    public int getPrioridad(){
        return prioridad;
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
    public void setTiempoFinalizacion(int tiempoFinalizacion){
        this.tiempoFinalizacion = tiempoFinalizacion;
    }
    public void setTiempoRetorno(int tiempoRetorno){
        this.tiempoRetorno = tiempoRetorno;
    }
    public void setTiempoEspera(int tiempoEspera){
        this.tiempoEspera = tiempoEspera;
    }
    public void setPrioridad(int prioridad){
        this.prioridad = prioridad;
    }

    //Metodo para reducir tiempo segun el quantum
    public void restarTiempo (int quantum){
        this.tiempoRestante -= quantum;
        if(tiempoRestante < 0)this.tiempoRestante = 0;
    }

    //Calcular la metrica del algoritmo
    public void calcularMetrica(){
        this.tiempoRetorno = this.tiempoFinalizacion - this.tiempoArribo;
        this.tiempoEspera = this.tiempoRetorno - this.tiempoRafaga;
    }

}