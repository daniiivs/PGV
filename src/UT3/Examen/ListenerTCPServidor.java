package UT3.Examen;

import java.io.*;
import java.net.Socket;
import java.util.Random;

// Clase ListenerTCPServidor, encargada de escuchar peticiones de cada cliente
// por TCP y enviarles una respuesta en función de dicha petición. Es un hilo
public class ListenerTCPServidor extends Thread {
    Socket socketUsuario; // El socket por el que se establece la conexión
    String nombreUsuario; // El nombre del usuario (si lo proporciona)

    // Constructor ListenerTCPServidor
    public ListenerTCPServidor(Socket socketUsuario) {
        this.socketUsuario = socketUsuario;
    }

    // Cuerpo del hilo
    public void run() {
        String mensaje;
        Random random = new Random();
        try {
            // Vías de comunicación cliente-servidor (salida y entrada)
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketUsuario.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socketUsuario.getInputStream()));

            while (!socketUsuario.isClosed()) {
                mensaje = in.readLine();
                if (mensaje != null) { // Comprobamos que el mensaje no sea null
                    mensaje = mensaje.trim();
                    try {
                        if (mensaje.toUpperCase().startsWith("CONECTAR")) { // Si empieza por conectar...
                            nombreUsuario = mensaje.split(" ")[1];
                            out.write("Bienvenido, " + nombreUsuario + ", todo listo para la transferencia TCP.\n");
                        } else if (mensaje.toUpperCase().startsWith("LEERFTP")) { // Si empieza por leerftp...
                            out.write("Ha descargado un total de " + random.nextInt(1,101) + " archivos de " + mensaje.split(" ")[1] + ".\n");
                        } else if (mensaje.equalsIgnoreCase("FIN")) { // Si es fin...
                            if (nombreUsuario == null) {
                                nombreUsuario = "cliente";
                            }
                            out.write("Desconectando... ¡hasta la próxima, " + nombreUsuario + "! ;)\n\n");
                            out.newLine();
                            out.flush();
                            socketUsuario.close(); // Cerramos el socket
                            break; // Salimos del bucle infinito, terminando el hilo
                        } else { // Si no es ninguno de los anteriores...
                            out.write("No conocemos ese mensaje. Pruebe con 'CONECTAR', 'LEEFRFTP', 'FIN'.\n");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.write("Introduzca un formato correcto para los mensajes.\n");
                    }
                    out.newLine();
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// Clase ListenerConexionCliente, que se encarga de lanzar un hilo ListenerTCPServidor
// cada vez que se conecte un nuevo cliente. Esto permite al servidor enviar mensajes
// Multicast en su main sin preocuparse de tener que aceptar nuevas conexiones. Es un hilo.
class ListenerConexionCliente extends Thread {
    public void run() {
        try {
            while (!Servidor.serverSocket.isClosed()) {
                new ListenerTCPServidor(Servidor.serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// Clase ListenerTCPCliente, que se encarga de escuchar mensajes del servidor recibidos
// por TCP. Es un hilo, y cada cliente lanza uno tras conectarse al servidor.
class ListenerTCPCliente extends Thread {
    Socket serverSocket;

    // Constructor ListenerTCPCliente
    public ListenerTCPCliente(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Cuerpo del hilo
    public void run() {
        String mensaje;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            while (true) {
                mensaje = in.readLine();
                if (mensaje != null) {
                    System.out.println(mensaje);
                    if (mensaje.toUpperCase().contains("DESCONECTANDO")) {
                        serverSocket.close(); // Si el mensaje contiene desconectando, cerramos el socket
                        break; // Sale del bucle y termina el hilo
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}