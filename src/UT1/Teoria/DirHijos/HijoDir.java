package UT1.Teoria.DirHijos;

import java.io.IOException;

public class HijoDir {
    public static void main(String[] args) {
        try {
            Process process = new ProcessBuilder("dir", "./src/UT1/Teoria").start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}