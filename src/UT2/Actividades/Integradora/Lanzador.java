package UT2.Actividades.Integradora;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lanzador {
    public static void main(String[] args) {
        Random random = new Random();
        Pistas pista = new Pistas();
        Semaphore semaforo = new Semaphore(1);
        Aviones avion;
        final int avionesTotales = random.nextInt(pista.maximoAviones + 10, pista.maximoAviones + 31);

        for (int i = 0; i < avionesTotales; i++) {
            avion = new Aviones(pista, semaforo);
            avion.setName(Pistas.lineas[random.nextInt(Pistas.lineas.length)] + " " + (i + 1));
            avion.start();
        }
    }
}
