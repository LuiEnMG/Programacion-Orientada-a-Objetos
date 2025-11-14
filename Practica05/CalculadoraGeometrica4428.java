package practica05;

import java.util.List;

public class CalculadoraGeometrica4428 {

    public double areaTotal(List<FiguraLEMG> figuras) {
        double sum = 0;
        if (figuras == null) return 0;
        for (FiguraLEMG f : figuras) {
            if (f == null) continue;
            sum += f.area();
        }
        return sum;
    }

    public void imprimirDetalle(List<FiguraLEMG> figuras) {
        if (figuras == null) return;
        for (FiguraLEMG f : figuras) {
            String extra = "";
            
            if (f instanceof CirculoMatta) {
                CirculoMatta c = (CirculoMatta) f;
                extra = " [circulo]";
            } else if (f instanceof RectanguloMatta) {
                extra = " [rectangulo]";
            } else if (f instanceof TrianguloMatta) {
                extra = " [triangulo]";
            }
            System.out.printf("%s%s -> area=%.4f, perimetro=%.4f%n",
                    f.infoBasica(), extra, f.area(), f.perimetro());
        }
    }
}
