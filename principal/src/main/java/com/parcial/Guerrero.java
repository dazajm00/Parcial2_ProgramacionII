package com.parcial;

public class Guerrero extends Criatura {

    private final String tipoArmadura;
    private Arma arma; // composición: arma principal del guerrero

    public Guerrero(String nombre, int salud, int fuerza, String tipoArmadura) {
        super(nombre, salud, fuerza);
        this.tipoArmadura = tipoArmadura;
    }

    public void equiparArma(Arma arma) {
        this.arma = arma;
        System.out.println(nombre + " empuña: " + arma.getNombre());
    }

    public void desequiparArma() {
        System.out.println(nombre + " guarda: "
                + (arma != null ? arma.getNombre() : "ninguna"));
        this.arma = null;
    }

    /** Ataca con su fuerza base; el arma añade daño extra si está equipada. */
    @Override
    public void atacar(Criatura objetivo) {
        System.out.println("⚔️  " + nombre + " ataca a " + objetivo.getNombre()
                + " con su espada causando " + fuerza + " puntos de daño!");
        objetivo.defender(fuerza);

        if (arma != null) {
            arma.atacarConArma(objetivo);
        }
    }

    /** La armadura absorbe el 15% del daño recibido. */
    @Override
    public void defender(int daño) {
        int dañoReal = (int) (daño * 0.85);
        salud -= dañoReal;
        System.out.println("🛡️  " + nombre + " bloquea parte del golpe ("
                + tipoArmadura + "). Recibe " + dañoReal
                + ". Salud: " + Math.max(salud, 0));
    }

    public String getTipoArmadura() { return tipoArmadura; }
    public Arma   getArma()         { return arma;         }
    
}