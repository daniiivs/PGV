package UT2.Actividades.Integradora;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Aviones extends Thread {
    Semaphore semaforo;
    Pistas pista;
    Random random = new Random();

    public Aviones(Pistas pista, Semaphore semaforo) {
        this.pista = pista;
        this.semaforo = semaforo;
    }

    public void run() {
        do {
            try {
                Thread.sleep(random.nextLong(5, 51) * 1000);
                semaforo.acquire();
                if (pista.tomarPista(this)) {
                    System.out.println(Thread.currentThread().getName() + " ha entrado en pista, posición: " + pista.getPosicionAvion(this));
                    semaforo.release();
                    intentarSalir();
                    break;
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

    public void intentarSalir() {
        try {
            do {
                Thread.sleep(random.nextLong(2, 8) * 1000);
                semaforo.acquire();
                if (pista.despegar(this)) {
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
