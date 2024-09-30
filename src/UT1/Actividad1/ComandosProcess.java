package UT1.Actividad1;

import java.io.IOException;

public class ComandosProcess {
    public static void main(String[] args) {
        // Comando
        String comando = "calc.exe";
        ProcessBuilder pb = new ProcessBuilder(comando);
        try {
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Archivo .bat
        String bat = ".\\src\\UT1\\Actividad1\\ArchivoBat.bat";
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/C", "start", bat);
        try {
            pb2.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Archivo .jar
        String jar = "src\\UT1\\Actividad1\\Calculadora.jar";
        ProcessBuilder pb3 = new ProcessBuilder("java", "-jar", jar);
        try {
            pb3.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
