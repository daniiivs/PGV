package UT3.Actividades.RepasoTCP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class ListenerTcpCliente extends Thread {
    Socket serverSocket;
    String usuario;

    public ListenerTcpCliente(Socket serverSocket, String usuario) {
        this.serverSocket = serverSocket;
        this.usuario = usuario;
    }

    public void run() {
        String mensaje;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            while (true) {
                mensaje = in.readLine();
                if (mensaje != null) {
                    if (mensaje.equalsIgnoreCase("##USER##")) {
                        out.write("##USER##:" + usuario);
                        out.newLine();
                        out.flush();
                    } else {
                        System.out.println(mensaje);
                        out.write("MENSAJE RECIBIDO CON ÉXITO");
                        out.newLine();
                        out.flush();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

class ListenerTcpServidor extends Thread {
    Socket socketUsuario;

    public ListenerTcpServidor(Socket socketUsuario) {
        this.socketUsuario = socketUsuario;
    }

    public void run() {
        String mensaje;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketUsuario.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socketUsuario.getInputStream()));

            out.write("##USER##");
            out.newLine();
            out.flush();

            while (true) {
                mensaje = in.readLine();
                if (mensaje != null) {
                    if (mensaje.startsWith("##USER##:")) {
                        Servidor.mapaSockets.put(mensaje.split(":")[1], socketUsuario);
                    } else {
                        System.out.println(mensaje);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ListenerUnionCliente extends Thread {
    public void run() {
        try {
            while (!Servidor.serverSocket.isClosed()) {
                new ListenerTcpServidor(Servidor.serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}