package com.parcial;

public abstract class Criatura {

    protected String nombre;
    protected int salud;
    protected int fuerza;

    public Criatura(String nombre, int salud, int fuerza) {
        this.nombre = nombre;
        this.salud  = salud;
        this.fuerza = fuerza;
    }

    /** Ataca a la criatura objetivo. Cada subclase define su propia lógica. */
    public abstract void atacar(Criatura objetivo);

    /** Recibe daño. Cada subclase puede aplicar reducciones distintas. */
    public abstract void defender(int daño);

    /** Método concreto compartido: true si salud > 0. */
    public boolean estaViva() {
        return salud > 0;
    }

    public String getNombre() { return nombre; }
    public int    getSalud()  { return salud;  }
    public int    getFuerza() { return fuerza; }

    
}
