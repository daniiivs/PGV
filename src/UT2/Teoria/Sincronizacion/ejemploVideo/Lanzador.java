package UT2.Teoria.Sincronizacion.ejemploVideo;

public class Lanzador {
    public static void main(String[] args) {
        Contador contador = new Contador(); // Creamos un nuevo contador

        Hilo hilo1 = new Hilo(contador); // Pasamos el contador a ambos hilos
        Hilo hilo2 = new Hilo(contador);

        hilo1.start(); // Ejecutamos ambos hilos
        hilo2.start();

        // Si no esperamos mediante los join, se mostrará que el contador vale 0. Esto es
        // debido a que el  proceso padre termina incluso antes de que se lancen los hilos
        try {
            hilo1.join(); // Esperamos a que terminen ambos hilos
            hilo2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("El contador vale: " + contador.getContador()); // Comprobamos cuanto vale el contador
    }
}
