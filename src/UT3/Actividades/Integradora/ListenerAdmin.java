package UT3.Actividades.Integradora;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;

public class ListenerAdmin extends Thread {
    JTextArea chatArea; // El chat en el que se imprimen los mensajes

    // Constructor para el listener, que recibe el chat
    public ListenerAdmin(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    // Cuerpo del hilo, que se encarga de escuchar nuevos mensajes que lleguen por Multicast
    // Al Admin le llegan todos los mensajes (incluso los que él mismo manda), pero solo va a
    // imprimir los que indiquen que un nuevo usuario se ha unido al grupo
    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje;
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                Admin.multicastSocket.receive(packet);
                mensaje = new String(packet.getData(), 0, packet.getLength());
                if (mensaje.contains(" SE HA UNIDO AL CHAT EN ")) {
                    chatArea.append("\n" + mensaje + "\n\n");
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}