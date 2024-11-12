package UT2.Actividades.Actividad4.Contador;

public class Productos {
    int productos;
    int maximoProductos;

    public Productos(int numeroProductos) {
        productos = numeroProductos;
        maximoProductos = numeroProductos;
    }

    public boolean comprar() {
        if (productos > 0) {
            productos--;
            return true;
        } else {
            return false;
        }
    }

    public boolean reponer() {
        if (productos < maximoProductos) {
            productos++;
            return true;
        } else {
            return false;
        }
    }

    public void imprimirProductos() {
        System.out.println("Cantidad de productos restantes: " + productos + "\n");
    }
}
