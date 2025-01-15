package UT3.Examen;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

// Clase Servidor, hay que ejecutarla primero
public class Servidor {
    // Variables para la conexión Multicast
    static MulticastSocket multicastSocket;
    static InetAddress multicastInetAddress;
    static InetSocketAddress inetSocketAddress;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    // Variables para la conexión por TCP
    static ServerSocket serverSocket;
    static int puertoTcp = 6668;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensaje;

        try {
            // Configuración y conexión al Multicast
            multicastSocket = new MulticastSocket(puertoMulticast);
            multicastInetAddress = InetAddress.getByName(direccionMulticast);
            inetSocketAddress = new InetSocketAddress(multicastInetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(multicastInetAddress);
            multicastSocket.joinGroup(inetSocketAddress, networkInterface);

            // Configuración de la conexión TCP
            serverSocket = new ServerSocket(puertoTcp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Lanzamiento del listener que administra nuevas conexiones por TCP
        new ListenerConexionCliente().start();

        // Bucle para enviar mensajes por Multicast
        System.out.println("--- ESCRIBA LOS MENSAJES Y PRESIONE ENTER PARA ENVIARLOS ---");
        do {
            mensaje = scanner.nextLine();
            mandarMensajeMulticast("multi> " + mensaje);
            System.out.println("[MENSAJE ENVIADO CON ÉXITO]\n");
        } while (true);
    }

    // Envía los mensajes por Multicast
    private static void mandarMensajeMulticast(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastInetAddress, puertoMulticast);
            multicastSocket.send(packet); // Envía el paquete con el mensaje
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}