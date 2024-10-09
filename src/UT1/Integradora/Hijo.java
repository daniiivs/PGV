package UT1.Integradora;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Hijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ruta = "./src/UT1/Integradora/" + sc.nextLine();
        File carpeta = new File(ruta);

        Process proceso3;
        OutputStreamWriter osw;

        try {
            for (String nombreArchivo : carpeta.list()) {
                proceso3 = new ProcessBuilder("./src/UT1/Integradora/Proceso3.bat").start();
                osw = new OutputStreamWriter(proceso3.getOutputStream());
                osw.write(ruta + "/" + nombreArchivo);
                osw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
