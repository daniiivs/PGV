package UT2.Actividades.Integradora;

import java.util.ArrayList;
import java.util.Arrays;

// Clase Pistas, el recurso compartido
public class Pistas {
    static String[] lineas = {"Binter", "Ryanair", "Vueling", "Spanair", "Iberia", "Air Berlin", "Lufthansa", "Condor", "SwissAir", "Canaryfly"};
    static int[] avionesPorLinea; // Almacena cuántos aviones de cada línea hay en pista
    final int maximoAviones = 50; // El máximo de aviones que podrán estar en pista
    final int maximoLineas = 10; // El máximo de aviones de una línea determinada que podrán estar en pista
    ArrayList<Aviones> avionesEnPista = new ArrayList<>(); // Lista que almacena de forma ordenada los aviones en pista

    // Constructor de Pistas
    public Pistas() {
        avionesPorLinea = new int[lineas.length];
        Arrays.fill(avionesPorLinea, 0); // Establecemos que de entrada hay 0 aviones de cada aerolínea en pista
    }

    // El avión intenta tomar pista
    public boolean tomarPista(Aviones avion) {
        // Comprobamos que haya menos aviones en pista de los permitidos y que el avión no supera el límite por aerolínea
        if (avionesEnPista.size() < maximoAviones && noSuperaLimite(avion)) {
            avionesEnPista.add(avion); // Añade el avión a la pista (a la lista)
            return true;
        } else {
            return false;
        }
    }

    // Comprobar si no nos pasamos de número de aviones por aerolínea
    private boolean noSuperaLimite(Aviones avion) {
        for (int i = 0; i < lineas.length; i++) {
            // Comprobamos si el nombre del avión coincide con el elemento dentro de la lista de aerolíneas y su número de aviones supera el máximo
            if (avion.getName().contains(lineas[i]) && avionesPorLinea[i] < maximoLineas) {
                avionesPorLinea[i]++; // Aumentamos el número de aviones de esa aerolínea en 1
                return true;
            }
        }
        return false;
    }

    // El avión intenta despegar
    public boolean despegar(Aviones avion) {
        if (avionesEnPista.getFirst().equals(avion)) { // Comprobamos si es el primero en la lista
            System.out.println("\n" + Thread.currentThread().getName() + " ha despegado.");
            avionesEnPista.remove(avion); // Lo eliminamos de la lista, que se reordena sola
            disminuirContador(avion); // Disminuimos en 1 el valor del contador de aviones por línea
            mostrarCola(); // Mostramos quién está primero en la cola
            return true;
        } else {
            return false;
        }
    }

    // Imprime el primero en cola y la cola en sí
    private void mostrarCola() {
        if (!avionesEnPista.isEmpty()){
            System.out.println(avionesEnPista.getFirst().getName() + " se encuentra primero en la cola:");
            for (Aviones avion: avionesEnPista) {
                System.out.print("| " + avion.getName().substring(0, 2) + avion.getName().split("-")[1].trim() + " ");
            }
            System.out.println("|\n");
        } else {
            System.out.println("No hay aviones en cola.\n");
        }
    }

    // Comprueba al posición en la que se encuentra el avión dentro de la pista (NO de la lista, por eso +1)
    public int getPosicionAvion(Aviones avion) {
        return (avionesEnPista.indexOf(avion)) + 1;
    }

    // Disminuir el contador de aviones por aerolínea
    public void disminuirContador(Aviones avion) {
        for (int i = 0; i < lineas.length; i++) {
            if (avion.getName().contains(lineas[i])) {
                avionesPorLinea[i]--;
            }
        }
    }
}
