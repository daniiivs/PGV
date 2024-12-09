package UT3.Actividades;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;

public class ServidorDatagram extends Thread {
    DatagramSocket socket;
    DatagramPacket receivedPacket;

    public ServidorDatagram(DatagramSocket socket, DatagramPacket receivedPacket) {
        this.socket = socket;
        this.receivedPacket = receivedPacket;
    }

    public void run() {
        System.out.println("Mensaje recibido en " + receivedPacket.getAddress() + ", puerto " + receivedPacket.getPort());
        if (!new String(receivedPacket.getData()).trim().isEmpty()) {
            if (comprobarMensaje(new String(receivedPacket.getData()))) {
                System.out.println("El cliente del puerto " + receivedPacket.getPort() + " se desconecta a las " + getHora());
            }
        }
    }


    private String getHora() {
        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
    }

    public boolean comprobarMensaje(String mensaje) {
        if (mensaje.equalsIgnoreCase("fin:")) {
            return true;
        }

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
            byte[] salida;

            salida = mensaje.getBytes();
            DatagramPacket sentPacket = new DatagramPacket(salida, salida.length, receivedPacket.getAddress(), receivedPacket.getPort());
            socket.send(sentPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServidorDatagram servidorDatagram;
        int puerto = 5999;
        int i = 0;

        try {
            DatagramSocket datagramSocket = new DatagramSocket(puerto);
            while (!datagramSocket.isClosed()) {
                byte[] entrada = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(entrada, entrada.length);
                datagramSocket.receive(receivedPacket);

                servidorDatagram = new ServidorDatagram(datagramSocket, receivedPacket);
                servidorDatagram.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
