package UT2.Teoria.Sincronizacion.ejemploVideo;

public class Contador {
    private int contador = 0;

    // Para solventar el error de acceso, debemos añadir synchronized al metodo. De esta manera, le decimos al proceso que si
    // un hilo quiere acceder al metodo, pero hay otro que también lo está intentando, deberá esperar a que termine el primero
    // para dejar paso al siguiente
    public synchronized void sumar() {
        contador++;
    }

    public int getContador() {
        return contador;
    }
}
