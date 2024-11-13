package UT2.ExamenBien;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase Clientes que extiende de Thread
public class Clientes extends Thread {
    final static Random random = new Random();
    final static int intentos = 3; // Numero de veces que un cliente intenta pedir prestamo
    Semaphore semaforo;
    String banco;
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
                banco = Bancos.vectorBancos[(random.nextInt(Bancos.vectorBancos.length))]; // Elegimos un banco aleatorio
                semaforo.acquire();
                System.out.println("\nEl " + currentThread().getName() + " ha pedido un préstamo a " + banco + " de " + prestamo + "€,");
                if (Bancos.retirar(prestamo, banco)) { // Si el préstamo se consigue realizar...
                    System.out.println("y se lo han CONCEDIDO (nuevos fondos de " + banco + ": " + Bancos.getFondos(banco) + "€)");
                    semaforo.release();
                    break; // Salimos del bucle
                } else { // Si no se retira
                    System.out.println("pero se lo han DENEGADO porque " + banco + " no dispone de fondos suficientes (" + Bancos.getFondos(banco) + "€)");
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
