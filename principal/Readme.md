#  Sistema de Batalla de Criaturas
### Parcial II – Programación II | G411

---

##  Descripción General

Este proyecto implementa un sistema de juego de batallas entre criaturas usando los principios fundamentales de la Programación Orientada a Objetos: **herencia**, **polimorfismo**, **clases abstractas**, **interfaces** y **composición**.

Las criaturas disponibles son **Dragón**, **Guerrero** y **Mago**, cada una con atributos, habilidades y mecánicas de combate únicas. La batalla se simula por turnos hasta que una criatura muere.

---

```
---

---

##  Decisiones de Diseño

### 1. Clase Abstracta `Criatura`

**Decisión:** Usar una clase abstracta en lugar de una interfaz como base.

**Justificación:** Todas las criaturas comparten atributos de estado (`nombre`, `salud`, `fuerza`) y un comportamiento concreto (`estaViva()`). Una interfaz no puede almacenar estado, por lo que la clase abstracta es la herramienta correcta. Los métodos `atacar()` y `defender()` se declaran abstractos porque cada criatura tiene una lógica de combate radicalmente distinta: no tiene sentido forzar una implementación común.

```java
// estaViva() es concreto: la regla "salud > 0" aplica igual para todos
public boolean estaViva() {
    return salud > 0;
}

// atacar() es abstracto: cada criatura combate de forma distinta
public abstract void atacar(Criatura objetivo);
```

---

### 2. Interfaces `Volador` y `Magico`

**Decisión:** Modelar las habilidades especiales como interfaces, no como herencia adicional.

**Justificación:** Volar y usar magia son *capacidades opcionales*, no características esenciales de ser una criatura. Si se usara herencia (`CriaturaVoladora`, `CriaturaMagica`), habría que crear subclases para cada combinación posible (ej. un dragón mágico necesitaría herencia múltiple, que Java no soporta). Las interfaces resuelven esto de forma limpia: una clase puede implementar tantas como necesite.

```java
// Dragon tiene vuelo como habilidad adicional, no como herencia obligatoria
public class Dragon extends Criatura implements Volador { ... }

// Mago tiene magia como habilidad adicional
public class Mago extends Criatura implements Magico { ... }
```

---

### 3. Composición con `Arma`

**Decisión:** Las criaturas *tienen* un arma (composición) en lugar de *ser* un tipo de criatura armada (herencia).

**Justificación:** El principio de diseño "favorecer composición sobre herencia" aplica perfectamente aquí. Un arma es un objeto independiente que puede equiparse, desequiparse o cambiarse en tiempo de ejecución sin modificar la jerarquía de clases. Si se usara herencia (`DragonConArma`), el código explotaría en subclases para cada combinación de criatura + arma.

```java
// El arma es un atributo opcional: puede ser null si la criatura no tiene
private Arma arma;  // composición: Dragon "tiene un" Arma

