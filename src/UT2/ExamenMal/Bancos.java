package UT2.ExamenMal;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase Bancos que extiende de Threads
public class Bancos extends Thread {
    final static Random random = new Random();
    final static String[] vectorBancos = {"Sabadell", "Bankia", "BBVA", "Santander", "Caixa"};
    static ArrayList<Bancos> listaBancos = new ArrayList<>(); // Almacena cada banco creado
    final static int maximoInyecciones = 4;
    final static int cambioPrioridad = 2; // Iteracion en la que debemos cambiar la prioridad

    Semaphore semaforo;
    double fondo; // Recurso compartido al que acceden los clientes
    double aumento; // Cantidad a inyectar
    int numeroInyecciones = 0;

    // Constructor de Bancos
    public Bancos(Semaphore semaforo, double fondo) {
        this.semaforo = semaforo;
        this.fondo = fondo;
        listaBancos.add(this);
    }

    public void run() {
        try {
            // Bucle para cada inyeccion
            for (int i = 0; i < maximoInyecciones; i++) {
                sleep(random.nextLong(10, 21) * 1000);
                semaforo.acquire();
                inyectar(); // Inyectamos el dinero
                System.out.println("\n" + currentThread().getName().toUpperCase() + " HA RECIBIDO UNA INYECCIÓN DE " + aumento + "€, POR LO QUE TIENE UN TOTAL DE " + fondo + "€. (Inyecciones " + numeroInyecciones + ")");
                comprobarPrioridad(); // Comprobamos la iteracion y vemos si hay que cambiarla
                semaforo.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Imprime los datos de cada banco
    public static void imprimirDatos() {
        System.out.println("Los datos de cada banco son:");
        for (Bancos banco : listaBancos) {
            System.out.println(" - " + banco.getName() + ": " + banco.fondo + "€");
        }
    }

    // Comprueba cuantas inyecciones ha recibido el banco para cambiar su prioridad
    public void comprobarPrioridad() {
        if (numeroInyecciones == cambioPrioridad) {
            currentThread().setPriority(Thread.MIN_PRIORITY);
            System.out.println("HA HABIDO UN CAMBIO DE PRIORIDAD: " + currentThread().getName() + " HA DISMINUIDO SU PRIORIDAD A " + currentThread().getPriority());
        }
    }

    // Inyecta una cantidad aleatoria al fondo del banco
    public void inyectar() {
        numeroInyecciones++;
        aumento = Lanzador.redondear(random.nextDouble(10000, 20000));
        fondo = Lanzador.redondear(aumento + fondo);
    }

    // Comprueba si es posible o no retirar del banco una cantidad determinada
    public boolean retirar(double cantidad) {
        if (cantidad > fondo) {
            return false;
        } else {
            fondo = Lanzador.redondear(fondo - cantidad);
            return true;
        }
    }
}
