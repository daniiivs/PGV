package UT1.Integradora;

import javax.swing.*;
import java.io.*;

public class Padre {
    public static void main(String[] args) {
        try {
            Process proceso1 = new ProcessBuilder("msconfig.exe").start();
            //Process proceso1Bat = new ProcessBuilder("./src/UT1/Integradora/Proceso1.bat").start();
            Process proceso2 = new ProcessBuilder("java", "-jar", "./src/UT1/Integradora/Hijo.jar").start();

            String nombreCarpeta;

            nombreCarpeta = JOptionPane.showInputDialog("Introduce el nombre de la carpeta:");
            OutputStreamWriter osr = new OutputStreamWriter(proceso2.getOutputStream());
            osr.write(nombreCarpeta);
            osr.flush();
            osr.close();

            proceso2.waitFor();
            File carpeta = new File("./src/UT1/Integradora/" + nombreCarpeta);
            String rutaCarpeta = carpeta.getAbsolutePath();
            Process proceso4 = new ProcessBuilder("cmd", "/C", "dir", rutaCarpeta).start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso4.getInputStream()));
            String linea;

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            proceso1.destroy();
            //proceso1Bat.destroy();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
