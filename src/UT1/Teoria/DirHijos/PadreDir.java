package UT1.Teoria.DirHijos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PadreDir {
    public static void main(String[] args) {
        try {
            Process process = new ProcessBuilder("java", "-jar", "./src/UT1/Teoria/DirHijos/JarDir.jar").start();

            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
