package UT2.Actividades.Actividad4.Contador;

import java.util.concurrent.Semaphore;

public class Lanzador {
    public static void main(String[] args) {
        final int numeroProductos = 10;
        final int numeroReponedores = 30;
        final int numeroClientes = 5;

        Semaphore semaphore = new Semaphore(1);
        Reponedores reponedor;
        Clientes cliente;
        Productos productos = new Productos(numeroProductos);

        System.out.println("Número inicial de productos: " + numeroProductos);

        for (int i = 0; i < numeroReponedores; i++) {
            reponedor = new Reponedores(productos, semaphore);
            reponedor.setName("Reponedor " + (i + 1));
            reponedor.start();
        }

        for (int i = 0; i < numeroClientes; i++) {
            cliente = new Clientes(productos, semaphore);
            cliente.setName("Cliente " + (i + 1));
            cliente.start();
        }
    }
}
