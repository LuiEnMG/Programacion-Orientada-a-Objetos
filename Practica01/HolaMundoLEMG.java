package holamundolemg;

public class HolaMundoLEMG {
    public static void main(String[] args) {
        String nombreCompleto = "Luis Enrique Matta Gonzalez";
        String matricula = "2114428";

        // Fecha/hora del sistema
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter fmt = 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Practica 1: Hola Mundo\n\n");
        System.out.println("Nombre: " + nombreCompleto);
        System.out.println("Matricula: " + matricula);
        System.out.println("Fecha y hora del sistema: " + ahora.format(fmt));
        System.out.println("Hola mundo");
    }
}
