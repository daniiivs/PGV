package UT3.Teoria.SocketStreamPruebas;

import javax.swing.*;
import java.awt.*;

public class Chat {
    private static JTextArea messageArea;

    public Chat(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.setVisible(true);

        editField("Creando socket del servidor en el puerto 5555");
    }



    public void editField(String message) {
        messageArea.append(message + "\n");
    }
}
