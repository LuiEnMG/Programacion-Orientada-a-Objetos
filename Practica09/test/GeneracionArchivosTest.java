package practica09;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneracionArchivosTest {

    private static final String MATRICULA = "2114428";
    private static final String DIA = "06";
    private static final String MES = "04";
    private static final String APELLIDO_PATERNO = "Matta";

    private static final String NOMBRE_TXT = "datos_" + MATRICULA + ".txt";
    private static final String NOMBRE_DAT = "backup_" + DIA + MES + ".dat";
    private static final String NOMBRE_CSV = "log_" + APELLIDO_PATERNO + ".csv";

    private static Path pkgDir;

    private static Path resolvePaqueteDir() throws Exception {
        URL url = InventarioLEMG4428.class.getResource("");
        if (url != null && "file".equalsIgnoreCase(url.getProtocol())) {
            return Paths.get(url.toURI());
        }
        Path fallback = Paths.get("practica09").toAbsolutePath().normalize();
        Files.createDirectories(fallback);
        return fallback;
    }

    @BeforeClass
    public static void beforeAll() throws Exception {
        pkgDir = resolvePaqueteDir();
        Files.createDirectories(pkgDir);
    }

    @Test
    public void genera_datos_txt() throws IOException {
        Path txt = pkgDir.resolve(NOMBRE_TXT);
        String contenido =
                "Alumno: Luis Enrique Matta Gonzalez\n" +
                "Matricula: " + MATRICULA + "\n" +
                "FechaNacimiento: " + DIA + "/" + MES + "\n" +
                "Generado: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n";
        Files.write(txt, contenido.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        assertTrue("No existe " + txt, Files.exists(txt));
        assertTrue("Archivo vacio " + txt, Files.size(txt) > 0);
    }

    @Test
    public void genera_backup_dat_binario() throws IOException {
        Path dat = pkgDir.resolve(NOMBRE_DAT);
        byte[] header = new byte[] { 0x42, 0x4B, 0x50, 0x06, 0x04 };
        byte[] payload = ("MATRICULA=" + MATRICULA).getBytes(StandardCharsets.UTF_8);

        Files.write(dat, header, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.write(dat, payload, StandardOpenOption.APPEND);

        assertTrue("No existe " + dat, Files.exists(dat));
        assertTrue("Archivo vacio " + dat, Files.size(dat) >= header.length + payload.length);
    }

    @Test
    public void genera_log_csv() throws IOException {
        Path csv = pkgDir.resolve(NOMBRE_CSV);
        String encabezado = "timestamp,evento,detalle\n";
        if (!Files.exists(csv) || Files.size(csv) == 0) {
            Files.write(csv, encabezado.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        String linea = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                + ",CREACION,log inicial para " + APELLIDO_PATERNO + "\n";
        Files.write(csv, linea.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        assertTrue("No existe " + csv, Files.exists(csv));
        assertTrue("CSV vacio " + csv, Files.size(csv) > 0);
    }
}