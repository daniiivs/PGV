package UT2.Actividades.Actividad2.ModeloPCDibujo;

import java.util.Arrays;

public class Plazas {
    static final int plazasTotales = 6;
    char[] plazasDisponibles; // [ _ _ _ _ _ _ ]

    public Plazas() {
        plazasDisponibles = new char[plazasTotales];
        Arrays.fill(plazasDisponibles, '_');
    }

    public synchronized int ocupar() {
        for (int i = 0; i < plazasTotales; i++) {
            if (plazasDisponibles[i] == '_') {
                plazasDisponibles[i] = 'X';
                System.out.println("Plaza OCUPADA por " + Thread.currentThread().getName() + ". Plazas:");
                imprimirPlazas();
                return i;
            }
        }
        System.out.println("Todas las plazas están ocupadas. El coche con matrícula " + Thread.currentThread().getName() + " no puede aparcar.");
        return -1;
    }

    public synchronized void liberar(int plazaLiberada) {
        plazasDisponibles[plazaLiberada] = '_';
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
