package UT1.Teoria.Procesos;

import java.io.IOException;

public class EjemploProcesos {
    public static void main(String[] args) {
        //String comando = "notepad.exe";
        //Definir el comando
        //ProcessBuilder pb = new ProcessBuilder(comando);

        String comando = "./src/UT1/Teoria/Procesos/prueba.bat";
        ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "start src\\UT1\\Teoria\\Procesos\\prueba.bat"); //poner las \\ porque si no no coge la ruta

        //Arrancar el proceso
        try {
            Process pro = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Para archivos .jar, añadir -java -jar
    }
}