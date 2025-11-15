package practica10;

public class ClienteRunnable04 implements Runnable {
    private final CuentaLEMG cuenta;
    private final double monto;
    private final int repeticiones;
    private final String nombre;

    public ClienteRunnable04(String nombre, CuentaLEMG cta, double monto, int repeticiones) {
        this.nombre = nombre;
        this.cuenta = cta;
        this.monto = monto;
        this.repeticiones = repeticiones;
    }

    @Override
    public void run() {
        for (int i = 0; i < repeticiones; i++) {
            cuenta.depositar(monto);
            // simulacion de trabajo
            if (i % 100 == 0) Thread.yield();
        }
        System.out.println(nombre + " termino depositos.");
    }
}