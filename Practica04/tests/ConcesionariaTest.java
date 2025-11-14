package practica04;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ConcesionariaTest {
    private Concesionaria4428 con;

    @Before
    public void setUp() {
        con = new Concesionaria4428();
        con.agregar(new AutoMatta("Toyota", "Corolla", 2021, 450, 4));
        con.agregar(new MotocicletaMatta("Yamaha", "XTZ", 2020, 120, true));
    }

    @Test
    public void calculaRendimientoPromedio() {
        double r = con.rendimientoPromedio();
        assertTrue(r > 0.0);
    }

    @Test
    public void agregaNoNulos() {
        int antes = con.getInventario().size();
        con.agregar(null);
        assertEquals(antes, con.getInventario().size());
    }
}