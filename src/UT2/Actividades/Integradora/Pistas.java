package UT2.Actividades.Integradora;

import java.util.ArrayList;
import java.util.Arrays;

public class Pistas {
    static String[] lineas = {"Binter", "Ryanair", "Vueling", "Spanair", "Iberia", "Air Berlin", "Lufthansa", "Condor", "SwissAir", "Canaryfly"};
    static int[] avionesPorLinea;
    final int maximoAviones = 50;
    final int maximoLineas = 10;
    ArrayList<Aviones> avionesEnPista = new ArrayList<>();

    public Pistas() {
        avionesPorLinea = new int[lineas.length];
        Arrays.fill(avionesPorLinea, 0);
    }

    public boolean tomarPista(Aviones avion) {
        if (avionesEnPista.size() < maximoAviones && noSuperaLimite(avion)) {
            avionesEnPista.add(avion);
            return true;
        } else {
            return false;
        }
    }

    private boolean noSuperaLimite(Aviones avion) {
        for (int i = 0; i < lineas.length; i++) {
            if (avion.getName().contains(lineas[i]) && avionesPorLinea[i] < maximoLineas) {
                avionesPorLinea[i]++;
                return true;
            }
        }
        return false;
    }

    public boolean despegar(Aviones avion) {
        if (avionesEnPista.getFirst().equals(avion)) {
            System.out.println("\n" + Thread.currentThread().getName() + " ha despegado.");
            avionesEnPista.remove(avion);
            disminuirContador(avion);
            mostrarCola();
            return true;
        } else {
            return false;
        }
    }

    private void mostrarCola() {
        if (!avionesEnPista.isEmpty()){
            System.out.println(avionesEnPista.getFirst().getName() + " se encuentra primero en la cola.\n");
        } else {
            System.out.println("No hay aviones en cola.\n");
        }
    }

    public int getPosicionAvion(Aviones avion) {
        return (avionesEnPista.indexOf(avion)) + 1;
    }

    public void disminuirContador(Aviones avion) {
        for (int i = 0; i < lineas.length; i++) {
            if (avion.getName().contains(lineas[i])) {
                avionesPorLinea[i]--;
            }
        }
    }
}
