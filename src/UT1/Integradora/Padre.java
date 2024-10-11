package UT1.Integradora;

import javax.swing.*;
import java.io.*;

// Clase padre
public class Padre {
    public static void main(String[] args) {
        try {
            Process proceso1 = new ProcessBuilder("msconfig.exe").start(); // Proceso 1
            //Process proceso1Bat = new ProcessBuilder("./src/UT1/Integradora/Proceso1.bat").start(); // Proceso 1 mediante .bat
            Process proceso2 = new ProcessBuilder("java", "-jar", "./src/UT1/Integradora/Hijo.jar").start(); // Proceso 2

            String nombreCarpeta;

            // Obtenemos el nombre de la carpeta y la enviamos al hijo
            nombreCarpeta = JOptionPane.showInputDialog("Introduce el nombre de la carpeta:");
            OutputStreamWriter osr = new OutputStreamWriter(proceso2.getOutputStream());
            osr.write(nombreCarpeta);
            osr.flush();
            osr.close();

            proceso2.waitFor(); // Esperamos a que el proceso 2 termine
            File carpeta = new File("./src/UT1/Integradora/" + nombreCarpeta);
            String rutaCarpeta = carpeta.getAbsolutePath(); // Obtenemos la ruta de la carpeta
            Process proceso4 = new ProcessBuilder("cmd", "/C", "dir", rutaCarpeta).start(); // Ejecutamos el proceso 4, que hace un dir de la carpeta

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso4.getInputStream()));
            String linea;

            // Leemos el resultado del proceso 4, que corresponde al dir
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            proceso1.destroy(); // Destruimos el proceso 1
            //proceso1Bat.destroy();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
