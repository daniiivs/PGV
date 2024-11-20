package UT3.Teoria.SocketStream;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketStream extends Thread {
    static BufferedReader br;

    public void run() {
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Escribe tu nombre: ");
        String myname = sc.nextLine();
        String mensaje;

        System.out.println("Creando nuevo socket cliente");
        Socket clientSocket = new Socket();
        System.out.println("Estableciendo la conexion");
        InetSocketAddress addr = new InetSocketAddress("localhost", 5555);

        clientSocket.connect(addr);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ClienteSocketStream listener = new ClienteSocketStream();
        listener.start();

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
