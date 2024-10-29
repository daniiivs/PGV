package UT2.Actividades.Actividad3.ModeloPC;

public class Lanzador {
    public static void main(String[] args) {
        final int numeroFilosofos = 5;
        Filosofos filosofo;

        for (int i = 0; i < numeroFilosofos; i++) {
            new Palillos();
        }

        for (int i = 0; i < numeroFilosofos; i++) {
            filosofo = new Filosofos();
            filosofo.setName("Filósofo " + (i + 1));
            filosofo.setIzquierdo(Palillos.listaPalillos.get(i));
            if (i == numeroFilosofos - 1) {
                filosofo.setDerecho(Palillos.listaPalillos.getFirst()); // No poner 1, poner 0 o getFirst
            } else {
                filosofo.setDerecho(Palillos.listaPalillos.get(i + 1));
            }
            filosofo.start();
        }
    }
}
