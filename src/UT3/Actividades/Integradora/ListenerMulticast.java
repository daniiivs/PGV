package UT3.Actividades.Integradora;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;

public class ListenerMulticast extends Thread {
    JTextArea chatArea;

    public ListenerMulticast(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje;
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                Usuario.multicastSocket.receive(packet);
                mensaje = new String(packet.getData(), 0, packet.getLength());
                chatArea.append(mensaje + "\n");
            } catch (IOException e) {
                break;
            }
        }
    }
}
