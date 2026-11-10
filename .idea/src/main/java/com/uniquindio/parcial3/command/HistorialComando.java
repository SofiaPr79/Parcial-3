package com.uniquindio.parcial3.command;

import java.util.Stack;

public class HistorialComando {
    private Stack<Comando> pilaComandos;

    public HistorialComando() {
        this.pilaComandos = new Stack<Comando>();
    }

    // Ejecuta el comando y lo apila en el historial
    public void ejecutar(Comando comando) {
        if (comando != null) {
            comando.ejecutar();
            pilaComandos.push(comando);
        }
    }

    // Deshace el último comando ejecutado sacándolo de la pila
    public void deshacerUltimo() {
        if (!pilaComandos.isEmpty()) {
            Comando comando = pilaComandos.pop();
            comando.deshacer();
        } else {
            System.out.println("[COMMAND] Historial vacío: No hay acciones recientes para deshacer.");
        }
    }

    public boolean tieneComandos() {
        return !pilaComandos.isEmpty();
    }
}
