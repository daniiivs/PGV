package UT3.Actividades.RepasoUDP;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class Servidor {
    static HashMap<String, Integer> mapaPuertos = new HashMap<>();

    static MulticastSocket multicastSocket;
    static InetAddress multicastInetAddress;
    static InetSocketAddress inetSocketAddress;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    static DatagramSocket datagramSocket;
    static int puertoDatagram = 7668;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensaje;

        try {
            multicastSocket = new MulticastSocket(puertoMulticast);
            multicastInetAddress = InetAddress.getByName(direccionMulticast);
            inetSocketAddress = new InetSocketAddress(multicastInetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(multicastInetAddress);
            multicastSocket.joinGroup(inetSocketAddress, networkInterface);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            datagramSocket = new DatagramSocket(puertoDatagram);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        new ListenerMulticastServidor(multicastSocket).start();
        new ListenerDatagramServidor(datagramSocket).start();

        do {
            System.out.println("Escriba el mensaje a enviar:");
            mensaje = scanner.nextLine();
            if (mensaje.equalsIgnoreCase("TERMINAR")) {
                break;
            } else if (mensaje.startsWith("privado:")) {
                mandarMensajePrivado(mensaje);
            } else {
                mandarMensajePublico(mensaje);
            }
        } while (true);
    }

    public static void mandarMensajePrivado(String mensaje) {
        String direccionDatagram = "localhost";
        String[] valores = mensaje.split(":");

        try {
            InetAddress datagramInetAddress = InetAddress.getByName(direccionDatagram);
            if (mapaPuertos.containsKey(valores[1])) {
                DatagramPacket paquete = new DatagramPacket(valores[2].getBytes(), valores[2].getBytes().length, datagramInetAddress, mapaPuertos.get(valores[1]));
                datagramSocket.send(paquete);
            } else {
                System.out.println("No existe ningún usuario llamado " + valores[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mandarMensajePublico(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastInetAddress, puertoMulticast);
            multicastSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
