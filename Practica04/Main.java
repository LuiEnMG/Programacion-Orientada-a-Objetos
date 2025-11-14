package practica04;

public class Main {
    public static void main(String[] args) {
        Concesionaria4428 con = new Concesionaria4428();

        con.agregar(new AutoMatta("Toyota", "Corolla", 2021, 450, 4));
        con.agregar(new MotocicletaMatta("Yamaha", "XTZ", 2020, 120, true));
        con.agregar(new CamionMatta("Freightliner", "M2", 2019, 12000, 3));

        con.imprimirCatalogo();
        System.out.println("Rendimiento promedio: " + con.rendimientoPromedio() + " km/l");
    }
}