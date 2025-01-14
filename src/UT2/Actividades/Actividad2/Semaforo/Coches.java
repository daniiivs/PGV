package UT2.Actividades.Actividad2.Semaforo;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Coches extends Thread {
    Plazas plazas;
    int vueltas = 2;

    final Semaphore semaforo;

    public Coches(Plazas plazas, Semaphore semaforo) {
        this.plazas = plazas;
        this.semaforo = semaforo;
    }

    public void run() {
        Random random = new Random();

        try {
            Thread.sleep(random.nextLong(1, 26) * 1000);
            for (int i = 0; i < vueltas; i++) {
                semaforo.acquire();
                if (plazas.aparcar()) {
                    semaforo.release();
                    Thread.sleep(random.nextLong(16, 26) * 1000);
                    semaforo.acquire();
                    plazas.salir();
                    semaforo.release();
                    break;
                } else {
                    semaforo.release();
                    if (i == vueltas - 1) {
                        System.out.println("El coche con matrícula " + Thread.currentThread().getName() + " no ha podido aparcar, así que se va a ir...");
                    }
                    Thread.sleep(random.nextLong(5, 11) * 1000);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
