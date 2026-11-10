package com.uniquindio.parcial3;

public class Cliente {

        public Cliente(String nombre, Mediador mediador) {
            super(nombre, mediador);
        }

        // Delegación al mediador para iniciar un reporte
        public Ticket reportarProblema(String descripcion, TipoTicket tipo, Prioridad prioridad) {
            return mediador.reportarProblema(this, descripcion, tipo, prioridad);
        }

        // PATRÓN OBSERVER: Método que se ejecuta cuando el ticket observado cambia
        @Override
        public void actualizar(Ticket ticket, String mensaje) {
            System.out.println("[OBSERVER] -> [Cliente " + getNombre() + "] Ticket #" + ticket.getId() + ": " + mensaje);
        }
    }


}
