package practica05;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<FiguraLEMG> figuras = new ArrayList<>();
        figuras.add(new CirculoMatta("C1", 2.0));
        figuras.add(new RectanguloMatta("R1", 3.0, 5.0));
        figuras.add(new TrianguloMatta("T1", 3.0, 4.0, 5.0));
        figuras.add(new RectanguloMatta(4.0)); // cuadrado 4x4
        figuras.add(new CirculoMatta(10.0, true)); // diametro=10 -> radio=5

        CalculadoraGeometrica4428 calc = new CalculadoraGeometrica4428();
        calc.imprimirDetalle(figuras);
        System.out.println("Area total = " + calc.areaTotal(figuras));
    }
}
