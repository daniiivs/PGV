package UT3.Actividades.Integradora;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ListenerUsuario extends Thread {
    BufferedReader in;
    BufferedWriter out;
    JTextArea chatArea;
    String usuario;

    public ListenerUsuario(BufferedReader in, BufferedWriter out, JTextArea chatArea, String nombre) {
        this.in = in;
        this.out = out;
        this.chatArea = chatArea;
        this.usuario = nombre;
    }

    public void run() {
        String mensaje;

        try {
            while (true) {
                mensaje = in.readLine();
                if (!mensaje.isEmpty()) {
                    if (mensaje.equals("##USER##")) {
                        out.write("##USERNAME:" + usuario);
                    } else {
                        chatArea.append(mensaje + "\n");
                        out.write("MENSAJE RECIBIDO CON ÉXITO");
                    }
                    out.newLine();
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
