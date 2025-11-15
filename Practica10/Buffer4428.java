package practica10;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class Buffer4428 {
    private static final Logger LOG = Logger.getLogger(Buffer4428.class.getName());
    private static final int BUFFER_SIZE = 28;
    private final Queue<String> cola = new LinkedList<>();

    public synchronized void producir(String item) throws InterruptedException {
        while (cola.size() >= BUFFER_SIZE) {
            LOG.fine("buffer lleno, productor espera");
            wait();
        }
        cola.add(item);
        if (cola.size() % 100 == 0) LOG.fine("producidos=" + cola.size());
        notifyAll();
    }

    public synchronized String consumir() throws InterruptedException {
        while (cola.isEmpty()) {
            LOG.fine("buffer vacio, consumidor espera");
            wait();
        }
        String it = cola.remove();
        if (cola.size() % 100 == 0) LOG.fine("consumidos, quedan=" + cola.size());
        notifyAll();
        return it;
    }

    public synchronized int size() { return cola.size(); }
}