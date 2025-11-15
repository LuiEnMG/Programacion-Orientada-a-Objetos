package practica10;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class ThreadPoolMatta implements AutoCloseable {
    private static final Logger LOG = Logger.getLogger(ThreadPoolMatta.class.getName());
    private final List<Worker> workers = new LinkedList<>();
    private final Queue<Runnable> cola = new LinkedList<>();
    private volatile boolean cerrado = false;

    public ThreadPoolMatta(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            Worker w = new Worker("MattaWorker-" + (i+1));
            workers.add(w);
            w.start();
        }
        LOG.info("pool iniciado con " + nThreads + " hilos");
    }

    public void submit(Runnable tarea) {
        synchronized (cola) {
            if (cerrado) throw new IllegalStateException("pool cerrado");
            cola.add(tarea);
            cola.notifyAll();
        }
    }

    @Override
    public void close() {
        synchronized (cola) {
            cerrado = true;
            cola.notifyAll();
        }
        for (Worker w : workers) {
            try { w.join(2000); } catch (InterruptedException ignored) {}
        }
        LOG.info("pool cerrado");
    }

    private final class Worker extends Thread {
        Worker(String nombre) { super(nombre); }

        @Override public void run() {
            while (true) {
                Runnable tarea;
                synchronized (cola) {
                    while (cola.isEmpty() && !cerrado) {
                        try { cola.wait(); } catch (InterruptedException ignored) {}
                    }
                    if (cerrado && cola.isEmpty()) break;
                    tarea = cola.poll();
                }
                try {
                    if (tarea != null) tarea.run();
                } catch (Throwable t) {
                    LOG.severe(getName() + " error: " + t.getMessage());
                }
            }
        }
    }
}