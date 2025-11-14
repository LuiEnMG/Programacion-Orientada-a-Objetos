package practica06;

public abstract class EmpleadoLEMG {
    protected String nombre;
    protected String matricula;
    protected double sueldoBase;

    public EmpleadoLEMG(String nombre, String matricula, double sueldoBase) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.sueldoBase = sueldoBase;
    }

    public abstract double calcularSueldo();  // contrato polimorfico
    public String resumen() {
        return getClass().getSimpleName() + "{" + nombre + ", " + matricula + ", base=" + sueldoBase + "}";
    }

    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }
    public double getSueldoBase() { return sueldoBase; }
    public void setSueldoBase(double sueldoBase) { this.sueldoBase = sueldoBase; }
}