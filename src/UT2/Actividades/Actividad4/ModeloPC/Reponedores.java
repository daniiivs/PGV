package UT2.Actividades.Actividad4.ModeloPC;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Reponedores extends Thread {
    Random random = new Random();
    Semaphore semaphore;
    Productos estanteria;
    final int limiteRepuesto = 3;
    int repuestoTotal;
    int repuestosExitosos;

    public Reponedores(Productos estanteria, Semaphore semaphore) {
        this.estanteria = estanteria;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextLong(10, 21) * 1000);
                repuestoTotal = random.nextInt(1, limiteRepuesto + 1);
                semaphore.acquire();
                repuestosExitosos = 0;
                for (int i = 0; i < repuestoTotal; i++) {
                    if (estanteria.reponer()) {
                        repuestosExitosos++;
                    } else {
                        break;
                    }
                }
                if (repuestosExitosos > 0) {
                    System.out.println("El " + Thread.currentThread().getName() + " ha repuesto " + repuestosExitosos + " unidad(es) del producto.");
                } else {
                    System.out.println("El " + Thread.currentThread().getName() + " ha intentado reponer el producto, pero no hay estantes libres.");
                }
                repuestosExitosos = 0;
                estanteria.imprimirEstanteria();
                semaphore.release();
                Thread.sleep(random.nextLong(5, 11) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
