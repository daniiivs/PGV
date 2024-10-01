package UT1.Actividad2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ServiciosPadre {
    public static void main(String[] args) {
        String linea;
        String nombreProceso;

        final String NOMBRE_SERVICIO = "svchost.exe";
        final int SERVICIO_SIZE = 3;
        try {
            Process processList = new ProcessBuilder("java", "-jar", "src/UT1/Actividad2/Servicios.jar").start();

            InputStreamReader isr = new InputStreamReader(processList.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((linea = br.readLine()) != null) {
                if (linea.contains(NOMBRE_SERVICIO)) {
                    System.out.println(linea);
                    for (int i = 0; i < SERVICIO_SIZE - 1; i++) {
                        System.out.println(br.readLine());
                    }
                    System.out.println();
                }
            }

            Process processConfig = new ProcessBuilder("java", "-jar", "src/UT1/Actividad2/ServiciosConfig.jar").start();
            nombreProceso = JOptionPane.showInputDialog("Introduce el nombre del proceso para comprobar su configuración:");
            OutputStreamWriter osw = new OutputStreamWriter(processConfig.getOutputStream());
            osw.write(nombreProceso);
            osw.flush();
            osw.close();

            isr = new InputStreamReader(processConfig.getInputStream());
            br = new BufferedReader(isr);
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
