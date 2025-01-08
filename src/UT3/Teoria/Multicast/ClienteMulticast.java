package UT3.Teoria.Multicast;

import java.io.IOException;
import java.net.*;

public class ClienteMulticast {
    public static void main(String args[]) throws IOException {
        String direccion = "225.1.1.1";
        int puerto = 6999;

        MulticastSocket m = new MulticastSocket(puerto);
        InetAddress dir = InetAddress.getByName(direccion);
        InetSocketAddress grupo = new InetSocketAddress(dir, puerto);
        NetworkInterface nif = NetworkInterface.getByInetAddress(dir);
        m.joinGroup(grupo, nif);
        System.out.println(InetAddress.getLocalHost() + " se ha unido al grupo");

        String mens = "";

        while (!mens.trim().equals("/")) {
            byte[] buf = new byte[1000];
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            m.receive(paquete);
            mens = new String(paquete.getData());
            System.out.println("Mensaje recibido desde el servidor: " + mens.trim());
        }
        m.leaveGroup(grupo, nif);
        m.close();
        System.out.println("Socket cerrado");
    }
}
