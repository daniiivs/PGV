package UT3.Actividades.Integradora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Usuario {
    static MulticastSocket multicastSocket;
    static InetAddress inetAddress;
    static InetSocketAddress grupo;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    static Socket serverSocket;
    static InetSocketAddress serverSocketAddress;
    static String IP = "localhost";
    static int puertoServer = 7668;

    public static void main(String[] args) {
        JTextArea chatArea;

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine().toUpperCase();

        try {
            multicastSocket = new MulticastSocket(puertoMulticast);
            inetAddress = InetAddress.getByName(direccionMulticast);
            grupo = new InetSocketAddress(inetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            multicastSocket.joinGroup(grupo, networkInterface);
            System.out.println("Se ha unido \"" + InetAddress.getLocalHost() + "\".");
            enviarMensaje(nombre + " SE HA UNIDO AL CHAT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        chatArea = crearChat(nombre);

        try {
            serverSocket = new Socket();
            serverSocketAddress = new InetSocketAddress(IP, puertoServer);
            serverSocket.connect(serverSocketAddress);
            System.out.println("Se ha conectado al socket con IP " + serverSocket.getInetAddress() + ", en el puerto " + serverSocket.getPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

            ListenerUsuario listenerUsuario = new ListenerUsuario(in, out, chatArea);
            listenerUsuario.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    private static JTextArea crearChat(String nombre) {
        // Crear la ventana principal
        JFrame frame = new JFrame("CHAT DE " + nombre.toUpperCase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Área de texto donde se mostrarán los mensajes
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Añadir componentes al frame principal
        frame.add(scrollPane, BorderLayout.CENTER);

        // Manejar el cierre de la ventana para salir del grupo
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    if (multicastSocket != null && !multicastSocket.isClosed()) {
                        multicastSocket.leaveGroup(grupo, networkInterface); // Salir del grupo multicast
                        multicastSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        // Hacer visible la ventana
        frame.setVisible(true);

        return chatArea;
    }

    public static void enviarMensaje(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, inetAddress, puertoMulticast);
            multicastSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}