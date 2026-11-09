package com.uniquindio.parcial3;

public class MesaDeAyuda implements Mediador{
        private List<Agente> agentes;
        private List<Ticket> tickets;
        private Supervisor supervisor;

        // PATRÓN COMMAND: Invocador con historial de acciones reversibles
        private HistorialComandos historial;

        // PATRÓN CHAIN OF RESPONSIBILITY: Inicio de la cadena de validación y escalamiento
        private ManejadorEscalamiento cadenaEscalamiento;

        // PATRÓN STRATEGY: Política intercambiable para asignar técnicos
        private EstrategiaAsignacion estrategia;

        private int contadorTickets;

        public MesaDeAyuda() {
            this.agentes = new ArrayList<Agente>();
            this.tickets = new ArrayList<Ticket>();
            this.historial = new HistorialComandos();
            this.contadorTickets = 0;

            // Estrategia de asignación por defecto
            this.estrategia = new AsignacionPorEspecialidad();

            // Construcción de la cadena de responsabilidad: Validación -> Nivel 1 -> Nivel 2 -> Nivel 3
            ManejadorEscalamiento validacion = new ManejadorValidacion();
            ManejadorEscalamiento nivel1 = new ManejadorNivel1();
            ManejadorEscalamiento nivel2 = new ManejadorNivel2();
            ManejadorEscalamiento nivel3 = new ManejadorNivel3();

            validacion.setSiguiente(nivel1);
            nivel1.setSiguiente(nivel2);
            nivel2.setSiguiente(nivel3);

            this.cadenaEscalamiento = validacion;
        }

        // Registro de participantes en el sistema
        public void registrarAgente(Agente agente) {
            agentes.add(agente);
        }

        public void setSupervisor(Supervisor supervisor) {
            this.supervisor = supervisor;
        }

        // PATRÓN STRATEGY: Permite cambiar dinámicamente el algoritmo de asignación
        public void setEstrategia(EstrategiaAsignacion nuevaEstrategia) {
            System.out.println("[MEDIATOR] Cambiando estrategia de asignación a: " + nuevaEstrategia.getClass().getSimpleName());
            this.estrategia = nuevaEstrategia;
        }

        public void agregarTicket(Ticket ticket) {
            tickets.add(ticket);
        }

        public void removerTicket(Ticket ticket) {
            tickets.remove(ticket);
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        // =========================================================================
        // FLUJO PRINCIPAL: Coordinación de patrones al reportar un problema
        // =========================================================================
        @Override
        public Ticket reportarProblema(Cliente cliente, String descripcion, TipoTicket tipo, Prioridad prioridad) {
            System.out.println("[MEDIATOR] La mesa recibió el reporte de " + cliente.getNombre()
                    + " [Tipo: " + tipo + ", Prioridad: " + prioridad + "]");

            contadorTickets++;
            Ticket ticket = new Ticket(contadorTickets, descripcion, tipo, prioridad, cliente);

            // 1. PATRÓN OBSERVER: Suscribir al cliente y al supervisor como observadores
            ticket.agregarObservador(cliente);
            if (supervisor != null) {
                ticket.agregarObservador(supervisor);
            }

            // 2. PATRÓN COMMAND: La creación del ticket se encapsula en un comando y se ejecuta
            Comando comandoCrear = new CrearTicketComando(this, ticket);
            historial.ejecutar(comandoCrear);

            // 3. PATRÓN CHAIN OF RESPONSIBILITY: Valida y determina el nivel de soporte
            int nivelRequerido = cadenaEscalamiento.manejar(ticket);
            if (nivelRequerido == 0) {
                System.out.println("[MEDIATOR] El ticket #" + ticket.getId() + " fue rechazado por la cadena de validación. Fin del flujo.");
                return ticket;
            }

            // 4. Filtrar agentes pertenecientes al nivel determinado por la cadena
            List<Agente> agentesDelNivel = new ArrayList<Agente>();
            for (Agente a : agentes) {
                if (a.getNivel() == nivelRequerido) {
                    agentesDelNivel.add(a);
                }
            }

            if (agentesDelNivel.isEmpty()) {
                System.out.println("[MEDIATOR] Alerta: No se encontraron agentes registrados para Nivel " + nivelRequerido);
                return ticket;
            }

            // 5. PATRÓN STRATEGY: La estrategia seleccionada elige al agente adecuado
            Agente agenteElegido = estrategia.elegirAgente(agentesDelNivel, ticket);
            if (agenteElegido != null) {
                // 6. PATRÓN COMMAND (+ MEMENTO y STATE): Se asigna el ticket
                // AsignarTicketComando guarda foto (Memento) y pasa el estado de Abierto a En Proceso (State)
                Comando comandoAsignar = new AsignarTicketComando(ticket, agenteElegido);
                historial.ejecutar(comandoAsignar);
            }

            return ticket;
        }

        // =========================================================================
        // PATRÓN TEMPLATE METHOD: Coordinar la resolución técnica del ticket
        // =========================================================================
        @Override
        public void atenderTicket(Agente agente, Ticket ticket) {
            System.out.println("[MEDIATOR] Coordinando atención del Ticket #" + ticket.getId()
                    + " con el agente " + agente.getNombre());

            // Selecciona la plantilla adecuada según la categoría del ticket
            ProcesadorTicket procesador;
            if (ticket.getTipo() == TipoTicket.HARDWARE) {
                procesador = new ProcesadorHardware();
            } else if (ticket.getTipo() == TipoTicket.SOFTWARE) {
                procesador = new ProcesadorSoftware();
            } else {
                procesador = new ProcesadorRed();
            }

            // Ejecuta el método plantilla con sus pasos fijos y específicos
            procesador.procesar(ticket);
        }

        // =========================================================================
        // PATRÓN COMMAND (+ MEMENTO y STATE): Resolver un ticket
        // =========================================================================
        @Override
        public void resolverTicket(Agente agente, Ticket ticket) {
            System.out.println("[MEDIATOR] Agente " + agente.getNombre() + " solicita resolver el Ticket #" + ticket.getId());
            Comando comandoResolver = new ResolverTicketComando(ticket);
            historial.ejecutar(comandoResolver);
        }

        // =========================================================================
        // PATRÓN STATE: Cerrar el ticket (transición Resuelto -> Cerrado)
        // =========================================================================
        @Override
        public void cerrarTicket(Ticket ticket) {
            System.out.println("[MEDIATOR] Solicitando cierre formal del Ticket #" + ticket.getId());
            ticket.cerrar();
        }

        // =========================================================================
        // PATRÓN COMMAND (+ MEMENTO): Modificar prioridad con soporte para Deshacer
        // =========================================================================
        @Override
        public void cambiarPrioridad(Ticket ticket, Prioridad nuevaPrioridad) {
            System.out.println("[MEDIATOR] Solicitando cambio de prioridad en Ticket #" + ticket.getId() + " a " + nuevaPrioridad);
            Comando comandoPrioridad = new CambiarPrioridadComando(ticket, nuevaPrioridad);
            historial.ejecutar(comandoPrioridad);
        }

        // =========================================================================
        // PATRÓN COMMAND + MEMENTO: Revertir la última acción ejecutada
        // =========================================================================
        @Override
        public void deshacerUltimaAccion() {
            System.out.println("[MEDIATOR] Solicitando deshacer la última acción realizada...");
            historial.deshacerUltimo();
        }
    }
}
