package UT2.Actividades.Actividad4.ModeloPC;

import java.util.concurrent.Semaphore;

public class Lanzador {
    public static void main(String[] args) {
        final int numeroReponedores = 3;
        final int numeroClientes = 5;
        final int numeroProductos = 10;

        Semaphore semaphore = new Semaphore(1);
        Reponedores reponedor;
        Clientes cliente;
        Productos estanteria = new Productos(numeroProductos);

        System.out.println("Estado inicial de la estantería:");
        estanteria.imprimirEstanteria();

        for (int i = 0; i < numeroReponedores; i++) {
            reponedor = new Reponedores(estanteria, semaphore);
            reponedor.setName("Reponedor " + (i + 1));
            reponedor.start();
        }

        for (int i = 0; i < numeroClientes; i++) {
            cliente = new Clientes(estanteria, semaphore);
            cliente.setName("Cliente " + (i + 1));
            cliente.start();
        }
    }
}
