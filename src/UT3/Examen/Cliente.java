package UT3.Examen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

// Clase Cliente, se ejecuta solo si hay un servidor ya funcionando
public class Cliente {
    // Variables para la conexión Multicast
    static MulticastSocket multicastSocket;
    static InetAddress multicastInetAddress;
    static InetSocketAddress inetSocketAddress;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    // Variables para la conexión por TCP
    static Socket serverSocket;
    static InetSocketAddress serverSocketAddress;
    static String IP = "localhost";
    static int puertoServer = 6668;

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

            // Configuración y conexión por TCP
            serverSocket = new Socket();
            serverSocketAddress = new InetSocketAddress(IP, puertoServer);
            serverSocket.connect(serverSocketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Lanzamiento del listener que escucha nuevos mensajes por Multicast
        new ListenerMulticast(multicastSocket).start();

        // Lanzamiento del listener que escucha nuevos mensajes por TCP
        new ListenerTCPCliente(serverSocket).start();

        // Bucle para enviar peticiones al servidor por TCP
        System.out.println("--- ESCRIBA LOS MENSAJES A MANDAR POR TCP Y PRESIONE ENTER PARA ENVIARLOS ---");
        do {
            mensaje = scanner.nextLine();
            mandarMensajeServidor(mensaje);
        } while (true);
    }

    // Mandar peticiones al servidor
    public static void mandarMensajeServidor(String mensaje) {
        if (serverSocket.isClosed()) { // Si el cliente cerró el socket con 'fin', no podrá mandar más peticiones
            System.out.println("Has cerrado la conexión TCP con el servidor, ¡ya no puedes comunicarte con él!\n");
        } else {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
                out.write(mensaje);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}