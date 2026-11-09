package com.uniquindio.parcial3;

public interface Mediador {
    Ticket reportarProblema(Cliente cliente, String descrpicion, TipoTicket tipo, Prioridad prioridad);
    void atenderTicket(Agente agente, Ticket ticket);
    void resolverTicket(Agente agente, Ticket ticket);
    void cerrarTicket(Ticket ticket);
    void cambiarPrioridad(Ticket ticket, Prioridad nuevaPrioridad);
    void deshacerUltimaAccion();
}
