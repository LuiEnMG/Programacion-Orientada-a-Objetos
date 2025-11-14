package practica06;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EmpresaTITest {

    private EmpresaTILEMG4428 emp;
    private GerenteMatta g;
    private DesarrolladorMatta d;
    private VendedorMatta v;

    @Before
    public void setUp() {
        emp = new EmpresaTILEMG4428();
        g = new GerenteMatta("Luis Enrique Matta Gonzalez", "2114428", 30000);
        d = new DesarrolladorMatta("Dev Uno", "D001", 20000);
        v = new VendedorMatta("Sales Uno", "S001", 12000);
        v.registrarVentas(100000); // comision 3%
        emp.contratar(g);
        emp.contratar(d);
        emp.contratar(v);
    }

    @Test
    public void nominaSumaPolimorfica() {
        emp.evaluarTodos(0.8);
        double total = emp.nominaTotal();
        assertTrue(total > 0);
    }

    @Test
    public void promoverSoloElegibles() {
        emp.evaluarTodos(0.9);
        double antes = emp.nominaTotal();
        emp.promoverElegibles();
        double despues = emp.nominaTotal();
        assertTrue(despues >= antes); // al promover sube base
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluarFueraDeRangoLanza() {
        d.evaluar(1.5); // invalido
    }
}