package UT3.Actividades.Actividad1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Clientes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        final String IP = "localhost";
        int PORT;

        String mensaje;

        do {
            System.out.print("¿Prefieres conectarte por Stream (1) o Datagram (2)? ");
            opcion = Integer.parseInt(sc.nextLine());
            if (opcion != 1 && opcion != 2) {
                System.out.println("\nPor favor, seleccione una de las opciones disponibles (1/2)");
            }
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            PORT = 5678;

            Socket socket = new Socket();
            InetSocketAddress socketAddress = new InetSocketAddress(IP, PORT);

            try {
                socket.connect(socketAddress);
                System.out.println("Se ha conectado al socket con IP " + socket.getInetAddress() + ", en el puerto " + socket.getPort());

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
        } else {
            PORT = 5999;

            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                InetAddress servidor = InetAddress.getByName(IP);

                while (true) {
                    System.out.println("Escriba el mensaje a enviar:");
                    mensaje = sc.nextLine();

                    DatagramPacket sentPacket = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, servidor, PORT);
                    datagramSocket.send(sentPacket);

                    if (mensaje.toLowerCase().startsWith("fin:")) {
                        System.out.println("Desconectando...");
                        break;
                    }

                    byte[] respuesta = new byte[1024];
                    DatagramPacket receivedPacket = new DatagramPacket(respuesta,respuesta.length);
                    datagramSocket.receive(receivedPacket);

                    System.out.println("Respuesta:\n" + new String(receivedPacket.getData()).trim());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
