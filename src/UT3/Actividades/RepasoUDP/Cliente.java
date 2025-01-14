package UT3.Actividades.RepasoUDP;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    static MulticastSocket multicastSocket;
    static InetAddress multicastInetAddress;
    static InetSocketAddress inetSocketAddress;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    static DatagramSocket datagramSocket;
    static InetAddress datagramInetAddress;
    static String direccionDatagram = "localhost";
    static int puertoDatagram = 7668;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca su nombre de usuario:");
        String usuario = scanner.nextLine();
        System.out.println(usuario);
        scanner.close();

        try {
            multicastSocket = new MulticastSocket(puertoMulticast);
            multicastInetAddress = InetAddress.getByName(direccionMulticast);
            inetSocketAddress = new InetSocketAddress(multicastInetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(multicastInetAddress);
            multicastSocket.joinGroup(inetSocketAddress, networkInterface);

            enviarUnion(usuario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            datagramSocket = new DatagramSocket();
            datagramInetAddress = InetAddress.getByName(direccionDatagram);

            enviarUsuario("##USER##:" + usuario);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }

        new ListenerMulticastCliente(multicastSocket).start();
        new ListenerDatagramCliente(datagramSocket).start();
    }

    private static void enviarUsuario(String usuario) {
        try {
            DatagramPacket paquete = new DatagramPacket(usuario.getBytes(), usuario.getBytes().length, datagramInetAddress, puertoDatagram);
            datagramSocket.send(paquete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void enviarUnion(String usuario) {
        try {
            String mensaje = usuario + " SE HA UNIDO AL GRUPO";
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastInetAddress, puertoMulticast);
            multicastSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
