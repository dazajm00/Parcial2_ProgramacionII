package com.parcial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Batalla")
public class BatallaTest {

    private Dragon dragon;
    private Guerrero guerrero;
    private Mago mago;
    private Arma armaBasica;

    @BeforeEach
    public void setUp() {
        dragon = new Dragon("testDragon", 150, 30, "Escamas de fuego");
        guerrero = new Guerrero("testGuerrero", 120, 40, "Armadura Simple");
        mago = new Mago("testmago", 100, 20, "Bola de fuego");
        armaBasica = new Arma("Espada Básica", 10);
    }

    @Test
    @DisplayName("Criatura comienza viva si salud > 0")
    void criaturaEstaVivaConSaludPositiva() {
        assertTrue(dragon.estaViva(), "El dragon debería estar vivo con salud positiva.");
        assertTrue(guerrero.estaViva(), "El guerrero debería estar vivo con salud positiva.");
        assertTrue(mago.estaViva(), "El mago debería estar vivo con salud positiva.");
    }

    @Test
    @DisplayName("Criatura muere si salud <= 0")
    void criaturaMuereConSaludCero() {

        dragon.defender(500); // Daño excesivo para asegurar que muere
        assertFalse(dragon.estaViva(), "El dragon debería estar muerto con salud <= 0.");
    }

    @Test
    @DisplayName("Arma retoma el daño adicional correcto")
    void armaRetomaDañoAdicionalCorrecto() {
        asserEquals(10, armaBasica.getDañoAdicional(), "El daño adicional del arma debería ser 10.");
    }

    @Test
    @DisplayName("atacarConArma reduce la salud del objetivo")
    void atacarConArmaReduceSalud() {
        
         int saludInicial = guerrero.getSalud();
         armaBasica.atacarconArma(guerrero);

         asserTrue(guerrero.getSalud() < saludInicial);
    }

    @Test
    @DisplayName("Dragon ataca con fuerza * 2 (aliento de fuego)")
    void dragonAtacaConDoblesFuerza() {
        int saludInicialMago = mago.getSalud();
        dragon.atacar(mago);

        asserEquals (saludInicialMago - 40, mago.getSalud());

    }

    @Test
    @DisplayName("Dragon reduce daño recibido en 20% (escamas)")
    void dragonReduceDañoConEscamas() {
        dragon.defender(100);

        asserEquals(20, dragon.getSalud());

    }

    @Test
    @DisplayName("Dragon implementa interfaz Volador")
    void dragonImplementaVolador() {
        assertInstanceOf(Volador.class, dragon);
    }

    @Test
    @DisplayName("Dragon puede equipar y desequipar arma")
    void dragonEquipaYDesaquipaArma(){
        asserNull(dragon.getArma());
        dragon.equiparArma(armaBasica);
        asserNotNull(dragon.getArma());
        dragon.desequiparArma();
        asserNotNull(dragon.getArma());
    }

    @Test
    @DisplayName("Guerrero reduce el daño en 15% por armadura")
    void guerreroReduceDañoPorArmadura() {
        guerrero.defender(100);
        // Reduce 100 * 0.85 = 85
        asserEquals(15, guerrero.getSalud());
    }

    @Test
    @DisplayName("Guerrero con arma inflige daño base + daño del arma")
    void guerreroConArmaInfligeMasDaño() {
        guerrero.equiparArma(armaBasica);
        int saludInicialMago = mago.getSalud();
        guerrero.atacar(mago);

        asserEquals(SaludInicialMago - 15 - 10, mago.getSalud());
        
    }

    @Test
    @DisplayName("Mago implementa interfaz Magico")
    void magoImplementaMagico() {
        assertInstanceOf(Magico.class, mago);

    }

    @Test
    @DisplayName("Mago aprende hechizo y actualiza su reportorio")
    void magoAprendeHechizo() {
        String hechizosIniciales = mago.getHechizos();
        mago.aprenderHechizo();
        asserNotEquals(hechizosIniciales, mago.getHechizos());
        asserTrue(mago.getHechizos().contains("Rayo Arcano"));
    }

    @Test
    @DisplayName ("Mago recibe daño completo (sin armadura)")
    void magoRecibeDañoCompleto() {
        mago.defender(30);
        asserEquals(50, mago.getSalud());
    }

    @Test
    @DisplayName("simularBatalla termina con exactamente un ganador")
    void batallaTerminaConUnGanador() {

        Dragon d = new Dragon ("D", 30, 20, "Roja");
        Mago m = new Mago ("M", 30, 25, "Fuego");

        Main.simularBatalla(d, m);

        asserFalse(d.estaViva() && m.estaViva(),
        "Ambas criaturas no deberían estar vivas al final de la batalla.");
    }

}
