package com.uniquindio.parcial3.command;

public class CambiarPrioridadComando implements Comando {
    private Ticket ticket;
    private Prioridad nuevaPrioridad;

    public CambiarPrioridadComando(Ticket ticket, Prioridad nuevaPrioridad) {
        this.ticket = ticket;
        this.nuevaPrioridad = nuevaPrioridad;
    }

    @Override
    public void ejecutar() {
        System.out.println("[COMMAND] Ejecutando CambiarPrioridadComando: Ticket #" + ticket.getId()
                + " prioridad cambiada a " + nuevaPrioridad);
        // PATRÓN MEMENTO: Respalda el estado y la prioridad actual
        ticket.guardarSnapshot();
        // Aplica el cambio y notifica a los observadores
        ticket.setPrioridad(nuevaPrioridad);
        ticket.notificarObservadores("Prioridad actualizada a: " + nuevaPrioridad);
    }

    @Override
    public void deshacer() {
        System.out.println("[COMMAND] Deshaciendo CambiarPrioridadComando: Revertir prioridad de Ticket #" + ticket.getId());
        // PATRÓN MEMENTO: Restaura la prioridad y estado guardados en la foto
        ticket.deshacerCambio();
    }
}
