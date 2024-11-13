package UT2.ExamenBien;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase lanzador, que crea y ejecuta los hilos
public class Lanzador {
    public static void main(String[] args) {
        final Random random = new Random();
        final Semaphore semaforo = new Semaphore(1); // Semáforo con permit de 1 para controlar el acceso al recurso compartido
        final int numeroClientes = 15; // El numero de clientes que generaremos
        Clientes cliente;
        Bancos banco;

        banco = new Bancos(semaforo); // Le pasamos los fondos iniciales
        banco.setName("BancoSpain"); // Le establecemos el nombre
        banco.start();


        // Bucle para generar los clientes
        for (int i = 0; i < numeroClientes; i++) {
            cliente = new Clientes(semaforo);
            cliente.setName("Cliente " + (i + 1));
            cliente.start();
        }

        Bancos.imprimirDatos(); // Imprimimos los datos iniciales de cada banco
    }

    // Redondea un double a dos decimales
    public static double redondear(double numero) {
        int entero = (int) (numero * 100);
        return (double) entero / 100;
    }
}
