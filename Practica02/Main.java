package practica02;

public class Main 
{
    public static void main(String[] args) {
        EstudianteLEMG e1 = new EstudianteLEMG("Luis Enrique Matta Gonzalez", "2114428", 21, "Ingenieria", 5);

        EstudianteLEMG e2 = new EstudianteLEMG("Ana Cruz", "A001", 19, "Matematicas", 3);
        EstudianteLEMG e3 = new EstudianteLEMG("Raul Soto", "A002");
        EstudianteLEMG e4 = new EstudianteLEMG();
        EstudianteLEMG e5 = new EstudianteLEMG("Maya Lee", "A003", 20, "Informatica", 4);

        Universidad4428 uni = new Universidad4428(10);
        
        uni.agregar(e1);
        uni.agregar(e2);
        uni.agregar(e3);
        uni.agregar(e4);
        uni.agregar(e5);

        uni.mostrar();

        e1.inscribirMateria("Programacion Orientada a Objetos");
        e2.cambiarCarrera("Inteligencia Artificial");
        e3.actualizarSemestre(1);

        EstudianteLEMG buscado = uni.buscar("A002");
        if (buscado != null) {
            System.out.println("Encontrado: " + buscado.resumen());
        }
    }
}