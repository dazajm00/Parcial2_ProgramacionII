package com.parcial;

public class Main {

    public static void simularBatalla(Criatura criatura1, Criatura criatura2) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║    BATALLA: " + criatura1.getNombre() + " vs " + criatura2.getNombre());
        System.out.println("╚══════════════════════════════════════════╝\n");

        
        if (criatura1 instanceof Volador) {
            ((Volador) criatura1).volar();
        }
        if (criatura2 instanceof Volador) {
            ((Volador) criatura2).volar();
        }

        int turno = 1;

        while (criatura1.estaViva() && criatura2.estaViva()) {
            System.out.println("\n--- Turno " + turno + " ---");

            
            if (criatura1.estaViva()) {
                criatura1.atacar(criatura2);
            }

            
            if (criatura2.estaViva()) {
                criatura2.atacar(criatura1);
            }

            turno++;
        }

        
        if (criatura1 instanceof Volador) ((Volador) criatura1).aterrizar();
        if (criatura2 instanceof Volador) ((Volador) criatura2).aterrizar();

        
        System.out.println("\n════════════════════════════════════════════");
        if (!criatura1.estaViva() && !criatura2.estaViva()) {
            System.out.println(" ¡EMPATE! Ambas criaturas cayeron en combate.");
        } else if (criatura1.estaViva()) {
            System.out.println(" ¡" + criatura1.getNombre() + " GANÓ la batalla con "
                    + criatura1.getSalud() + " de salud restante!");
        } else {
            System.out.println(" ¡" + criatura2.getNombre() + " GANÓ la batalla con "
                    + criatura2.getSalud() + " de salud restante!");
        }
        System.out.println("════════════════════════════════════════════\n");
    }

    public static void main(String[] args) {

        // -- Crear las armas -----------------------------------------
        Arma garrasDeFuego  = new Arma("Garras de Fuego", 15);
        Arma espadaLegendaria = new Arma("Espada Legendaria", 20);
        Arma bastonArcano   = new Arma("Bastón Arcano", 10);

        // -- Crear las criaturas -----------------------------------------
        Dragon dragon = new Dragon("Ignis", 120, 30, "Escamas Rojas");
        dragon.equiparArma(garrasDeFuego);

        Guerrero guerrero = new Guerrero("Thorin", 150, 25, "Armadura de Placas");
        guerrero.equiparArma(espadaLegendaria);

        Mago mago = new Mago("Zara", 80, 35, "Bola de Fuego, Congelación");
        mago.equiparArma(bastonArcano);
        

        
        System.out.println("\n\n  TORNEO DE CRIATURAS  \n");

    
        simularBatalla(dragon, guerrero);

        
        Dragon dragon2   = new Dragon("Ignis II", 120, 30, "Escamas Negras");
        Mago   mago2     = new Mago("Zara II", 80, 35, "Rayo, Tormenta");
        dragon2.equiparArma(new Arma("Colmillos de Hielo", 12));
        mago2.equiparArma(new Arma("Orbe de Cristal", 8));

        // Batalla 2: Dragón vs Mago
        simularBatalla(dragon2, mago2);

        // Batalla 3: Guerrero vs Mago
        Guerrero guerrero2 = new Guerrero("Manuel", 140, 22, "Cota de Malla");
        Mago     mago3     = new Mago("Aldric", 90, 30, "Fuego Oscuro");
        guerrero2.equiparArma(new Arma("Hacha de Guerra", 18));
        simularBatalla(guerrero2, mago3);
    }
}
