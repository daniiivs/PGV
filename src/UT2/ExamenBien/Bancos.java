package UT2.ExamenBien;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase Bancos que extiende de Threads
public class Bancos extends Thread {
    final static Random random = new Random();
    final static String[] vectorBancos = {"Sabadell", "Bankia", "BBVA", "Santander", "Caixa"};
    static double[] fondos = new double[vectorBancos.length]; // Recurso compartido
    static int numeroInyecciones = 0;
    final static int maximoInyecciones = 4;
    final static int cambioPrioridad = 2; // Iteracion en la que debemos cambiar la prioridad

    Semaphore semaforo;

    // Constructor de Bancos
    public Bancos(Semaphore semaforo) {
        for (int i = 0; i < vectorBancos.length; i++) {
            fondos[i] = Lanzador.redondear(random.nextDouble(10000, 20001));
        }
        this.semaforo = semaforo;
    }

    public void run() {
        try {
            // Bucle para cada inyeccion
            for (int i = 0; i < maximoInyecciones; i++) {
                sleep(random.nextLong(10, 21) * 1000);
                semaforo.acquire();
                inyectar(); // Inyectamos el dinero
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
        for (int i = 0; i < vectorBancos.length; i++) {
            System.out.print(vectorBancos[i] + " " + fondos[i] + "\n");
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
    public static void inyectar() {
        numeroInyecciones++;
        for (int i = 0; i < vectorBancos.length; i++) {
            double aumento; // Cantidad a inyectar
            aumento = Lanzador.redondear(random.nextDouble(10000, 20000));
            fondos[i] = fondos[i] + aumento;
            System.out.println("\n" + currentThread().getName().toUpperCase() + " HA RECIBIDO UNA INYECCIÓN DE " + aumento + "€, POR LO QUE TIENE UN TOTAL DE " + getFondos(vectorBancos[i]) + "€. (Inyecciones " + numeroInyecciones + ")");
        }

    }

    // Comprueba si es posible o no retirar del banco una cantidad determinada
    public static boolean retirar(double cantidad, String banco) {
        int pos = 0;
        for (int i = 0; i < vectorBancos.length; i++) {
            if (banco.equals(vectorBancos[i])) {
                pos = i;
                break;
            }
        }
        if (cantidad > fondos[pos]) {
            return false;
        } else {
            fondos[pos] = Lanzador.redondear(fondos[pos] - cantidad);
            return true;
        }
    }

    public static double getFondos(String banco) {
        int pos = 0;
        for (int i = 0; i < vectorBancos.length; i++) {
            if (banco.equals(vectorBancos[i])) {
                pos = i;
                break;
            }
        }
        return fondos[pos];
    }
}
