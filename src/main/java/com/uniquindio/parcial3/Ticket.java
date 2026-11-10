package com.uniquindio.parcial3;

import java.util.ArrayList;
import java.util.List;
public class Ticket {
    private int id;
    private String descripcion;
    private TipoTicket tipo;
    private Prioridad prioridad;
    private Cliente cliente;
    private Agente agenteAsignado;

    // PATRÓN STATE: Referencia al estado actual polimórfico
    private EstadoTicket estado;

    // PATRÓN OBSERVER: Lista de observadores interesados en los cambios de este ticket
    private List<Observador> observadores;

    // PATRÓN MEMENTO: Cuidador que guarda las instantáneas previas
    private CuidadorMementos cuidador;

    public Ticket(int id, String descripcion, TipoTicket tipo, Prioridad prioridad, Cliente cliente) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.cliente = cliente;
        this.agenteAsignado = null;
        // Estado inicial al crearse
        this.estado = new EstadoAbierto();
        this.observadores = new ArrayList<Observador>();
        this.cuidador = new CuidadorMementos();
    }

    // ==========================================
    // MÉTODOS DEL PATRÓN STATE (Delegación)
    // ==========================================
    public void asignar(Agente agente) {
        estado.asignar(this, agente);
    }

    public void resolver() {
        estado.resolver(this);
    }

    public void cerrar() {
        estado.cerrar(this);
    }

    public void reabrir() {
        estado.reabrir(this);
    }

    public void cambiarEstado(EstadoTicket nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("[STATE] Ticket #" + id + ": Estado cambió a -> " + nuevoEstado.getNombre());
        // Se conecta directamente con Observer: el cambio de estado dispara la notificación
        notificarObservadores("El estado ha cambiado a: " + nuevoEstado.getNombre());
    }

    // ==========================================
    // MÉTODOS DEL PATRÓN OBSERVER (Sujeto)
    // ==========================================
    public void agregarObservador(Observador observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void quitarObservador(Observador observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(this, mensaje);
        }
    }

    // ==========================================
    // MÉTODOS DEL PATRÓN MEMENTO (Originator)
    // ==========================================
    public TicketMemento crearMemento() {
        return new TicketMemento(this.estado, this.agenteAsignado, this.prioridad);
    }

    public void restaurarDesdeMemento(TicketMemento memento) {
        if (memento != null) {
            this.estado = memento.getEstado();
            this.agenteAsignado = memento.getAgenteAsignado();
            this.prioridad = memento.getPrioridad();
            System.out.println("[MEMENTO] Ticket #" + id + " restaurado al estado previo: " + estado.getNombre()
                    + ", Agente: " + (agenteAsignado != null ? agenteAsignado.getNombre() : "Ninguno")
                    + ", Prioridad: " + prioridad);
            notificarObservadores("Se revirtió el último cambio. Estado actual: " + estado.getNombre()
                    + ", Prioridad: " + prioridad);
        }
    }

    // Ayudante para guardar foto antes de una acción de Command
    public void guardarSnapshot() {
        System.out.println("[MEMENTO] Se guardó una foto del Ticket #" + id);
        cuidador.guardar(crearMemento());
    }

    // Ayudante para restaurar la foto más reciente
    public void deshacerCambio() {
        TicketMemento memento = cuidador.recuperarUltimo();
        if (memento != null) {
            restaurarDesdeMemento(memento);
        } else {
            System.out.println("[MEMENTO] No existen fotos previas guardadas para el Ticket #" + id);
        }
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoTicket getTipo() {
        return tipo;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Agente getAgenteAsignado() {
        return agenteAsignado;
    }

    public void setAgenteAsignado(Agente agenteAsignado) {
        this.agenteAsignado = agenteAsignado;
    }

    public EstadoTicket getEstado() {
        return estado;
    }
}

