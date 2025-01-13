package UT3.Actividades.Integradora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Usuario {
    // Variables para la conexión Multicast
    static MulticastSocket multicastSocket;
    static InetAddress inetAddress;
    static InetSocketAddress grupo;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    // Variables para la conexión TCP
    static Socket serverSocket;
    static InetSocketAddress serverSocketAddress;
    static String IP = "localhost";
    static int puertoServer = 7668;

    public static void main(String[] args) {
        JTextArea chatArea;

        // Recogemos el nombre del ususario por consola
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine().toUpperCase();

        // Configuramos la conexión y unión al grupo Multicast
        try {
            multicastSocket = new MulticastSocket(puertoMulticast);
            inetAddress = InetAddress.getByName(direccionMulticast);
            grupo = new InetSocketAddress(inetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            multicastSocket.joinGroup(grupo, networkInterface);
            System.out.println("Se ha unido \"" + InetAddress.getLocalHost() + "\".");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        chatArea = crearChat(nombre); // Creamos la ventana del chat

        // Configuramos y realizamos la conexión TCP al servidor
        try {
            serverSocket = new Socket();
            serverSocketAddress = new InetSocketAddress(IP, puertoServer);
            serverSocket.connect(serverSocketAddress); // Nos conectamos
            System.out.println("Se ha conectado al socket con IP " + serverSocket.getInetAddress() + ", en el puerto " + serverSocket.getLocalPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

            // Creamos y lanzamos el listener que gestionará la llegada de mensajes privados
            ListenerUsuario listenerUsuario = new ListenerUsuario(in, out, chatArea, nombre);
            listenerUsuario.start();

            enviarMensaje(nombre + " SE HA UNIDO AL CHAT EN " + serverSocket.getLocalPort()); // Enviamos un mensaje Multicast para informar a todos de la unión
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Fragmento que actúa como listener para los mensajes que lleguen por Multicast
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

    // Crea el chat para mostrar los mensajes
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

    // Envía mensajes por Multicast, que solo se utiliza cuando se conecta al grupo
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