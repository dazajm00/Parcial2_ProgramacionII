package com.parcial;

public class Mago extends Criatura implements Magico {

    private String hechizos;
    private Arma arma;

    public Mago(String nombre, int salud, int fuerza, String hechizos) {
        super(nombre, salud, fuerza);
        this.hechizos = hechizos;
    }

    public void equiparArma(Arma arma) {
        this.arma = arma;
        System.out.println(nombre + " ha equipado el arma: " + arma.getnombre());
    }

    public void desequiparArma() {
        System.out.println(nombre + " ha desequipado el arma: " + arma.getnombre());
        this.arma = null;
    }
     
    @Override
    public void atacar(Criatura Objetivo) {
        lanzarHechizo();
        int daño = fuerza;
        System.out.println(nombre + " lanza un hechizo de tipo: " + Objetivo.getNombre() 
        + " causando " + daño + " de daño.");

        Objetivo.defender(daño);

        if (arma != null) {
            arma.atacarconArma();
        }
    }

    @Override
    public void defender(int daño) {
        salud -= daño;
        System.out.println(nombre + " no tiene armadura. Recibe " + daño + " de daño. Salud:" 
            + Math.max(salud, 0));
    }

    @Override
    public void lanzarHechizo() {
        System.out.println(nombre + " lanza un hechizo poderoso: [" + hechizos + "]...");
    }

    @Override
    public void aprenderHechizo() {
        hechizos += " + Rayo arcano";
        System.out.println(nombre + " ha aprendido un nuevo hechizo: Reportorio: " + hechizos);
    }

    public String getHechizos() {
        return hechizos;
    }

    public Arma getArma() {
        return arma;
    }

}
