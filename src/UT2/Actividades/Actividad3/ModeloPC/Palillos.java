package UT2.Actividades.Actividad3.ModeloPC;

import java.util.ArrayList;

public class Palillos {
    static ArrayList<Palillos> listaPalillos = new ArrayList<>();
    boolean ocupado;

    public Palillos() {
        ocupado = false;
        listaPalillos.add(this);
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean isOcupado() {
        return ocupado;
    }
}
