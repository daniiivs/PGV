package UT2.Actividades.Actividad4.ModeloPC;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Clientes extends Thread {
    Random random = new Random();
    Semaphore semaphore;
    Productos estanteria;
    final int limiteCompra = 2;
    int compraTotal;
    int comprasExitosas;

    public Clientes(Productos estanteria, Semaphore semaphore) {
        this.estanteria = estanteria;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextLong(5, 16) * 1000);
                compraTotal = random.nextInt(1, limiteCompra + 1);
                semaphore.acquire();
                comprasExitosas = 0;
                for (int i = 0; i < compraTotal; i++) {
                    if (estanteria.comprar()) {
                        comprasExitosas++;
                    } else {
                        break;
                    }
                }
                if (comprasExitosas > 0) {
                    System.out.println("El " + Thread.currentThread().getName() + " se ha llevado " + comprasExitosas + " producto(s).");
                } else {
                    System.out.println("El " + Thread.currentThread().getName() + " no se ha llevado ningún producto porque no quedan.");
                }
                comprasExitosas = 0;
                estanteria.imprimirEstanteria();
                semaphore.release();
                Thread.sleep(random.nextLong(3, 8) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
