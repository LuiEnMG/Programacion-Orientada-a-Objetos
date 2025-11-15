package practica10;

public class CajeroThread06 extends Thread {
    private final CuentaLEMG cuenta;
    private final double monto;
    private final int repeticiones;

    public CajeroThread06(String nombre, CuentaLEMG cta, double monto, int repeticiones) {
        super(nombre);
        this.cuenta = cta;
        this.monto = monto;
        this.repeticiones = repeticiones;
    }

    @Override
    public void run() {
        for (int i = 0; i < repeticiones; i++) {
            try {
                cuenta.retirar(monto);
            } catch (RuntimeException ex) {
                // registro basico en consola
                System.err.println(getName() + " fallo retiro: " + ex.getMessage());
            }
        }
    }
}