package practica11;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class ModeloTablaEstudiantes extends AbstractTableModel {
    private final String[] cols = {"Matricula", "Nombre", "Carrera", "Promedio"};
    private final List<Estudiante0604> data = new ArrayList<>();
    private final Set<String> matriculas = new HashSet<>();

    public void add(Estudiante0604 e) {
        if (matriculas.contains(e.getMatricula())) throw new IllegalArgumentException("matricula repetida");
        data.add(e); matriculas.add(e.getMatricula());
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }
    public void remove(int row) {
        if (row>=0 && row<data.size()) {
            matriculas.remove(data.get(row).getMatricula());
            data.remove(row); fireTableRowsDeleted(row,row);
        }
    }
    public Estudiante0604 get(int row) { return data.get(row); }
    public List<Estudiante0604> all() { return data; }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }
    @Override public Object getValueAt(int r, int c) {
        Estudiante0604 e = data.get(r);
        switch (c) {
            case 0: return e.getMatricula();
            case 1: return e.getNombre();
            case 2: return e.getCarrera();
            case 3: return e.getPromedio();
            default: return "";
        }
    }
    @Override public boolean isCellEditable(int r, int c) { return c != 0; } // matricula no editable
    @Override
    public void setValueAt(Object v, int r, int c) {
        Estudiante0604 e = data.get(r);
        switch (c) {
            case 1:
                e.setNombre(String.valueOf(v));
                break;
            case 2:
                e.setCarrera(String.valueOf(v));
                break;
            case 3: {
                double nuevo;
                try {
                    nuevo = Double.parseDouble(String.valueOf(v).replace(',', '.'));
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Promedio invalido. Debe ser numero entre 0 y 100.",
                            "Error de validacion",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                    fireTableCellUpdated(r, c); // revertir visualmente
                    return;
                }
                if (nuevo < 0.0 || nuevo > 100.0) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Promedio fuera de rango (0..100).",
                            "Error de validacion",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                    fireTableCellUpdated(r, c);
                    return;
                }
                e.setPromedio(nuevo);
                break;
            }
        }
        fireTableCellUpdated(r, c);
    }
}