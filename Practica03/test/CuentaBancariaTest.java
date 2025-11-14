package practica03;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CuentaBancariaTest {

    private CuentaBancaria0604 cuenta;

    @Before
    public void setUp() {
        cuenta = new CuentaBancaria0604("Luis Enrique Matta Gonzalez", "2114428", 100.0);
    }

    @Test
    public void depositoIncrementaSaldo() {
        cuenta.depositar(50.0);
        assertEquals(150.0, cuenta.getSaldo(), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteSaldoInicialDebajoDelMinimo() {
        new CuentaBancaria0604("Luis", "2114428", 1.0);
    }

    @Test(expected = IllegalStateException.class)
    public void retirarNoPuedeBajarDelLimite() {
        cuenta.retirar(200.0);
    }
}