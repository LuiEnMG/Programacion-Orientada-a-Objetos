package practica05;

public class TrianguloMatta extends FiguraLEMG {
    private double a,b,c; 

    public TrianguloMatta(double a, double b, double c) { this(null, a, b, c); }
    public TrianguloMatta(String nombre, double a, double b, double c) {
        super(nombre); setLados(a,b,c);
    }
    
    public TrianguloMatta(double lado) { this(null, lado, lado, lado); }

    public void setLados(double a, double b, double c) {
        if (a<=0 || b<=0 || c<=0) throw new IllegalArgumentException("lados invalidos");
        if (a+b<=c || a+c<=b || b+c<=a) throw new IllegalArgumentException("no cumple desigualdad triangular");
        this.a=a; this.b=b; this.c=c;
    }

    @Override public double area() {
        double s = (a+b+c)/2.0;
        return Math.sqrt(s*(s-a)*(s-b)*(s-c));
    }
    @Override public double perimetro() { return a+b+c; }
}