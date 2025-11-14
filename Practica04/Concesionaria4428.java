package practica04;

import java.util.ArrayList;
import java.util.List;

public class Concesionaria4428 {
    private final List<VehiculoBaseLEMG> inventario = new ArrayList<>();

    public void agregar(VehiculoBaseLEMG v) {
        if (v != null) inventario.add(v);
    }

    public double rendimientoPromedio() {
        if (inventario.isEmpty()) return 0.0;
        double sum = 0;
        for (VehiculoBaseLEMG v : inventario) {
            sum += v.calcularRendimientoKmPorLitro();
        }
        return sum / inventario.size();
    }

    public void imprimirCatalogo() {
        System.out.println("--Catalogo--");
        for (VehiculoBaseLEMG v : inventario) {
            System.out.println("[" + v.tipoVehiculo() + "] " + v.descripcion());
        }
    }

    public List<VehiculoBaseLEMG> getInventario() { return inventario; }
}