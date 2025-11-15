package practica10;

import java.util.logging.Logger;

public class CuentaLEMG {
    private static final Logger LOG = Logger.getLogger(CuentaLEMG.class.getName());
    private double saldo;

    public CuentaLEMG(double saldoInicial) { this.saldo = saldoInicial; }

    public synchronized void depositar(double m) {
        if (m <= 0) throw new IllegalArgumentException("monto invalido");
        double antes = saldo;
        saldo += m;
        LOG.fine("deposito m=" + m + " saldo " + antes + " -> " + saldo);
    }

    public synchronized void retirar(double m) {
        if (m <= 0) throw new IllegalArgumentException("monto invalido");
        if (m > saldo) throw new IllegalStateException("fondos insuficientes");
        double antes = saldo;
        saldo -= m;
        LOG.fine("retiro m=" + m + " saldo " + antes + " -> " + saldo);
    }

    public synchronized double getSaldo() { return saldo; }
}