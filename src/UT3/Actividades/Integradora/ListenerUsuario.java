package UT3.Actividades.Integradora;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ListenerUsuario extends Thread {
    BufferedReader in;
    BufferedWriter out;
    JTextArea chatArea;

    public ListenerUsuario(BufferedReader in, BufferedWriter out, JTextArea chatArea) {
        this.in = in;
        this.out = out;
        this.chatArea = chatArea;
    }

    public void run() {
        String mensaje;
        try {
            while (true) {
                mensaje = in.readLine();
                chatArea.append(mensaje + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
