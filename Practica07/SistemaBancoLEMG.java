package practica07;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;

public class SistemaBancoLEMG {

    private static final Logger LOG = Logger.getLogger(SistemaBancoLEMG.class.getName());
    private static boolean loggerInicializado = false;

    private final Map<String, Double> cuentasPorMatricula = new HashMap<>();

    public SistemaBancoLEMG() {
        cuentasPorMatricula.put("2114428", 500.00);
        cuentasPorMatricula.put("A001", 150.00);
    }

    public static void initLogger() {
        if (loggerInicializado) return;
        try {
            Path preferida = Paths.get("test", "practica07");
            Path archivoLog = Files.exists(preferida)
                    ? preferida.resolve("pruebas.log")
                    : Paths.get("pruebas.log");

            if (archivoLog.getParent() != null) {
                Files.createDirectories(archivoLog.getParent());
            }

            LOG.setUseParentHandlers(false);
            for (Handler h : LOG.getHandlers()) LOG.removeHandler(h);
            FileHandler fh = new FileHandler(archivoLog.toString(), true);
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
            LOG.setLevel(Level.INFO);

            LOG.info("Logger inicializado. Usuario: Luis Enrique Matta Gonzalez, Matricula: 2114428");
            loggerInicializado = true;
        } catch (IOException e) {
            throw new RuntimeException("No fue posible inicializar el logger", e);
        }
    }

    public void validarMatricula(String matricula) throws Matricula06InvalidaException {
        if (matricula == null || matricula.isBlank() || matricula.length() < 4) {
            LOG.warning("Matricula invalida: '" + matricula + "'");
            throw new Matricula06InvalidaException("Matricula no valida");
        }
    }

    public double obtenerSaldo(String matricula) throws Usuario4428NoEncontradoException {
        Double saldo = cuentasPorMatricula.get(matricula);
        if (saldo == null) {
            LOG.warning("Usuario no encontrado: " + matricula);
            throw new Usuario4428NoEncontradoException("Usuario no encontrado: " + matricula);
        }
        return saldo;
    }

    public void retirar(String matricula, double monto) throws ExceptionMattaBase {
        try {
            validarMatricula(matricula);
            double saldo = obtenerSaldo(matricula);
            if (monto <= 0) {
                LOG.warning("Monto invalido: " + monto);
                throw new IllegalArgumentException("Monto invalido");
            }
            double minimo = 44.28;
            if (saldo - monto < minimo) {
                LOG.warning("Saldo insuficiente para " + matricula + " intento retirar " + monto + " saldo " + saldo);
                throw new Saldo04InsuficienteException("No puedes bajar del minimo de " + minimo);
            }
            cuentasPorMatricula.put(matricula, saldo - monto);
            LOG.info("Retiro exitoso. Matricula " + matricula + " monto " + monto + " nuevo saldo " + (saldo - monto));
        } catch (Matricula06InvalidaException | Usuario4428NoEncontradoException | Saldo04InsuficienteException e) {
            LOG.severe("Error controlado: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Error no controlado", e);
            throw e;
        }
    }

