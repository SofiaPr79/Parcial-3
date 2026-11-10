package com.uniquindio.parcial3.command;
import com.uniquindio.parcial3.MesaDeAyuda;
import com.uniquindio.parcial3.Ticket;

public class CrearTicketComando implements Comando{
    private MesaDeAyuda mesa;
    private Ticket ticket;

    public CrearTicketComando(MesaDeAyuda mesa, Ticket ticket) {
   this.mesa=mesa;
   this.ticket=ticket;
    }

    @Override
    public void ejecutar() {
        System.out.println("[COMMAND] Ejecutando CrearTicketComando: Registrando Ticket #" + ticket.getId() + " en la mesa.");
        mesa.agregarTicket(ticket);
    }

    @Override
    public void deshacer() {
        System.out.println("[COMMAND] Deshaciendo CrearTicketComando: Eliminando Ticket #" + ticket.getId() + " de la mesa.");
        mesa.removerTicket(ticket);
    }
}
