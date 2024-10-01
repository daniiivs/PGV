package UT1.Actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServiciosHijo {
    public static void main(String[] args) {
        String[] comando = {"tasklist","/SVC","/FO","LIST"};
        String linea;

        try {
            Process process = new ProcessBuilder(comando).start();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
