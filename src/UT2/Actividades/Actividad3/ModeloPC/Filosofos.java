package UT2.Actividades.Actividad3.ModeloPC;

import java.util.Random;

public class Filosofos extends Thread {
    Palillos izquierdo;
    Palillos derecho;
    Random random = new Random();

    public void setIzquierdo(Palillos izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(Palillos derecho) {
        this.derecho = derecho;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextLong(5, 16) * 1000);
                if (comer()) {
                    Thread.sleep(random.nextLong(15, 21) * 1000);
                    terminar();
                    Thread.sleep(random.nextLong(5, 11) * 1000);
                } else {
                    Thread.sleep(random.nextLong(5, 16) * 1000);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean comer() {
        if (!izquierdo.isOcupado() && !derecho.isOcupado()) {
            izquierdo.setOcupado(true);
            derecho.setOcupado(true);
            System.out.println(Thread.currentThread().getName() + " ha empezado a comer...");
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " no puede comer, alguno de sus palillos está ocupado.");
            return false;
        }
    }

    public synchronized void terminar() {
        izquierdo.setOcupado(false);
        derecho.setOcupado(false);
        System.out.println(Thread.currentThread().getName() + " ha terminado de comer...");
    }
}
