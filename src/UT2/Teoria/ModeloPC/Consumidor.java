package UT2.Teoria.ModeloPC;

import java.util.Random;

public class Consumidor extends Thread {
    Buffer buffer;

    public Consumidor(Buffer buffer, int i) {
        this.buffer = buffer;
        this.setName("Cons_" + i);
    }

    public void run() {
        Random random = new Random();
        try {
            sleep(random.nextLong(2,20) * 1000);
            for (int i = 0; i < 2; i++) {
                buffer.consumir();
                sleep(random.nextLong(2,20) * 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}


