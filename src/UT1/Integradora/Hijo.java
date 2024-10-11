package UT1.Integradora;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

// Clase hijo (realizar un .jar de esta para poder ejecutar el padre)
public class Hijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ruta = "./src/UT1/Integradora/" + sc.nextLine(); // Recogemos el nombre de la carpeta enviado por el padre.
        File carpeta = new File(ruta);
        // Añado manualmente la ruta en la que se ubica, ya que solo se pide el nombre

        Process proceso3;
        OutputStreamWriter osw;

        try {
            // Recorremos la carpeta, obteniendo el nombre de cada archivo
            for (String nombreArchivo : carpeta.list()) {
                proceso3 = new ProcessBuilder("./src/UT1/Integradora/Proceso3.bat").start(); // Ejecutamos el .bat en el proceso 3
                osw = new OutputStreamWriter(proceso3.getOutputStream());
                osw.write(ruta + "/" + nombreArchivo); // Escribimos el nombre del archivo y lo mandamos al .bat
                osw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
