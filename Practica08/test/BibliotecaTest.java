package practica08;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BibliotecaTest {
    private BibliotecaLEMG4428 b;

    @Before
    public void setUp() {
        b = new BibliotecaLEMG4428();
        b.altaUsuario("2114428", "Luis Enrique Matta Gonzalez");
        b.altaLibro(new Libro0604("A", "Alpha", "Autor1", "CS"));
        b.altaLibro(new Libro0604("B", "Beta", "Autor2", "MAT"));
        b.altaLibro(new Libro0604("C", "Gamma", "Autor3", "CS"));
    }

    @Test
    public void crudLibroYStreams() {
        // Antes de borrar: deben ser 2 CS
        List<Libro0604> csAntes = b.filtrarPorCategoriaOrdenTitulo("CS");
        assertEquals(2, csAntes.size());
        assertEquals("Alpha", csAntes.get(0).getTitulo());
        assertEquals("Gamma", csAntes.get(1).getTitulo());

        // CRUD: borrar A
        assertTrue(b.buscarPorIsbn("A").isPresent());
        assertTrue(b.bajaLibroPorIsbn("A"));
        assertFalse(b.buscarPorIsbn("A").isPresent());

        // Despues de borrar: queda 1 CS
        List<Libro0604> csDespues = b.filtrarPorCategoriaOrdenTitulo("CS");
        assertEquals(1, csDespues.size());
        assertEquals("Gamma", csDespues.get(0).getTitulo());
    }

    @Test
    public void colaReservas() {
        b.encolarReserva("2114428");
        b.encolarReserva("A002");
        assertEquals("2114428", b.atenderReserva().orElse(null));
        assertEquals("A002", b.atenderReserva().orElse(null));
        assertFalse(b.atenderReserva().isPresent());
    }
}