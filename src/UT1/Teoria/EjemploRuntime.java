package UT1.Teoria;

import java.io.IOException;

public class EjemploRuntime {
    public static void main(String[] args) {
        String primerComando = "notepad.exe";
        String segundoComando = "calc.exe";

        ProcessBuilder pb = new ProcessBuilder(primerComando);
        Runtime rt = Runtime.getRuntime();

        try {
            Process primerProceso = pb.start();
            Process segundoProceso = rt.exec(segundoComando);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
