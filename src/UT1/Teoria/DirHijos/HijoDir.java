package UT1.Teoria.DirHijos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HijoDir {
    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("cmd /C dir");
            String line;

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}