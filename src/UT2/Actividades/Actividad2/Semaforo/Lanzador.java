package UT2.Actividades.Actividad2.Semaforo;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lanzador {
    public static void main(String[] args) {
        Plazas plazas = new Plazas();
        Random random = new Random();
        StringBuilder matricula;
        Coches coches;
        int numeroCoches = 10;
        Semaphore semaforo = new Semaphore(1);

        System.out.println("Plazas libres: " + plazas.getPlazasTotales());
        for (int i = 0; i < numeroCoches; i++) {
            matricula = new StringBuilder(random.nextInt(1000, 10000) + " ");
            for (int j = 0; j < 3; j++) {
                matricula.append((char) random.nextInt(65, 91));
            }
            coches = new Coches(plazas, semaforo);
            coches.setName(matricula.toString());
            coches.start();
        }
    }
}
