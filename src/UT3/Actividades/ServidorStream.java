package UT3.Actividades;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServidorStream extends Thread {
    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public ServidorStream(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println(getName() + " se ha unido en " + socket.getInetAddress() + ", puerto " + socket.getPort());

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String mensaje;

            while (true) {
                mensaje = in.readLine();
                if (!mensaje.isEmpty()) {
                    if (comprobarMensaje(mensaje)) {
                        System.out.println("El " + getName() + " se desconecta a las " + getHora());
                        in.close();
                        out.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getHora() {
        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
    }

    public boolean comprobarMensaje(String mensaje) {
        if (!mensaje.contains(":")) {
            mandarMensaje("No conocemos ese mensaje.");
            return false;
        }

        if (mensaje.endsWith(":")) {
            mandarMensaje("Debe proporcionar un parámetro.");
            return false;
        }

        String[] vectorMensaje = mensaje.split(":");
        switch (vectorMensaje[0].toLowerCase()) {
            case "nom":
                mandarMensaje("Hola " + vectorMensaje[1].trim());
                return false;
            case "eco":
                mandarMensaje("Línea OK, " + vectorMensaje[1].trim());
                return false;
            case "fin":
                return true;
            default:
                mandarMensaje("No conocemos ese mensaje.");
                return false;
        }
    }

    public void mandarMensaje(String mensaje) {
        try {
            out.write(mensaje);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServidorStream servidorStream;
        int puerto = 5678;
        int i = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            while (!serverSocket.isClosed()) {
                i++;
                servidorStream = new ServidorStream(serverSocket.accept());
                servidorStream.setName("Cliente " + i);
                servidorStream.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
