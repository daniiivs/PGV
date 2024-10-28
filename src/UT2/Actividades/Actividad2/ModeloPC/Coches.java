package UT2.Actividades.Actividad2.ModeloPC;

import java.util.Random;

public class Coches extends Thread {
    Plazas plazas;
    int vueltas = 2;

    public Coches(Plazas plazas) {
        this.plazas = plazas;
    }

    public void run() {
        Random random = new Random();

        try {
            Thread.sleep(random.nextLong(1, 26) * 1000);
            for (int i = 0; i < vueltas; i++) {
                if (plazas.aparcar()) {
                    Thread.sleep(random.nextLong(16, 26) * 1000);
                    plazas.salir();
                    break;
                } else {
                    Thread.sleep(random.nextLong(5, 11) * 1000);
                    if (i == vueltas - 1) {
                        System.out.println("El coche con matrícula " + Thread.currentThread().getName() + " no ha podido aparcar, así que se va a ir...");
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
