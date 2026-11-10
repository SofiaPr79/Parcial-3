package com.uniquindio.parcial3;
import java.util.Stack;


public class CuidadorMementos {
    private Stack<PedidoMemento> historial;

    public CuidadorMementos() {
        this.historial = new Stack<PedidoMemento>();
    }

    public void guardar(PedidoMemento memento) {
        historial.push(memento);
    }

    public PedidoMemento recuperarUltimo() {
        if (!historial.isEmpty()) {
            return historial.pop();
        }
        return null;
    }

    public boolean estaVacio() {
        return historial.isEmpty();
    }
}
