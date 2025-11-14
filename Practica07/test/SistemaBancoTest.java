package practica07;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.*;
import java.io.*;

public class SistemaBancoTest {

    @BeforeClass
    public static void prepararDirectorioYLogger() throws Exception {
        Path testDir = Paths.get("test", "practica07");
        Files.createDirectories(testDir);

        // inicializa el logger (escribira en test/practica07/pruebas.log)
        SistemaBancoLEMG.initLogger();
    }

    private SistemaBancoLEMG sistema;

    @Before
    public void setUp() {
        sistema = new SistemaBancoLEMG();
    }

    @Test
    public void retiroExitosoNoLanza() throws Exception {
        sistema.retirar("2114428", 100.0);
        assertTrue(true);
    }

    @Test(expected = Matricula06InvalidaException.class)
    public void matriculaInvalidaLanza() throws Exception {
        sistema.retirar("A", 10.0);
    }

    @Test(expected = Usuario4428NoEncontradoException.class)
    public void usuarioNoEncontradoLanza() throws Exception {
        sistema.retirar("NOEXISTE", 10.0);
    }

    @Test(expected = Saldo04InsuficienteException.class)
    public void saldoInsuficienteLanza() throws Exception {
        sistema.retirar("2114428", 480.0);
    }

    @Test
    public void importarCsvConTryWithResources() throws Exception {
        Path testDir = Paths.get("test", "practica07");
        Path csv = testDir.resolve("clientes.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(csv)) {
            bw.write("A100;200.50\n");
            bw.write("A200;300.00\n");
        }
        int n = sistema.importarClientesCSV(csv);
        assertEquals(2, n);
    }

    @Test
    public void seGeneroArchivoDeLogEnDirectorioDeTests() throws Exception {
        Path log = Paths.get("test", "practica07", "pruebas.log");
        try { sistema.retirar("2114428", 50.0); } catch (Exception ignored) {}
        assertTrue("No se encontro el log en el directorio de tests: " + log, Files.exists(log));
        assertTrue("Log vacio", Files.size(log) > 0);
    }
}