    public int importarClientesCSV(Path ruta) throws ExceptionMattaBase {
        initLogger();
        int cont = 0, omitidas = 0;
        try {
            Path abs = ruta.toAbsolutePath().normalize();
            System.out.println("Leyendo CSV desde: " + abs);

            try (BufferedReader br = Files.newBufferedReader(abs, java.nio.charset.StandardCharsets.UTF_8)) {
                String line;
                boolean primera = true;
                while ((line = br.readLine()) != null) {
                    if (primera) {
                        if (!line.isEmpty() && line.charAt(0) == '\uFEFF') {
                            line = line.substring(1);
                        }
                        primera = false;
                    }
                    
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] parts = line.split("[;,]");
                    if (parts.length < 2) { omitidas++; continue; }

                    String m = parts[0].trim();
                    String sRaw = parts[1].trim();

                    if (sRaw.matches(".*\\d,\\d.*") && !sRaw.contains(".")) {
                        sRaw = sRaw.replace(',', '.');
                    }
                    double s = Double.parseDouble(sRaw);

                    cuentasPorMatricula.put(m, s);
                    cont++;
                }
            }

            LOG.info("Importacion CSV: " + cont + " registros. Omitidas: " + omitidas + ". Archivo=" + ruta);
            System.out.println("Importados " + cont + " registros. Omitidas: " + omitidas);
            return cont;

        } catch (IOException ioe) {
            LOG.log(java.util.logging.Level.SEVERE, "Fallo de IO al leer CSV: " + ruta, ioe);
            throw new ExceptionMattaBase("Error leyendo CSV: " + ruta, ioe);
        } catch (NumberFormatException nfe) {
            LOG.log(java.util.logging.Level.WARNING, "CSV con numero invalido: " + ruta, nfe);
            throw new ExceptionMattaBase("CSV con datos invalidos (revisa decimales).", nfe);
        }
    }

    public static void main(String[] args) {
        initLogger();
        SistemaBancoLEMG app = new SistemaBancoLEMG();

        if (args != null && args.length > 0) {
            String cmd = args[0].toLowerCase(Locale.ROOT);
            try {
                switch (cmd) {
                    case "saldo": {
                        if (args.length < 2) { imprimirUso(); return; }
                        String matricula = args[1];
                        double saldo = app.obtenerSaldo(matricula);
                        System.out.println("Saldo de " + matricula + ": " + saldo);
                        break;
                    }
                    case "retirar": {
                        if (args.length < 3) { imprimirUso(); return; }
                        String matricula = args[1];
                        double monto = Double.parseDouble(args[2]);
                        app.retirar(matricula, monto);
                        System.out.println("Retiro OK. Nuevo saldo: " + app.obtenerSaldo(matricula));
                        break;
                    }
                    case "importar-csv": {
                        if (args.length < 2) { imprimirUso(); return; }
                        Path ruta = Paths.get(args[1]);
                        int n = app.importarClientesCSV(ruta);
                        System.out.println("Importados " + n + " registros desde " + ruta);
                        break;
                    }
                    case "help":
                    case "-h":
                    case "--help":
                        imprimirUso();
                        break;
                    default:
                        System.out.println("Comando no reconocido: " + cmd);
                        imprimirUso();
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace(System.err);
            }
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== SistemaBancoLEMG (Luis Enrique Matta Gonzalez, 2114428) ===");
        while (true) {
            System.out.println();
            System.out.println("1) Consultar saldo");
            System.out.println("2) Retirar");
            System.out.println("3) Importar clientes desde CSV");
            System.out.println("4) Salir");
            System.out.print("Selecciona una opcion: ");
            String op = sc.nextLine().trim();
            try {
                switch (op) {
                    case "1": {
                        System.out.print("Matricula: ");
                        String m = sc.nextLine().trim();
                        double s = app.obtenerSaldo(m);
                        System.out.println("Saldo de " + m + ": " + s);
                        break;
                    }
                    case "2": {
                        System.out.print("Matricula: ");
                        String m = sc.nextLine().trim();
                        System.out.print("Monto a retirar: ");
                        double monto = Double.parseDouble(sc.nextLine().trim());
                        app.retirar(m, monto);
                        System.out.println("Retiro OK. Nuevo saldo: " + app.obtenerSaldo(m));
                        break;
                    }
                    case "3": {
                        System.out.print("Ruta del CSV (formato 'matricula;saldo' por linea) (test\\practica07\\clientes.csv en netbeans): ");
                        String entrada = sc.nextLine().trim();

                        if ((entrada.startsWith("\"") && entrada.endsWith("\"")) || (entrada.startsWith("'") && entrada.endsWith("'"))) {
                            entrada = entrada.substring(1, entrada.length() - 1);
                        }
                        Path ruta = java.nio.file.Paths.get(entrada);
                        if (!java.nio.file.Files.exists(ruta)) {
                            System.err.println("No se encontro el archivo: " + ruta.toAbsolutePath());
                            break;
                        }
                        int n = app.importarClientesCSV(ruta);
                        System.out.println("Importados " + n + " registros.");
                        break;
                    }
                    case "4":
                        System.out.println("Hasta luego.");
                        return;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void imprimirUso() {
        System.out.println("Uso:");
        System.out.println("  java practica07.SistemaBancoLEMG saldo <matricula>");
        System.out.println("  java practica07.SistemaBancoLEMG retirar <matricula> <monto>");
        System.out.println("  java practica07.SistemaBancoLEMG importar-csv <ruta>");
        System.out.println("  java practica07.SistemaBancoLEMG help");
    }
}