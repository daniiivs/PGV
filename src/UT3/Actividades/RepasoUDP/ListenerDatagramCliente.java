package UT3.Actividades.RepasoUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenerDatagramCliente extends Thread {
    DatagramSocket datagramSocket;

    public ListenerDatagramCliente(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void run() {
        byte[] buffer;
        DatagramPacket datagramPacket;
        String mensaje;

        while (true) {
            try {
                buffer = new byte[1024];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                mensaje = new String(buffer).trim();
                System.out.println(mensaje);
                enviarConfirmacion(datagramSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void enviarConfirmacion(DatagramSocket datagramSocket) {
        String mensaje = "MENSAJE RECIBIDO CON ÉXITO";
        try {
            DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, Cliente.datagramInetAddress, Cliente.puertoDatagram);
            datagramSocket.send(paquete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ListenerDatagramServidor extends Thread {
    DatagramSocket datagramSocket;

    public ListenerDatagramServidor(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void run() {
        byte[] buffer;
        DatagramPacket datagramPacket;
        String mensaje;
        String usuario;

        while (true) {
            try {
                buffer = new byte[1024];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                mensaje = new String(buffer).trim();
                if (mensaje.startsWith("##USER##")) {
                    usuario = new String(buffer).trim().split(":")[1];
                    Servidor.mapaPuertos.put(usuario, datagramPacket.getPort());
                } else {
                    System.out.println(mensaje);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
