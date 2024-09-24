package UT1.Teoria.Procesos;

import java.io.IOException;

public class EjemploRuntime {
    public static void main(String[] args) {
        String primerComando = "notepad.exe";
        String segundoComando = "calc.exe";
        String rutaBat = "src/UT1/Teoria/Procesos/prueba.bat";
        String rutaJar = "java -jar src/UT1/Teoria/Procesos/Calculadora.jar";

        ProcessBuilder pb = new ProcessBuilder(primerComando);
        Runtime rt = Runtime.getRuntime();

        try {
            //Process primerProceso = pb.start();
            //Process segundoProceso = rt.exec(segundoComando);
            //Process batProceso = rt.exec(rutaBat);
            Process batJar = rt.exec(rutaJar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
