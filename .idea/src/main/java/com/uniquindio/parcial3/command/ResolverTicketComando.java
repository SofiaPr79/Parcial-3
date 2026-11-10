package com.uniquindio.parcial3.command;

public class ResolverTicketComando implements Comando{
    private Ticket ticket;

    public ResolverTicketComando(Ticket ticket) {
        this.ticket = ticket;
    }
    @Override
    public void ejecutar() {
        System.out.println("[COMMAND] Ejecutando ResolverTicketComando para Ticket #" + ticket.getId());
        // PATRÓN MEMENTO: Guarda instantánea previa
        ticket.guardarSnapshot();
        // PATRÓN STATE: Transición a Resuelto
        ticket.resolver();
    }

    @Override
    public void deshacer() {
        System.out.println("[COMMAND] Deshaciendo ResolverTicketComando para Ticket #" + ticket.getId());
        // PATRÓN MEMENTO: Restaura estado anterior (En Proceso)
        ticket.deshacerCambio();
    }
}
