package practica05;

public class CirculoMatta extends FiguraLEMG {
    private double radio;

    public CirculoMatta(double radio) { this(null, radio); }
    public CirculoMatta(String nombre, double radio) { super(nombre); setRadio(radio); }
    public CirculoMatta(double diametro, boolean esDiametro) {
        this(null, esDiametro ? diametro / 2.0 : diametro);
    }
    public void setRadio(double radio) {
        if (radio <= 0) throw new IllegalArgumentException("radio invalido");
        this.radio = radio;
    }
    
    public void setRadio(int radioEntero) { setRadio((double) radioEntero); }

    @Override public double area() { return Math.PI * radio * radio; }
    @Override public double perimetro() { return 2 * Math.PI * radio; }
}