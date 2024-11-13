package UT2.ExamenMal;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase Clientes que extiende de Thread
public class Clientes extends Thread {
    final static Random random = new Random();
    final static int intentos = 3; // Numero de veces que un cliente intenta pedir prestamo
    Semaphore semaforo;
    Bancos banco;
    double prestamo; // Importe del prestamo a pedir

    // Constructor de Clientes
    public Clientes(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void run() {
        prestamo = Lanzador.redondear(random.nextDouble(9000, 15000)); // Calculamos cual será el préstamo

        // Bucle que se repite el numero de intentos establecidos
        for (int i = 0; i < intentos; i++) {
            try {
                sleep(random.nextLong(5, 21) * 1000);
                banco = Bancos.listaBancos.get(random.nextInt(Bancos.listaBancos.size())); // Elegimos un banco aleatorio
                semaforo.acquire();
                System.out.println("\nEl " + currentThread().getName() + " ha pedido un préstamo a " + banco.getName() + " de " + prestamo + "€,");
                if (banco.retirar(prestamo)) { // Si el préstamo se consigue realizar...
                    System.out.println("y se lo han CONCEDIDO (nuevos fondos de " + banco.getName() + ": " + banco.fondo + "€)");
                    semaforo.release();
                    break; // Salimos del bucle
                } else { // Si no se reitra
                    System.out.println("pero se lo han DENEGADO porque " + banco.getName() + " no dispone de fondos suficientes (" + banco.fondo + "€)");
                    if (i == intentos - 1){ // Si además estamos en el último intento...
                        System.out.println("El " + currentThread().getName() + " está cansado de intentar conseguir un préstamo, así que se va a casa con las manos vacías...");
                    }
                    semaforo.release();
                    Thread.sleep(random.nextLong(10, 21) * 1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
