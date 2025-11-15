package practica09;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class InventarioTest {

    private static Path pkgDir;
    private static Path csvPath;

    private static Path resolvePaqueteDir() throws Exception {
        URL url = InventarioLEMG4428.class.getResource("");
        if (url != null && "file".equalsIgnoreCase(url.getProtocol())) {
            return Paths.get(url.toURI());
        }
        Path fallback = Paths.get("practica09").toAbsolutePath().normalize();
        Files.createDirectories(fallback);
        return fallback;
    }

    private static void writeCsv(String content) throws IOException {
        Files.createDirectories(pkgDir);
        Files.write(csvPath, content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @BeforeClass
    public static void suiteSetup() throws Exception {
        pkgDir  = resolvePaqueteDir();
        csvPath = pkgDir.resolve("productos.csv");

        String base =
                "# sku;nombre;categoria;precio;existencias\n" +
                "SKU-001;Cuaderno Profesional;Papeleria;45.90;120\n" +
                "SKU-002;Pluma Gel Azul;Papeleria;18.50;300\n" +
                "SKU-003;Calculadora Cientifica;Electronica;399.00;40\n" +
                "SKU-004;Laptop 14;Electronica;9800.00;15\n" +
                "SKU-005;USB 64GB;Electronica;129.99;80\n";
        writeCsv(base);
    }

    private InventarioLEMG4428 inv;

    @Before
    public void setUp() {
        inv = new InventarioLEMG4428();
    }

    @Test
    public void cargaDesdeCsvBasico() throws Exception {
        String data =
                "# base valida\n" +
                "SKU-001;Alpha;CS;10.00;5\n" +
                "SKU-002;Beta;MAT;15.50;7\n" +
                "SKU-003;Gamma;CS;12.00;3\n";
        writeCsv(data);

        int n = inv.cargarDesdeCsvEnMismoPaquete();
        assertEquals(3, n);
        assertTrue(inv.buscar("SKU-001").isPresent());
        assertTrue(inv.buscar("SKU-002").isPresent());
        assertTrue(inv.buscar("SKU-003").isPresent());
    }

    @Test
    public void filtrarCategoriaOrdenPrecio() throws Exception {
        String data =
                "# electronica ordenable por precio\n" +
                "E-01;Mouse;Electronica;99.99;10\n" +
                "E-02;USB 32GB;Electronica;79.50;20\n" +
                "E-03;Laptop 15;Electronica;15000.00;5\n" +
                "P-01;Lapicero;Papeleria;9.00;100\n";
        writeCsv(data);

        inv.cargarDesdeCsvEnMismoPaquete();
        List<Producto0604> elec = inv.porCategoriaOrdenPrecio("Electronica");
        assertEquals(3, elec.size());
        for (int i = 1; i < elec.size(); i++) {
            assertTrue(elec.get(i - 1).getPrecio() <= elec.get(i).getPrecio());
        }
    }

    @Test
    public void crudAltaYBaja() throws Exception {
        String data =
                "A-1;Item A;CS;1.00;1\n" +
                "B-1;Item B;CS;2.00;2\n";
        writeCsv(data);

        inv.cargarDesdeCsvEnMismoPaquete();
        
        Producto0604 p = new Producto0604("X-1", "Item X", "CS", 3.00, 3);
        assertTrue(inv.alta(p));
        assertTrue(inv.buscar("X-1").isPresent());

        assertTrue(inv.baja("X-1"));
        assertFalse(inv.buscar("X-1").isPresent());
    }

    @Test
    public void soportaSeparadorComaYOComaDecimal() throws Exception {

        String data =
                "C-1,Item C,CS,10,00,5\n" +     
                "C-2;Item D;CS;20,50;6\n" +     
                "C-3,Item E,CS,5.75,10\n";      
        writeCsv(data);

        int n = inv.cargarDesdeCsvEnMismoPaquete();
        assertEquals(3, n);
        assertTrue(inv.buscar("C-1").isPresent());
        assertTrue(inv.buscar("C-2").isPresent());
        assertTrue(inv.buscar("C-3").isPresent());
    }

    @Test
    public void ignoraLineasVaciasYComentarios() throws Exception {
        String data =
                "\n" +
                "# comentario\n" +
                "Z-1;Uno;CS;1.00;1\n" +
                "\n" +
                "Z-2;Dos;CS;2.00;2\n";
        writeCsv(data);

        int n = inv.cargarDesdeCsvEnMismoPaquete();
        assertEquals(2, n);
    }
}
