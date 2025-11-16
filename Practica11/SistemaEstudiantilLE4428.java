package practica11;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaEstudiantilLE4428 extends JFrame {

    private final JLabel logoA = new JLabel();
    private final JLabel logoB = new JLabel();
    private final ModeloTablaEstudiantes model = new ModeloTablaEstudiantes();
    private final JTable table = new JTable(model);

    public SistemaEstudiantilLE4428() {
        super("SistemaEstudiantilLE4428");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);

        RecursosMatta.ensureLogos();
        RecursosMatta.ensureStyle();
        Font btnFont = RecursosMatta.loadButtonFont();

        logoA.setIcon(RecursosMatta.loadLogo(RecursosMatta.LOGO1, 128));
        logoB.setIcon(RecursosMatta.loadLogo(RecursosMatta.LOGO2, 128));
        logoA.setPreferredSize(new Dimension(128,128));
        logoB.setPreferredSize(new Dimension(128,128));

        JPanel leftLogoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        leftLogoPanel.add(logoA);

        JPanel rightLogoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        rightLogoPanel.add(logoB);

        JToolBar tb = new JToolBar(); tb.setFloatable(false);
        JButton btnCargar  = new JButton("Cargar CSV");
        JButton btnNuevo   = new JButton("Nuevo");
        JButton btnEliminar= new JButton("Eliminar");
        JButton btnGuardar = new JButton("Guardar CSV");
        for (JButton b : new JButton[]{btnCargar, btnNuevo, btnEliminar, btnGuardar}) b.setFont(btnFont);
        tb.add(btnCargar); tb.addSeparator(); tb.add(btnNuevo); tb.add(btnEliminar); tb.addSeparator(); tb.add(btnGuardar);

        JPanel north = new JPanel(new BorderLayout());
        north.add(leftLogoPanel, BorderLayout.WEST);
        north.add(rightLogoPanel, BorderLayout.EAST);
        north.add(tb, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        
        table.setFillsViewportHeight(true);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        btnCargar.addActionListener(e -> cargarDesdeCSV());
        btnNuevo.addActionListener(e -> {
            String m = JOptionPane.showInputDialog(this, "Matricula (solo enteros):", "");
            if (m == null) return; // cancelar
            m = m.trim();

            // validacion
            if (!m.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "La matricula debe contener solo digitos (0-9).",
                        "Error de validacion",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (m.length() > 18) {
                    JOptionPane.showMessageDialog(this,
                            "Matricula demasiado larga.",
                            "Error de validacion",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.add(new Estudiante0604(m, "Nombre", "Carrera", 0.0));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnEliminar.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) model.remove(r); });
        btnGuardar.addActionListener(e -> guardarCSV());

        cargarDesdeCSV();
    }

    private void cargarDesdeCSV() {
        Path in = RecursosMatta.csvInputRuntime();
        List<String> errores = new ArrayList<>();
        int cargados = 0;

        while (model.getRowCount() > 0) model.remove(0);

        try {
            if (!Files.exists(in)) {
                JOptionPane.showMessageDialog(this, "No se encontro: " + in.toAbsolutePath() + "\nLa tabla inicia vacia.");
                return;
            }
            try (BufferedReader br = Files.newBufferedReader(in, StandardCharsets.UTF_8)) {
                String line; int n = 0;
                while ((line = br.readLine()) != null) {
                    n++;
                    String s = line.trim();
                    if (s.isEmpty() || s.startsWith("#")) continue;
                    if (s.length() > 0 && s.charAt(0) == '\uFEFF') s = s.substring(1); // BOM
                    String[] parts = s.split("[;,]");
                    if (parts.length < 4) { errores.add("linea " + n + " invalida"); continue; }
                    String matricula = parts[0].trim();
                    String nombre    = parts[1].trim();
                    String carrera   = parts[2].trim();
                    String promRaw   = parts[3].trim().replace(',', '.');
                    double promedio;
                    try { promedio = Double.parseDouble(promRaw); } catch (NumberFormatException ex) { errores.add("linea " + n + " promedio invalido"); continue; }
                    try {
                        model.add(new Estudiante0604(matricula, nombre, carrera, promedio));
                        cargados++;
                    } catch (IllegalArgumentException ex) {
                        errores.add("linea " + n + " " + ex.getMessage());
                    }
                }
            }
            String msg = "Registros cargados: " + cargados + (errores.isEmpty() ? "" : " | Errores: " + errores.size());
            JOptionPane.showMessageDialog(this, msg + (errores.isEmpty() ? "" : "\n" + String.join("\n", errores)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCSV() {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }

            Path out = RecursosMatta.csvInputRuntime();

            Path tmp = out.resolveSibling(out.getFileName().toString() + ".tmp");

            try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
                bw.write("# matricula;nombre;carrera;promedio\n");
                for (Estudiante0604 s : model.all()) {
                    bw.write(s.getMatricula() + ";" + s.getNombre() + ";" + s.getCarrera() + ";" + s.getPromedio() + "\n");
                }
            }

            Files.move(tmp, out, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            JOptionPane.showMessageDialog(this, "Guardado: " + out.toAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error guardando CSV: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaEstudiantilLE4428().setVisible(true));
    }
}