package UT2.ExamenMal;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase lanzador, que crea y ejecuta los hilos
public class Lanzador {
    public static void main(String[] args) {
        final Random random = new Random();
        final Semaphore semaforo = new Semaphore(1); // Semáforo con permit de 1 para controlar el acceso al recurso compartido

        /*
        El semaforo tiene un permit de 1 ya que, a pesar de que tenemos 5 bancos,
        si ponemos un permit de 5 puede darse el caso en el que dos o mas clientes
        accedan simultaneamente al mismo banco, lo que podria generar conflictos
        sobre el recurso compartido. Ademas, tambien podria ocurrir que un cliente
        intente sacar dinero mientras el banco recibe una inyeccion, por lo que
        si ponemos un permit de 1 nos ahorramos todos estos problemas
         */

        final int numeroClientes = 15; // El numero de clientes que generaremos
        Clientes cliente;
        Bancos banco;

        // Bucle para generar los bancos
        for (int i = 0; i < Bancos.vectorBancos.length; i++) {
            banco = new Bancos(semaforo, redondear(random.nextDouble(10000, 20001))); // Le pasamos los fondos iniciales
            banco.setName(Bancos.vectorBancos[i]); // Le establecemos el nombre almacenado en el array
            banco.start();
        }

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
