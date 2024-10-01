package UT1.Actividad2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MayusculaHijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(sc.nextLine().toUpperCase());

        File cancion = new File("src/UT1/Actividad2/daylight.m4a");
        String comando = "cmd /C start wmplayer.exe " + cancion.getAbsolutePath();
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(comando);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
