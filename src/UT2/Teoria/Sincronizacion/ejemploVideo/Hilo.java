package UT2.Teoria.Sincronizacion.ejemploVideo;

public class Hilo extends Thread {
    Contador contador;

    public Hilo(Contador contador) {
        this.contador = contador;
    }

    // Contiene el código que ejecutarán los hilos
    public void run() {
        // Si tenemos un valor pequeño no dará problema. Sin embargo, si ponemos un valor mayor (10000), veremos que el resultado
        // no será 20000. Esto es porque habrá ocasiones en las que ambos hilos intenten acceder al metodo de forma simultánea,
        // por lo que uno de ellos no llegará a sumar
        for (int i = 0; i < 10000; i++) {
            contador.sumar();
        }
    }
}
