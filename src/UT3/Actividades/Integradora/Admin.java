package UT3.Actividades.Integradora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Random;

public class Admin extends Thread {
    static JTextArea chatArea; // El chat en el que se muestran los mensajes e información para el admin
    static Random random = new Random(); // Para generar números aleatorios

    // Variables para la conexión Multicast
    static MulticastSocket multicastSocket;
    static InetAddress inetAddress;
    static InetSocketAddress grupo;
    static NetworkInterface networkInterface;
    static String direccionMulticast = "225.10.10.10";
    static int puertoMulticast = 6998;

    // Variables para la conexión TCP
    static int puerto = 7668;
    Socket TCPSocket;
    BufferedReader in;
    BufferedWriter out;

    // Lista de usuarios y de sus respectivos sockets
    static HashMap<String, Socket> mapaUsuarios = new HashMap<>();

    // Constructor para Admin, que se usa cada vez que se une un cliente por TCP
    public Admin(Socket socket) {
        this.TCPSocket = socket;
    }

    // Hilo que actúa como Listener para cada cliente por TCP
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(TCPSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(TCPSocket.getOutputStream()));
            String mensaje;
            String aviso = "##USER##";

            out.write(aviso);
            out.newLine();
            out.flush();

            while (true) {
                mensaje = in.readLine();
                if (!mensaje.isEmpty()) {
                    if (mensaje.startsWith("##USERNAME:")) {
                        mapaUsuarios.put(mensaje.split(":")[1], TCPSocket);
                    } else {
                        chatArea.append(mensaje + "\n\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            multicastSocket = new MulticastSocket(puertoMulticast);
            inetAddress = InetAddress.getByName(direccionMulticast);
            grupo = new InetSocketAddress(inetAddress, puertoMulticast);
            networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            multicastSocket.joinGroup(grupo, networkInterface);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        chatArea = crearChat();
        ListenerAdmin listenerMulticast = new ListenerAdmin(chatArea);
        listenerMulticast.start();

        Admin serverStream;
        int i = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            while (!serverSocket.isClosed()) {
                i++;
                serverStream = new Admin(serverSocket.accept());
                serverStream.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JTextArea crearChat() {
        // Crear la ventana principal
        JFrame frame = new JFrame("ADMIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Área de texto donde se mostrarán los mensajes
        JTextArea chatArea = new JTextArea();
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
                    if (message.toLowerCase().startsWith("privado:")) {
                        try {
                            String[] valores = message.split(":");
                            if (!mapaUsuarios.containsKey(valores[1].toUpperCase())) {
                                chatArea.append("NO HAY NINGÚN USUARIO LLAMADO " + valores[1].toUpperCase() + "\n");
                            } else {
                                enviarMensajePrivado(valores[1].toUpperCase(), valores[2]);
                                chatArea.append("HAS ENVIADO: " + valores[2] + " A " + valores[1].toUpperCase() + "\n");
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            chatArea.append("\nINTRODUCE EL FORMATO CORRECTO (PRIVADO:USUARIO:MENSAJE)\n\n");
                        }
                    } else if (message.equalsIgnoreCase("descargar")) {
                        int number = random.nextInt(1, 101);
                        enviarMensaje("Se han descargado " + number + " archivos");
                        chatArea.append("\nHAS DESCARGADO " + number + " ARCHIVOS\n\n");
                    } else {
                        enviarMensaje(message);
                        chatArea.append("HAS ENVIADO: " + message + "\n");
                    }
                    inputField.setText("");
                }
            }
        });

        // Acción al presionar Enter en el campo de texto
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        // Manejar el cierre de la ventana para salir del grupo
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (multicastSocket != null && !multicastSocket.isClosed()) {
                    multicastSocket.close();
                }
                System.exit(0);
            }
        });

        // Hacer visible la ventana
        frame.setVisible(true);

        return chatArea;
    }

    public static void enviarMensajePrivado(String user, String message) {
        if (mapaUsuarios.containsKey(user)) {
            Socket socket = mapaUsuarios.get(user);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO Indicar que no contiene
        }
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
