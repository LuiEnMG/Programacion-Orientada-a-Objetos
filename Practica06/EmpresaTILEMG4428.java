package practica06;

import java.util.*;

public class EmpresaTILEMG4428 {
    private final List<EmpleadoLEMG> plantilla = new ArrayList<>();

    public void contratar(EmpleadoLEMG e) { if (e != null) plantilla.add(e); }
    public boolean despedir(EmpleadoLEMG e) { return plantilla.remove(e); }
    public List<EmpleadoLEMG> getPlantilla() { return Collections.unmodifiableList(plantilla); }

    public double nominaTotal() {
        double sum = 0;
        for (EmpleadoLEMG e : plantilla) sum += e.calcularSueldo(); // polimorfico
        return sum;
    }

    public void evaluarTodos(double score) {
        for (EmpleadoLEMG e : plantilla) {
            if (e instanceof Evaluable04) {
                ((Evaluable04) e).evaluar(score);
            }
        }
    }

    public void promoverElegibles() {
        for (EmpleadoLEMG e : plantilla) {
            if (e instanceof Promovible4428) {
                Promovible4428 p = (Promovible4428) e;
                if (p.esPromovible()) p.promover();
            }
        }
    }

    public void imprimirResumen() {
        System.out.println("=== Empresa TI LEMG 4428 ===");
        for (EmpleadoLEMG e : plantilla) {
            System.out.println(e.resumen() + " -> sueldo=" + e.calcularSueldo());
        }
        System.out.println("Nomina total=" + nominaTotal());
    }
}