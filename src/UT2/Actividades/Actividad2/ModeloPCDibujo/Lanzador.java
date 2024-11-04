package UT2.Actividades.Actividad2.ModeloPCDibujo;

import java.util.Random;

public class Lanzador {
    public static void main(String[] args) {
        Plazas plazas = new Plazas();
        Coches coches;
        int numeroCoches = 10;

        System.out.println("Plazas libres: " + plazas.getPlazasTotales());
        for (int i = 0; i < numeroCoches; i++) {
            coches = new Coches(plazas);
            coches.setName(generarMatricula());
            coches.start();
        }
    }

    private static String generarMatricula() {
        Random random = new Random();
        StringBuilder matricula;
        matricula = new StringBuilder(random.nextInt(1000, 10000) + " ");
        for (int j = 0; j < 3; j++) {
            matricula.append((char) random.nextInt(65, 91));
        }
        return matricula.toString();
    }
}
