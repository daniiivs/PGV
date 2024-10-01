package UT1.Actividad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ServiciosConfigHijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] comando = {"sc", "qc", sc.nextLine()};
        String linea;

        try {
            Process process = new ProcessBuilder(comando).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((linea = br.readLine()) != null ) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
