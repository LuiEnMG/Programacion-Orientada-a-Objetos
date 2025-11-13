package practica02;

public class EstudianteLEMG 
{
    private String nombre;
    private String matricula;
    private int    edad;
    private String carrera;
    private int    semestreActual;

    public EstudianteLEMG() {
        this("Sin Nombre", "0000000", 18, "Indefinida", 1);
    }

    public EstudianteLEMG(String nombre, String matricula) {
        this(nombre, matricula, 18, "Indefinida", 1);
    }

    public EstudianteLEMG(String nombre, String matricula, int edad, String carrera, int semestreActual) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
        this.carrera = carrera;
        this.semestreActual = semestreActual;
    }

    public void inscribirMateria(String nombreMateria) {
        System.out.println(nombre + " inscribio la materia: " + nombreMateria);
    }

    public void cambiarCarrera(String nuevaCarrera) {
        if (nuevaCarrera != null && !nuevaCarrera.isBlank()) {
            this.carrera = nuevaCarrera;
        }
    }

    public void actualizarSemestre(int incremento) {
        if (incremento > 0) {
            this.semestreActual += incremento;
        }
    }

    public boolean esRegular() {
        return this.semestreActual >= 1 && this.semestreActual <= 12;
    }

    public String resumen() {
        return String.format("Estudiante{%s, %s, edad=%d, carrera=%s, sem=%d}",
                nombre, matricula, edad, carrera, semestreActual);
    }

    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }
    public int getEdad() { return edad; }
    public String getCarrera() { return carrera; }
    public int getSemestreActual() { return semestreActual; }
    public void setEdad(int edad) { if (edad > 0) this.edad = edad; }
}