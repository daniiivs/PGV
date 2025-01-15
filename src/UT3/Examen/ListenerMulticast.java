package UT3.Examen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

// Clase ListenerMulticast, encargada de escuchar mensajes nuevos que lleguen por
// Multicast. Cada cliente tendrá uno, y los imprimirá por pantalla. El servidor no
// tiene porque no necesita imprimir los mensajes que él mismo manda.
public class ListenerMulticast extends Thread {
    MulticastSocket multicastSocket;

    // Constructor ListenerMulticast
    public ListenerMulticast(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    // Cuerpo del hilo
    public void run() {
        byte[] buffer = new byte[1024];
        String mensaje;
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet); // Recibe el paquete
                mensaje = new String(packet.getData(), 0, packet.getLength());
                System.out.println(mensaje); // Imprime el mensaje
            } catch (IOException e) {
                break;
            }
        }
    }
}