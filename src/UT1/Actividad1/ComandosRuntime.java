package UT1.Actividad1;

import java.io.IOException;

public class ComandosRuntime {
    public static void main(String[] args) {
        // Comando
        String comando = "calc.exe";
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(comando);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Archivo .bat
        String bat = ".\\src\\UT1\\Actividad1\\ArchivoBat.bat";
        try {
            rt.exec("cmd /C start " + bat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Archivo .jar
        String jar = "java -jar src\\UT1\\Actividad1\\Calculadora.jar";
        try {
            rt.exec(jar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
