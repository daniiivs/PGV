package UT1.Actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MayusculaPadre {
    public static void main(String[] args) {
        try {
            Process proceso = new ProcessBuilder("java", "-jar","src/UT1/Actividad2/Mayuscula.jar").start();

            OutputStreamWriter osw = new OutputStreamWriter(proceso.getOutputStream());
            String mensaje = "Hola, mi nombre es Daniel Viera";
            osw.write(mensaje);
            osw.flush();
            osw.close();

            InputStreamReader isr = new InputStreamReader(proceso.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            isr.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
