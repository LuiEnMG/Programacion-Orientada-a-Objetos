package practica10;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConcurrenciaTest {

    @Test
    public void cuentaSinCondicionDeCarrera() throws Exception {
        CuentaLEMG cuenta = new CuentaLEMG(1000);
        Thread t1 = new CajeroThread06("c1", cuenta, 1, 10000);
        Thread t2 = new Thread(new ClienteRunnable04("d1", cuenta, 1, 10000));
        t1.start(); t2.start(); t1.join(); t2.join();
        // Saldo final debe ser 1000
        assertEquals(1000.0, cuenta.getSaldo(), 1e-9);
    }

    @Test
    public void productorConsumidorProcesaTodo() throws Exception {
        Buffer4428 buf = new Buffer4428();
        int n = 1000;
        Thread p = new Thread(new PCJobs.Productor("P", buf, n));
        Thread c = new Thread(new PCJobs.Consumidor("C", buf, n));
        p.start(); c.start(); p.join(); c.join();
        assertEquals(0, buf.size());
    }
}