package UT3.Actividades.RepasoTCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ListenerMulticastCliente extends Thread {
    MulticastSocket multicastSocket;

    public ListenerMulticastCliente(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje = "";
        do {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    mensaje = new String(packet.getData(), 0, packet.getLength());
                    if (mensaje.equalsIgnoreCase("TERMINAR")) {
                        break;
                    }
                    System.out.println(mensaje);
                } catch (IOException e) {
                    break;
                }
            }
        } while (!mensaje.equalsIgnoreCase("TERMINAR"));
    }
}

class ListenerMulticastServidor extends Thread {
    MulticastSocket multicastSocket;

    public ListenerMulticastServidor(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje = "";
        do {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    mensaje = new String(packet.getData(), 0, packet.getLength());
                    if (mensaje.equalsIgnoreCase("TERMINAR")) {
                        break;
                    } else if (mensaje.startsWith("##SERVER##")) {
                        System.out.println(mensaje);
                    }
                } catch (IOException e) {
                    break;
                }
            }
        } while (!mensaje.equalsIgnoreCase("TERMINAR"));
    }
}