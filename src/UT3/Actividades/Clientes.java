package UT3.Actividades;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Clientes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String IP = "localhost";
        final int PORT = 5678;
        String mensaje;

        Socket socket = new Socket();
        InetSocketAddress socketAddress = new InetSocketAddress(IP, PORT);

        try {
            socket.connect(socketAddress);
            System.out.println("Se ha conectado al socket con IP " + socket.getInetAddress() + ", en el puerto" + socket.getPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                System.out.println("Escriba el mensaje a enviar:");
                out.write(mensaje = sc.nextLine());
                out.newLine();
                out.flush();
                if (mensaje.toLowerCase().startsWith("fin:")) {
                    in.close();
                    out.close();
                    break;
                }
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
