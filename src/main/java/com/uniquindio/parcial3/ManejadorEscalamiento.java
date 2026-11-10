package com.uniquindio.parcial3;

public abstract class ManejadorEscalamiento {
    protected ManejadorEscalamiento siguiente;

    public void setSiguiente(ManejadorEscalamiento siguiente) {
        this.siguiente = siguiente;
    }
    public abstract int manejar(Ticket ticket);
}
