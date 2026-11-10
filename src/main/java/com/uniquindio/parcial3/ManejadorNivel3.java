package com.uniquindio.parcial3;

public class ManejadorNivel3 extends ManejadorEscalamiento{
    @Override
    public int manejar(Ticket ticket) {
        System.out.println("[CHAIN] ManejadorNivel3: Prioridad ALTA o caso crítico -> Atendido por Soporte Especializado Nivel 3.");
        return 3;
    }
}
