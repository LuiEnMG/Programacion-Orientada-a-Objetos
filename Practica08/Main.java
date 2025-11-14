package practica08;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        BibliotecaLEMG4428 b = new BibliotecaLEMG4428();
        b.altaUsuario("2114428", "Luis Enrique Matta Gonzalez");
        b.altaLibro(new Libro0604("ISBN-001", "Estructuras de Datos", "Weiss", "CS"));
        b.altaLibro(new Libro0604("ISBN-002", "POO en Java", "Deitel", "CS"));
        b.altaLibro(new Libro0604("ISBN-003", "Matematicas Discretas", "Rosen", "MAT"));

        while (true) {
            System.out.println("\n=== Biblioteca LEMG 4428 ===");
            System.out.println("1) Listar titulos");
            System.out.println("2) Filtrar por categoria");
            System.out.println("3) Encolar reserva (matricula)");
            System.out.println("4) Atender reserva");
            System.out.println("5) Medir tiempo: buscar por ISBN");
            System.out.println("6) Salir");
            System.out.print("Opcion: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    b.getDisponibles().stream().sorted(ComparadoresMatta.POR_TITULO_ASC)
                            .forEach(l -> System.out.println(" - " + l));
                    break;
                case "2":
                    System.out.print("Categoria: ");
                    String cat = sc.nextLine().trim();
                    b.filtrarPorCategoriaOrdenTitulo(cat).forEach(x -> System.out.println(" - " + x));
                    break;
                case "3":
                    System.out.print("Matricula: ");
                    b.encolarReserva(sc.nextLine().trim());
                    System.out.println("OK");
                    break;
                case "4":
                    System.out.println("Atendido: " + b.atenderReserva().orElse("(cola vacia)"));
                    break;
                case "5":
                    System.out.print("ISBN a buscar: ");
                    String isbn = sc.nextLine().trim();
                    long t0 = System.nanoTime();
                    boolean existe = b.buscarPorIsbn(isbn).isPresent();
                    long t1 = System.nanoTime();
                    System.out.println("Existe=" + existe + " | tiempo=" + (t1 - t0) + " ns");
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}