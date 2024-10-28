package UT2.Teoria.ModeloPC;

import java.util.Random;

public class Productor extends Thread {
    Buffer buffer;

    public Productor(Buffer buffer, int i) {
        this.buffer = buffer;
        this.setName("Prod_" + i);
    }

    public void run() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            buffer.producir();
            try {
                sleep(random.nextLong(7,21) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}