package UT2.Actividades.Integradora;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase aviones, extiende de Thread
public class Aviones extends Thread {
    Semaphore semaforo; // El semáforo que controla el acceso a métodos
    Pistas pista; // El recurso compartido
    Random random = new Random();

    // Constructor de Aviones
    public Aviones(Pistas pista, Semaphore semaforo) {
        this.pista = pista;
        this.semaforo = semaforo;
    }

    public void run() {
        do { // Bucle do-while(true) que no termina hasta que entran en pista y despegan
            try {
                Thread.sleep(random.nextLong(5, 51) * 1000);
                semaforo.acquire();
                if (pista.tomarPista(this)) { // Si el avión consigue tomar pista
                    System.out.println(Thread.currentThread().getName() + " ha entrado en pista, posición: " + pista.getPosicionAvion(this));
                    semaforo.release();
                    intentarSalir(); // Bucle infinito para intentar salir, no saldrá hasta que despegue
                    break; // Salimos del bucle infinito
                } else {
                    System.out.println(Thread.currentThread().getName() + " no ha podido ponerse en cola...");
                    semaforo.release();
                    Thread.sleep(random.nextLong(5, 26) * 1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }

    // Bucle para intentar despegar, solo podrá si es el primero en pista
    public void intentarSalir() {
        try {
            do {
                Thread.sleep(random.nextLong(2, 8) * 1000);
                semaforo.acquire();
                if (pista.despegar(this)) { // Comprobamos si consigue despegar
                    semaforo.release();
                    break;
                } else {
                    semaforo.release();
                    Thread.sleep(random.nextLong(5, 11) * 1000);
                }
            } while (true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
