package practica03;

public class Main {
    public static void main(String[] args) {
        // Usa tu fecha real: reemplaza DDMM en el nombre de la clase
        CuentaBancaria0604 cta = new CuentaBancaria0604(
            "Luis Enrique Matta Gonzalez", "2114428", 200.00
        );
        ClienteGonzalez cli = new ClienteGonzalez("LEMGL4428", cta);

        System.out.println(cta.toString());
        cta.depositar(50);
        cta.retirar(100);
        System.out.println("Saldo final: " + cta.getSaldo());

        System.out.println(cli.resumenCliente());
    }
}
