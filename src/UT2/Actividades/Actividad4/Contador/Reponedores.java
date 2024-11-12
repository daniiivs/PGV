package UT2.Actividades.Actividad4.Contador;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Reponedores extends Thread {
    Random random = new Random();
    Semaphore semaphore;
    Productos productos;
    final int limiteRepuesto = 3;
    int repuestoTotal;
    int repuestosExitosos;

    public Reponedores(Productos productos, Semaphore semaphore) {
        this.productos = productos;
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
                    if (productos.reponer()) {
                        repuestosExitosos++;
                    } else {
                        break;
                    }
                }
                if (repuestosExitosos > 0) {
                    System.out.println("El " + Thread.currentThread().getName() + " ha repuesto " + repuestosExitosos + " unidad(es) del producto.");
                    productos.imprimirProductos();
                } else {
                    System.out.println("El " + Thread.currentThread().getName() + " ha intentado reponer el producto, pero no hay estantes libres.");
                }
                repuestosExitosos = 0;
                semaphore.release();
                Thread.sleep(random.nextLong(5, 11) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
