package practica05;

public class RectanguloMatta extends FiguraLEMG {
    private double base;
    private double altura;

    public RectanguloMatta(double lado) { this(null, lado, lado); }
    public RectanguloMatta(double base, double altura) { this(null, base, altura); }
    public RectanguloMatta(String nombre, double base, double altura) {
        super(nombre);
        setDimensiones(base, altura);
    }
    
    public void setDimensiones(double lado) { setDimensiones(lado, lado); }
    public void setDimensiones(double base, double altura) {
        if (base <= 0 || altura <= 0) throw new IllegalArgumentException("dimensiones invalidas");
        this.base = base; this.altura = altura;
    }

    @Override public double area() { return base * altura; }
    @Override public double perimetro() { return 2 * (base + altura); }
}