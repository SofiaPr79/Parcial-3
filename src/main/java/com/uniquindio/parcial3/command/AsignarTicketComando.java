package com.uniquindio.parcial3.command;
import com.uniquindio.parcial3.Agente;
import com.uniquindio.parcial3.Ticket;


public class AsignarTicketComando implements Comando {
    private Ticket ticket;
    private Agente agente;

    public AsignarTicketComando(Ticket ticket, Agente agente) {
        this.ticket = ticket;
        this.agente = agente;
    }

    @Override
    public void ejecutar() {
        System.out.println("[COMMAND] Ejecutando AsignarTicketComando: Asignando Ticket #" + ticket.getId()
                + " al agente " + agente.getNombre());
        // 1. PATRÓN MEMENTO: Se guarda instantánea antes de alterar el estado
        ticket.guardarSnapshot();
        // 2. PATRÓN STATE: Se delega la asignación al estado actual (Abierto -> En Proceso)
        ticket.asignar(agente);
    }

    @Override
    public void deshacer() {
        System.out.println("[COMMAND] Deshaciendo AsignarTicketComando: Revirtiendo asignación de Ticket #" + ticket.getId());
        if (agente != null) {
            agente.removerTicket(ticket);
        }
        // PATRÓN MEMENTO: Se restaura el estado previo
        ticket.deshacerCambio();
    }
}
