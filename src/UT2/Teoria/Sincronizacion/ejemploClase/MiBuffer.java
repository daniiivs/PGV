package UT2.Teoria.Sincronizacion.ejemploClase;

public class MiBuffer {
    int contador = 0;

    public synchronized void inc() {
        contador++;
        System.out.println("Hola " + Thread.currentThread().getName() + ": " + contador);
    }

    public synchronized void dec() {
        contador--;
        System.out.println("Hola " + Thread.currentThread().getName() + ": " + contador);
    }
}
