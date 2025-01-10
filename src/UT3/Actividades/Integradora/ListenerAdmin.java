package UT3.Actividades.Integradora;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;

public class ListenerAdmin extends Thread {
    JTextArea chatArea;

    public ListenerAdmin(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

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