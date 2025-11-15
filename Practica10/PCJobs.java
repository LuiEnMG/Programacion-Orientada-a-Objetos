package practica10;

public final class PCJobs {
    private PCJobs() {}

    public static class Productor implements Runnable {
        private final Buffer4428 buf;
        private final int cuantos;
        private final String nombre;

        public Productor(String nombre, Buffer4428 b, int n) {
            this.nombre = nombre; this.buf = b; this.cuantos = n;
        }
        @Override public void run() {
            try {
                for (int i = 1; i <= cuantos; i++) {
                    buf.producir(nombre + "-job-" + i);
                }
            } catch (InterruptedException ignored) {}
        }
    }

    public static class Consumidor implements Runnable {
        private final Buffer4428 buf;
        private final int cuantos;
        private final String nombre;

        public Consumidor(String nombre, Buffer4428 b, int n) {
            this.nombre = nombre; this.buf = b; this.cuantos = n;
        }
        @Override public void run() {
            try {
                for (int i = 1; i <= cuantos; i++) {
                    String job = buf.consumir();
                    // procesar job
                    if (i % 200 == 0) Thread.yield();
                }
            } catch (InterruptedException ignored) {}
        }
    }
}