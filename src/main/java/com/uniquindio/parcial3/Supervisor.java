package com.uniquindio.parcial3;

public class Supervisor extends Participante implements Observador {
    public Supervisor(String nombre, Mediador mediador) {
        super(nombre, mediador);
    }

    // PATRÓN OBSERVER: Notificación recibida ante cualquier evento en los tickets
    @Override
    public void actualizar(Ticket ticket, String mensaje) {
        System.out.println("[OBSERVER] -> [Supervisor " + getNombre() + "] Auditoría Ticket #" + ticket.getId() + ": " + mensaje);
    }
}
