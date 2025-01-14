package UT3.Actividades.RepasoTCP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class Servidor {
    static HashMap<String, Socket> mapaSockets = new HashMap<>();

    static MulticastSocket multicastSocket;
    static InetAddress multicastInetAddress;
    static InetSocketAddress inetSocketAddress;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    static ServerSocket serverSocket;
    static int puertoTcp = 7668;

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
            serverSocket = new ServerSocket(puertoTcp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new ListenerMulticastServidor(multicastSocket).start();
        new ListenerUnionCliente().start();

        System.out.println("--- ESCRIBA LOS MENSAJES Y PRESIONE ENTER PARA ENVIARLO ---");
        do {
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
        String[] valores = mensaje.split(":");
        try {
            if (mapaSockets.containsKey(valores[1])) {
                Socket userSocket = mapaSockets.get(valores[1]);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
                out.write(valores[2]);
                out.newLine();
                out.flush();
            } else {
                System.out.println("NO EXISTE EL USUARIO " + valores[1]);
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
