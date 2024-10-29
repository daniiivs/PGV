package UT2.Actividades.Actividad2.ModeloPCDibujo;

import java.util.Arrays;

public class Plazas {
    char[] plazasDisponibles;
    final int plazasTotales = 6;

    public Plazas() {
        plazasDisponibles = new char[plazasTotales];
        Arrays.fill(plazasDisponibles, 'O');
    }

    public synchronized int aparcar() {
        for (int i = 0; i < plazasTotales; i++) {
            if (plazasDisponibles[i] == 'O') {
                plazasDisponibles[i] = 'X';
                System.out.println("Plaza OCUPADA por " + Thread.currentThread().getName() + ". Plazas:");
                imprimirPlazas();
                return i;
            }
        }
        System.out.println("Todas las plazas están ocupadas. El coche con matrícula " + Thread.currentThread().getName() + " no puede aparcar.");
        return -1;
    }

    public synchronized void salir(int plazaLiberada) {
        plazasDisponibles[plazaLiberada] = 'O';
        System.out.println("Plaza LIBERADA por " + Thread.currentThread().getName() + ". Plazas:");
        imprimirPlazas();
    }

    public int getPlazasTotales() {
        return plazasTotales;
    }

    public void imprimirPlazas() {
        for (char estado : plazasDisponibles) {
            System.out.print("| " + estado + " ");
        }
        System.out.println("|\n");
    }
}
