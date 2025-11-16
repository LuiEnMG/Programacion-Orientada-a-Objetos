package practica11;

public class Estudiante0604 {
    private final String matricula;
    private String nombre;
    private String carrera;
    private double promedio;

    public Estudiante0604(String matricula, String nombre, String carrera, double promedio) {
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("matricula invalida");
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
        this.promedio = promedio;
    }
    public String getMatricula() { return matricula; }
    public String getNombre() { return nombre; }
    public String getCarrera() { return carrera; }
    public double getPromedio() { return promedio; }
    public void setNombre(String v) { this.nombre = v; }
    public void setCarrera(String v) { this.carrera = v; }
    public void setPromedio(double v) { this.promedio = v; }
}