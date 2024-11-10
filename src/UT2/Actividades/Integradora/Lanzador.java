package UT2.Actividades.Integradora;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase Lanzador
public class Lanzador {
    public static void main(String[] args) {
        Random random = new Random();
        Pistas pista = new Pistas(); // El recurso compartido
        Semaphore semaforo = new Semaphore(1); // Semáforo con permit de 1, para que solo un hilo pueda acceder a los métodos simultáneamente
        Aviones avion;
        final int avionesTotales = random.nextInt(pista.maximoAviones + 10, pista.maximoAviones + 31); // Número total de aviones

        // Bucle para crear los aviones y lanzarlos
        for (int i = 0; i < avionesTotales; i++) {
            avion = new Aviones(pista, semaforo);
            avion.setName(Pistas.lineas[random.nextInt(Pistas.lineas.length)] + " - " + (i + 1));
            avion.start();
        }
    }
}
