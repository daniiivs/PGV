package UT3.Teoria.TCP.SocketStream;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketStream {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Hola, escribe tu nombre: ");
        String myname = sc.nextLine();
        String mensaje;

        System.out.println("Creando nuevo socket cliente");
        Socket clientSocket = new Socket();
        System.out.println("Estableciendo la conexion");
        InetSocketAddress addr = new InetSocketAddress("localhost", 5555);

        clientSocket.connect(addr);

        OutputStream os = clientSocket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        System.out.println("Escribe Q para salir");

        while (true) {
            System.out.print("Mensaje: ");
            mensaje = sc.nextLine();
            if (mensaje.equals("exit")) {
                bw.write("exit");
                bw.newLine();
                bw.flush();
                System.out.println("Cerrando conexión");
                clientSocket.close();
                break;
            }
            System.out.println("Enviando mensaje...");
            bw.write(myname + " => " + mensaje);
            bw.newLine();
            bw.flush();
            System.out.println("Mensaje enviado");
        }
    }
}
