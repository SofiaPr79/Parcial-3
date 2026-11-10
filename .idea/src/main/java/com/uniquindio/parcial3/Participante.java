package com.uniquindio.parcial3;

public abstract class Participante {
        private String nombre;
        private Mediador mediador;

        public Participante(String nombre, Mediador mediador) {
            this.nombre = nombre;
            this.mediador = mediador;
        }

        public String getNombre() {
            return nombre;
        }

        public Mediador getMediador() {
            return mediador;
        }

        public void setMediador(Mediador mediador) {
            this.mediador = mediador;
        }
    }
