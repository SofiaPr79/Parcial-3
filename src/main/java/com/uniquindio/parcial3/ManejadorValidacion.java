package com.uniquindio.parcial3;

public class ManejadorValidacion extends ManejadorEscalamiento{
    @Override
    public int manejar(Ticket ticket) {
        if (ticket == null || ticket.getDescripcion() == null || ticket.getDescripcion().trim().isEmpty()) {
            System.out.println("[CHAIN] ManejadorValidacion: RECHAZADO - Ticket con datos vacíos o inválidos. Se corta la cadena.");
            return 0; // 0 indica ticket inválido
        }

        System.out.println("[CHAIN] ManejadorValidacion: Ticket #" + ticket.getId() + " validado con éxito. Pasa al siguiente eslabón.");
        if (siguiente != null) {
            return siguiente.manejar(ticket);
        }
        return 1;
    }
}
