package UT2.Actividades.Actividad4.ModeloPC;

import java.util.Arrays;

public class Productos {
    char[] estanteria;

    public Productos(int numeroProductos) {
        estanteria = new char[numeroProductos];
        Arrays.fill(estanteria, 'O');
    }

    public boolean comprar() {
        for (int i = 0; i < estanteria.length; i++) {
            if (estanteria[i] == 'O') {
                estanteria[i] = '_';
                return true;
            }
        }
        return false;
    }

    public boolean reponer() {
        for (int i = 0; i < estanteria.length; i++) {
            if (estanteria[i] == '_') {
                estanteria[i] = 'O';
                return true;
            }
        }
        return false;
    }

    public void imprimirEstanteria() {
        for (char estado : estanteria) {
            System.out.print("| " + estado + " ");
        }
        System.out.println("|\n");
    }
}
