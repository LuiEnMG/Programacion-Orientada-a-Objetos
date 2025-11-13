package practica02;

public class Universidad4428 
{
    private EstudianteLEMG[] estudiantes;
    private int indiceLibre = 0;

    public Universidad4428(int capacidad) {
        if (capacidad <= 0) capacidad = 10;
        estudiantes = new EstudianteLEMG[capacidad];
    }

    public boolean agregar(EstudianteLEMG e) {
        if (e == null || indiceLibre >= estudiantes.length) return false;
        estudiantes[indiceLibre++] = e;
        return true;
    }

    public EstudianteLEMG buscar(String matricula) {
        if (matricula == null) return null;
        for (int i = 0; i < indiceLibre; i++) {
            if (matricula.equals(estudiantes[i].getMatricula())) {
                return estudiantes[i];
            }
        }
        return null;
    }

    public void mostrar() {
        System.out.println("Listado de Estudiantes\n");
        for (int i = 0; i < indiceLibre; i++) {
            System.out.println((i+1) + ". " + estudiantes[i].resumen());
        }
        if (indiceLibre == 0) System.out.println("(sin estudiantes)");
        System.out.println("\n");
    }
}