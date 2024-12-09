package UT3.Teoria.PruebaIP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketStream extends Thread {
    static BufferedReader br;
    static Chat chat;
    static boolean conectado = true;

    public void run() {
        String line;
        try {
            while (conectado) {
                if ((line = br.readLine()) != null) {
                    chat.editField(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        String mensaje;

        System.out.print("Escribe tu nombre: ");
        String myname = sc.nextLine();
        chat = new Chat("Servidor - " + myname);

        System.out.println("Creando nuevo socket cliente");
        Socket clientSocket = new Socket();
        System.out.println("Estableciendo la conexion");
        InetSocketAddress addr = new InetSocketAddress("192.168.1.61", 5555);

        clientSocket.connect(addr);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        bw.write(myname + " se ha conectado.");
        bw.newLine();
        bw.flush();

        ClienteSocketStream listener = new ClienteSocketStream();
        listener.start();

        System.out.println("Escribe exit para salir");

        while (true) {
            System.out.print("Mensaje: ");
            mensaje = sc.nextLine();
            System.out.println("Enviando mensaje...");
            bw.write(myname + " => " + mensaje);
            bw.newLine();
            bw.flush();
            System.out.println("Mensaje enviado");
            if (mensaje.equals("exit")) {
                System.out.println("Cerrando conexión");
                conectado = false;

                try {
                    listener.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                clientSocket.close();
                break;
            }
        }
        System.exit(0);
    }
}
