package UT3.Teoria.TCP.ChatRemoto;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorSocketStream extends Thread {
    static ServerSocket serverSocket;
    static Chat chat;
    static ArrayList<BufferedWriter> socketWriters = new ArrayList<>();

    public void run() {
        Socket newSocket;
        String mensaje;

        System.out.println("Conexión libre en " + getName());

        try {
            newSocket = serverSocket.accept();

            System.out.println("Conexión recibida en " + getName());
            BufferedReader br = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(newSocket.getOutputStream()));
            socketWriters.add(bw);

            while (true) {
                mensaje = br.readLine();
                if (!mensaje.isEmpty()) {
                    if (mensaje.contains("=>")) {
                        if (mensaje.split("=>")[1].trim().equals("exit")) {
                            chat.editField("\n" + mensaje.split("=")[0].trim() + " ha abandonado el chat.\n");
                            socketWriters.remove(bw);
                            writeUsers(mensaje.split("=")[0].trim() + " ha abandonado el chat.");
                            break;
                        }
                    }
                    chat.editField(mensaje);
                    writeUsers(mensaje);
                }
            }

            System.out.println("Cerramos el " + getName());
            newSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUsers(String message) {
        try {
            for (BufferedWriter writer : socketWriters) {
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws IOException {
        final String IP = "192.168.153.89";
        final int PORT = 5555;

        chat = new Chat("Servidor");

        InetSocketAddress inetSocketAddress = new InetSocketAddress(IP, PORT);
        serverSocket = new ServerSocket();
        serverSocket.bind(inetSocketAddress);
        int numeroCanales = 2;

        for (int i = 0; i < numeroCanales; i++) {
            ServidorSocketStream socket = new ServidorSocketStream();
            socket.setName("Socket " + (i + 1));
            socket.start();
        }
    }
}
