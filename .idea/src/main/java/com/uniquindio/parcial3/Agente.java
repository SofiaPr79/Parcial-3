package com.uniquindio.parcial3;

public class Agente extends Participante{
    private int nivel;
    private TipoTicket especialidad;
    private List<Ticket> ticketsAsignados;

    public Agente(String nombre, Mediador mediador, int nivel, TipoTicket especialidad) {
        super(nombre, mediador);
        this.nivel = nivel;
        this.especialidad = especialidad;
        this.ticketsAsignados = new ArrayList<Ticket>();
    }

    public int getNivel() {
        return nivel;
    }

    public TipoTicket getEspecialidad() {
        return especialidad;
    }

    public List<Ticket> getTicketsAsignados() {
        return ticketsAsignados;
    }

    public int getCantidadTickets() {
        return ticketsAsignados.size();
    }

    public void agregarTicket(Ticket ticket) {
        ticketsAsignados.add(ticket);
    }

    public void removerTicket(Ticket ticket) {
        ticketsAsignados.remove(ticket);
    }

    // Coordina la atención técnica del ticket a través del mediador
    public void atenderTicket(Ticket ticket) {
        mediador.atenderTicket(this, ticket);
    }

    // Solicita la resolución del ticket a través del mediador
    public void resolverTicket(Ticket ticket) {
        mediador.resolverTicket(this, ticket);
    }
}

