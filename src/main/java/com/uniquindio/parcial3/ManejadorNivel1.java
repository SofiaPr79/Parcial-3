package com.uniquindio.parcial3;

public class ManejadorNivel1 extends ManejadorEscalamiento{
    @Override
    public int manejar(Ticket ticket) {
        if (ticket.getPrioridad() == Prioridad.BAJA) {
            System.out.println("[CHAIN] ManejadorNivel1: Prioridad BAJA detectada -> Atendido por Soporte Nivel 1.");
            return 1;
        }
        System.out.println("[CHAIN] ManejadorNivel1: Prioridad " + ticket.getPrioridad() + " supera Nivel 1. Escalando a Nivel 2...");
        if (siguiente != null) {
            return siguiente.manejar(ticket);
        }
        return 1;
    }
}
