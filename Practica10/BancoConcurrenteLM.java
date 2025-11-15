package practica10;

import java.util.concurrent.*;
import java.util.logging.Logger;

public class BancoConcurrenteLM {
    private static final Logger LOG = Logger.getLogger(BancoConcurrenteLM.class.getName());

    public static void main(String[] args) throws Exception {
        LogConfigMatta.init();
        LOG.info("inicio de ejecucion del simulador");

        demoCuentaSincronizada();
        demoProductorConsumidor();
        demoExecutorService();

        LOG.info("fin de demostraciones");
        System.out.println("Fin de demostraciones. Revisa test/practica10/exec_2114428.log");
    }

    static void demoCuentaSincronizada() throws InterruptedException {
        LOG.info("== demoCuentaSincronizada ==");
        long t0 = System.nanoTime();

        CuentaLEMG cuenta = new CuentaLEMG(1000.0);
        Thread t1 = new CajeroThread06("Cajero-1", cuenta, 5.0, 1000);
        Thread t2 = new CajeroThread06("Cajero-2", cuenta, 5.0, 1000);
        Thread t3 = new Thread(new ClienteRunnable04("Cliente-1", cuenta, 5.0, 1000));
        Thread t4 = new Thread(new ClienteRunnable04("Cliente-2", cuenta, 5.0, 1000));
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();

        long t1n = System.nanoTime();
        double saldo = cuenta.getSaldo();
        LOG.info("saldo final=" + saldo + " | ns=" + (t1n - t0));
    }

    static void demoProductorConsumidor() throws InterruptedException {
        LOG.info("== demoProductorConsumidor ==");
        long t0 = System.nanoTime();

        Buffer4428 buf = new Buffer4428();
        int porProductor = 500;
        Thread p1 = new Thread(new PCJobs.Productor("P1", buf, porProductor));
        Thread p2 = new Thread(new PCJobs.Productor("P2", buf, porProductor));
        Thread c1 = new Thread(new PCJobs.Consumidor("C1", buf, porProductor));
        Thread c2 = new Thread(new PCJobs.Consumidor("C2", buf, porProductor));
        p1.start(); p2.start(); c1.start(); c2.start();
        p1.join(); p2.join(); c1.join(); c2.join();

        long t1 = System.nanoTime();
        LOG.info("buffer size final=" + buf.size() + " | ns=" + (t1 - t0));
    }

    static void demoExecutorService() throws InterruptedException {
        LOG.info("== demoExecutorService ==");
        int tareas = 20;

        long t0 = System.nanoTime();
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < tareas; i++) {
            final int k = i;
            es.submit(() -> trabajoPesado(k));
        }
        es.shutdown();
        es.awaitTermination(30, TimeUnit.SECONDS);
        long t1 = System.nanoTime();

        // pool personalizado
        long t2 = System.nanoTime();
        try (ThreadPoolMatta pool = new ThreadPoolMatta(4)) {
            for (int i = 0; i < tareas; i++) {
                final int k = i;
                pool.submit(() -> trabajoPesado(k));
            }
        }
        long t3 = System.nanoTime();

        LOG.info("ExecutorService ns=" + (t1 - t0) + " | ThreadPoolMatta ns~=" + (t3 - t2));
    }

    private static void trabajoPesado(int k) {
        double acc = 0;
        for (int i = 0; i < 200_000; i++) acc += Math.sin(i + k);
        if (k % 5 == 0) Thread.yield();
    }
}