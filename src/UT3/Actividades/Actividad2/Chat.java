package UT3.Actividades.Actividad2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Chat extends Thread {
    MulticastSocket socket;
    InetAddress grupo;
    String direccion = "225.1.1.1";
    int puerto = 6999;
    JTextArea chatArea;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine();
        sc.close();

        Chat chat = new Chat(nombre);
        chat.start();
    }

    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje;
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                mensaje = new String(packet.getData(), 0, packet.getLength());
                chatArea.append(mensaje + "\n");
            } catch (IOException e) {
                System.err.print("Ha ocurrido un error");
                break;
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, grupo, puerto);
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Chat(String nombre) {
        // Configuramos el chat, uniéndonos al socket
        try {
            socket = new MulticastSocket(puerto);
            grupo = InetAddress.getByName(direccion);
            socket.joinGroup(grupo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Ventana principal
        JFrame frame = new JFrame("CHAT DE " + nombre.toUpperCase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Área de texto donde se mostrarán los mensajes
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Panel inferior para entrada de texto y botón de enviar
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Enviar");

        // Añadir los componentes al panel inferior
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Añadir componentes al frame principal
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Acción del botón "Enviar"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText().trim();
                if (!message.isEmpty()) {
                    enviarMensaje(nombre.toUpperCase() + ": " + message);
                    inputField.setText("");
                }
            }
        });

        // Acción al presionar "Enter" en el campo de texto
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        // Manejar el cierre de la ventana para salir del grupo
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.leaveGroup(grupo); // Salir del grupo multicast
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        // Hacer visible la ventana
        frame.setVisible(true);
    }
}
