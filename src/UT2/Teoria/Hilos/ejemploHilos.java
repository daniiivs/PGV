package UT2.Teoria.Hilos;

public class ejemploHilos extends Thread {
    MiBuffer mb;

    public ejemploHilos(MiBuffer mb) {
        this.mb = mb;
    }

    // El start ejecuta el run
    public void run() {
        mb.inc();
        System.out.println("Hola " + Thread.currentThread().getName());
        System.out.println(mb.contador);
//        Scanner sc = new Scanner(System.in);
//        String valor = sc.nextLine();
//        System.out.println("Soy " + Thread.currentThread().getName() + ", y mi valor es " + valor);
    }

    public static void main(String[] args) {
        MiBuffer mb = new MiBuffer();

        for (int i = 0; i < 4; i++) {
            Thread hilo = new ejemploHilos(mb);
            hilo.setName("Hilo " + i);
            hilo.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
