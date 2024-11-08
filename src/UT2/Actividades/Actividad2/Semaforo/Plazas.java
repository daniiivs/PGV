package UT2.Actividades.Actividad2.Semaforo;

public class Plazas {
    final int plazasTotales = 6;
    int plazasOcupadas = 0;

    public boolean aparcar() {
        if (plazasOcupadas < plazasTotales) {
            plazasOcupadas++;
            System.out.println("Plaza OCUPADA por " + Thread.currentThread().getName() + ". Plazas libres: " + (plazasTotales - plazasOcupadas));
            return true;
        } else {
            System.out.println("Todas las plazas están ocupadas. El coche con matrícula " + Thread.currentThread().getName() + " no puede aparcar.");
            return false;
        }
    }

    public void salir() {
        plazasOcupadas--;
        System.out.println("Plaza LIBERADA por " + Thread.currentThread().getName() + ". Plazas libres: " + (plazasTotales - plazasOcupadas));
    }

    public int getPlazasTotales() {
        return plazasTotales;
    }
}
