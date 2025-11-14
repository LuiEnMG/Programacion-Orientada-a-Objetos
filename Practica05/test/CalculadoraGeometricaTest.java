package practica05;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class CalculadoraGeometricaTest {

    private List<FiguraLEMG> figs;
    private CalculadoraGeometrica4428 calc;

    @Before
    public void setUp() {
        figs = new ArrayList<>();
        figs.add(new CirculoMatta(2.0));
        figs.add(new RectanguloMatta(3.0, 5.0));
        figs.add(new TrianguloMatta(3.0, 4.0, 5.0));
        calc = new CalculadoraGeometrica4428();
    }

    @Test
    public void areaTotalSumaPolimorfica() {
        double total = calc.areaTotal(figs);
        assertEquals(12.5663706 + 15.0 + 6.0, total, 1e-4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void trianguloInvalidoLanza() {
        new TrianguloMatta(1.0, 2.0, 3.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void circuloRadioInvalidoLanza() {
        new CirculoMatta(-1.0);
    }
}
