package com.parcial;

public class Arma {

    private final String nombre;
    private final int    dañoAdicional;

    public Arma(String nombre, int dañoAdicional) {
        this.nombre        = nombre;
        this.dañoAdicional = dañoAdicional;
    }

    public void atacarConArma(Criatura objetivo) {
        System.out.println(nombre + " golpea a " + objetivo.getNombre()
                + " causando " + dañoAdicional + " puntos de daño extra.");
        objetivo.defender(dañoAdicional);
    }

    /** Retorna el daño adicional que proporciona el arma. */
    public int getDañoAdicional() {
        return dañoAdicional;
    }

    public String getNombre() {
        return nombre;
    }
    
}