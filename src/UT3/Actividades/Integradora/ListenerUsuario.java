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

    // Constructor para ListenerUsuario
    public ListenerUsuario(BufferedReader in, BufferedWriter out, JTextArea chatArea, String nombre) {
        this.in = in;
        this.out = out;
        this.chatArea = chatArea;
        this.usuario = nombre;
    }

    // Cuerpo del hilo, encargado de escuchar mensajes que llegan por TCP y, en caso de recoger alguno,
    // envía una confirmación a travésd el mismo socket. Cuando el usuario recibe por primera vez el
    // mensaje '##USER##' (enviado por el servidor cuando el usuario se une) responderá con su nombre
    public void run() {
        String mensaje;

        try {
            while (true) {
                mensaje = in.readLine();
                if (!mensaje.isEmpty()) {
                    if (mensaje.equals("##USER##")) {
                        out.write("##USERNAME:" + usuario); // El usuario le responde al servidor con su nombre
                    } else {
                        chatArea.append(mensaje + "\n");
                        out.write("MENSAJE RECIBIDO CON ÉXITO"); // El usuario le indica al servidor que ha recibido el mensaje con éxito
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
