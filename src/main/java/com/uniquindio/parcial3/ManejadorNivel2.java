package com.uniquindio.parcial3;

public class ManejadorNivel2 extends ManejadorEscalamiento{
    @Override
    public int manejar(Ticket ticket) {
        if (ticket.getPrioridad() == Prioridad.MEDIA) {
            System.out.println("[CHAIN] ManejadorNivel2: Prioridad MEDIA detectada -> Atendido por Soporte Nivel 2.");
            return 2;
        }

        System.out.println("[CHAIN] ManejadorNivel2: Prioridad " + ticket.getPrioridad() + " supera Nivel 2. Escalando a Nivel 3...");
        if (siguiente != null) {
            return siguiente.manejar(ticket);
        }
        return 2;
    }

}
