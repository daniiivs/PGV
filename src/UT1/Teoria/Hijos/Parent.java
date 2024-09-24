package UT1.Teoria.Hijos;

import java.io.*;

public class Parent {
    public static void main(String[] args) {
        try {
            Process proceso = new ProcessBuilder("java", "-jar", "src/UT1/Teoria/Hijos/PruebaPadres.jar").start();

            OutputStreamWriter osw = new OutputStreamWriter(proceso.getOutputStream());
            String mensaje = "Este es el programa padre\n";
            osw.write(mensaje);
            osw.flush();

            InputStreamReader isr = new InputStreamReader(proceso.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            osw.close();
            isr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
