package com.parcial;

public class Dragon extends Criatura implements Volador {

    private final String escamas;
    private Arma arma; // composición: el dragón puede tener un arma opcional

    public Dragon(String nombre, int salud, int fuerza, String escamas) {
        super(nombre, salud, fuerza);
        this.escamas = escamas;
    }

    /** Equipa un arma mediante composición. */
    public void equiparArma(Arma arma) {
        this.arma = arma;
        System.out.println(nombre + " equipó: " + arma.getNombre());
    }

    /** Desequipa el arma actual. */
    public void desequiparArma() {
        System.out.println(nombre + " desequipó: "
                + (arma != null ? arma.getNombre() : "ninguna"));
        this.arma = null;
    }

    @Override
    public void atacar(Criatura objetivo) {
        int daño = fuerza * 2;
        System.out.println(nombre + " usa ALIENTO DE FUEGO sobre "
                + objetivo.getNombre() + " causando " + daño + " puntos de daño!");
        objetivo.defender(daño);

        if (arma != null) {
            arma.atacarConArma(objetivo);
        }
    }

    /** Las escamas reducen el daño recibido en un 20%. */
    @Override
    public void defender(int daño) {
        int dañoReal = (int) (daño * 0.8);
        salud -= dañoReal;
        System.out.println("🛡️  " + nombre + " recibe " + dañoReal
                + " de daño (escamas absorbieron algo). Salud: " + Math.max(salud, 0));
    }

    @Override
    public void volar() {
        System.out.println("🦅 " + nombre + " abre sus alas y se eleva al cielo!");
    }

    @Override
    public void aterrizar() {
        System.out.println("🦅 " + nombre + " aterriza con estrépito.");
    }

    public String getEscamas() { return escamas; }
    public Arma   getArma()    { return arma;    }

}
