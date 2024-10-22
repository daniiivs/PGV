package UT2.Actividades.Actividad1;

public class MiHilo implements Runnable {
    static int numeroHilos = 4;
    static int numeroMuestras = 5;

    public static void main(String[] args) {
        for (int i = 0; i < numeroHilos; i++) {
            new Thread(new MiHilo(), "Hilo " + i).start();
        }
        System.out.println("Hilo principal finalizado");
    }

    public void run() {
        for (int i = 0; i < numeroMuestras; i++) {
            System.out.println("Se está ejecutando el " + Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName() + " terminado.");
    }
}