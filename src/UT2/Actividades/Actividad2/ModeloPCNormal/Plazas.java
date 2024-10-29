package UT2.Actividades.Actividad2.ModeloPCNormal;

public class Plazas {
    final int plazasTotales = 6;
    int plazasOcupadas = 0;

    public synchronized boolean aparcar() {
        if (plazasOcupadas < plazasTotales) {
            plazasOcupadas++;
            System.out.println("Plaza OCUPADA por " + Thread.currentThread().getName() + ". Plazas libres: " + (plazasTotales - plazasOcupadas));
            return true;
        } else {
            System.out.println("Todas las plazas están ocupadas. El coche con matrícula " + Thread.currentThread().getName() + " no puede aparcar.");
            return false;
        }
    }

    public synchronized void salir() {
        plazasOcupadas--;
        System.out.println("Plaza LIBERADA por " + Thread.currentThread().getName() + ". Plazas libres: " + (plazasTotales - plazasOcupadas));
    }

    public int getPlazasTotales() {
        return plazasTotales;
    }
}
