package practica09;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventarioLEMG4428 inv = new InventarioLEMG4428();
        try {
            int n = inv.cargarDesdeCsvEnMismoPaquete();
            System.out.println("Cargados " + n + " productos desde productos.csv (mismo paquete).");
        } catch (Exception e) {
            System.err.println("Error al cargar CSV: " + e.getMessage());
        }

        // menu simple
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Inventario LEMG 4428 ===");
            System.out.println("1) Listar todos");
            System.out.println("2) Buscar por SKU");
            System.out.println("3) Filtrar por categoria (orden precio asc)");
            System.out.println("4) Salir");
            System.out.print("Opcion: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    inv.listar().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("SKU: ");
                    System.out.println(inv.buscar(sc.nextLine().trim()).orElse(null));
                    break;
                case "3":
                    System.out.print("Categoria: ");
                    List<Producto0604> r = inv.porCategoriaOrdenPrecio(sc.nextLine().trim());
                    r.forEach(System.out::println);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}