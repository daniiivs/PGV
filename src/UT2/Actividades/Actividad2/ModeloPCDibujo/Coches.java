package UT2.Actividades.Actividad2.ModeloPCDibujo;

import java.util.Random;

public class Coches extends Thread {
    Plazas plazas;
    int plazaOcupada;
    int vueltas = 2;

    public Coches(Plazas plazas) {
        this.plazas = plazas;
    }

    public void run() {
        Random random = new Random();

        try {
            Thread.sleep(random.nextLong(1, 26) * 1000);
            for (int i = 0; i < vueltas; i++) {
                if ((plazaOcupada = plazas.aparcar()) != -1) {
                    Thread.sleep(random.nextLong(16, 26) * 1000);
                    plazas.salir(plazaOcupada);
                    break;
                } else {
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
