package UT3.Teoria.SocketStream;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class ServidorSocketStream extends Thread {
    static ServerSocket serverSocket;

    public void run() {
        Socket newSocket;
        String mensaje;

        System.out.println("Acepta conexiones en " + getName());

        try {
            newSocket = serverSocket.accept();

            System.out.println("Conexion recibida en " + getName());
            InputStream is = newSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while (true) {
                mensaje = br.readLine();

                if (!mensaje.isEmpty()) {
                    if (mensaje.equals("exit")) {
                        break;
                    }
                    System.out.println("Mensaje recibido: " + mensaje);
                }
            }
            System.out.println("Cerramos el " + getName());
            newSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println("Creando socket del servidor");
        serverSocket = new ServerSocket(5555);
        int numeroCanales = 5;

        for (int i = 0; i < numeroCanales; i++) {
            ServidorSocketStream socket = new ServidorSocketStream();
            socket.setName("Socket " + (i + 1));
            socket.start();
        }
    }
}