// Se puede cambiar en cualquier momento
public void equiparArma(Arma arma)  { this.arma = arma; }
public void desequiparArma()        { this.arma = null;  }
```

---

### 4. Polimorfismo en `simularBatalla()`

**Decisión:** El método recibe dos objetos de tipo `Criatura`, no tipos concretos.

**Justificación:** Gracias al polimorfismo, `simularBatalla()` no necesita saber si está manejando un Dragón, un Guerrero o un Mago. Llama a `atacar()` y la JVM despacha automáticamente al método correcto de cada subclase en tiempo de ejecución. Esto hace que el método sea extensible: si en el futuro se añade una criatura `Elfo`, funciona sin modificar `simularBatalla()`.

```java
// Un solo método funciona para cualquier par de criaturas
public static void simularBatalla(Criatura criatura1, Criatura criatura2) {
    while (criatura1.estaViva() && criatura2.estaViva()) {
        criatura1.atacar(criatura2);  // ← polimorfismo: se ejecuta el atacar() de cada subclase
        criatura2.atacar(criatura1);
    }
}
```

---

### 5. Mecánica de Defensa Diferenciada

**Decisión:** Cada criatura implementa `defender()` con su propia reducción de daño.

**Justificación:** Hace el sistema más balanceado e interesante. El dragón es más resistente gracias a sus escamas, el guerrero tiene protección parcial de su armadura, y el mago, siendo frágil pero poderoso, recibe el daño completo. Esto crea dinámicas estratégicas reales.

| Criatura | Reducción de daño | Justificación narrativa |
|---|---|---|
| Dragón | 20% menos daño | Escamas como armadura natural |
| Guerrero | 15% menos daño | Armadura de metal |
| Mago | Sin reducción | Solo porta túnica, sin protección física |

---

### 6. Verificación de Interfaces con `instanceof`

**Decisión:** En `simularBatalla()` se usa `instanceof` para invocar habilidades especiales al inicio de la batalla.

**Justificación:** No se puede llamar `volar()` directamente desde una variable de tipo `Criatura` porque esa clase no define ese método. `instanceof` permite verificar en tiempo de ejecución si la criatura implementa la interfaz, y hacer el cast con seguridad. Es el patrón correcto cuando se trabaja con interfaces opcionales.

```java
if (criatura1 instanceof Volador) {
    ((Volador) criatura1).volar();  // cast seguro tras verificar
}
```

---

##  Pruebas Unitarias (JUnit 5)

Las pruebas están en `BatallaTest.java` y cubren los siguientes casos:

| Prueba | Qué verifica |
|---|---|
| `criaturaEstaVivaConSaludPositiva` | `estaViva()` retorna `true` al inicio |
| `criaturaMuereConSaludCero` | `estaViva()` retorna `false` tras daño letal |
| `armaRetornaDañoAdicionalCorrecto` | `getDañoAdicional()` retorna el valor correcto |
| `atacarConArmaReduceSalud` | El arma efectivamente reduce la salud del objetivo |
| `dragonAtacaConDoblesFuerza` | El dragón hace `fuerza * 2` de daño |
| `dragonReduceDañoConEscamas` | El dragón recibe solo el 80% del daño |
| `dragonImplementaVolador` | `Dragon` es instancia de `Volador` |
| `dragonEquipaYDesequipaArma` | El arma se asigna y se limpia correctamente |
| `guerreroReduceDañoPorArmadura` | El guerrero recibe solo el 85% del daño |
| `guerreroConArmaInfligeMasDaño` | El guerrero con arma inflige daño base + arma |
| `magoImplementaMagico` | `Mago` es instancia de `Magico` |
| `magoAprendeHechizo` | El repertorio del mago se actualiza correctamente |
| `magoRecibeDañoCompleto` | El mago recibe el 100% del daño |
| `batallaTerminaConUnGanador` | Al final de una batalla hay al menos un derrotado |

### Ejecutar pruebas

```bash
mvn test
```

---

##  Cómo Ejecutar el Proyecto

### Prerrequisitos

- Java 17 o superior
- Maven 3.8 o superior

### Compilar y ejecutar

```bash
# Compilar el proyecto
mvn compile

# Ejecutar las pruebas unitarias
mvn test

# Empaquetar en JAR
mvn package

# Ejecutar la simulación de batallas
java -jar target/batalla-criaturas-1.0-SNAPSHOT.jar
```

---

##  Dependencias (`pom.xml`)

```xml
<!-- JUnit 5 para pruebas unitarias -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

---

##  Integrantes

 Juan Manuel Daza  
 Camila Rodriguez 

---

##  Flujo de Git Recomendado

```bash
# Crear rama por funcionalidad
git checkout -b feature/clase-dragon

# Commit descriptivo
git commit -m "feat: implementar clase Dragon con interfaz Volador"

# Push y merge request
git push origin feature/clase-dragon
# → abrir Merge Request → revisión del compañero → aprobar → merge a main
```

Ramas sugeridas: `feature/criatura-abstracta`, `feature/interfaces`, `feature/composicion-arma`, `feature/simulacion-batalla`, `feature/pruebas-unitarias`
