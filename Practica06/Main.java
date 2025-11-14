package practica06;

public class Main {
    public static void main(String[] args) {
        EmpresaTILEMG4428 emp = new EmpresaTILEMG4428();

        EmpleadoLEMG g = new GerenteMatta("Luis Enrique Matta Gonzalez", "2114428", 30000);
        EmpleadoLEMG d = new DesarrolladorMatta("Dev Uno", "D001", 20000);
        VendedorMatta v = new VendedorMatta("Sales Uno", "S001", 12000);

        v.registrarVentas(150000); // comision 3%

        emp.contratar(g);
        emp.contratar(d);
        emp.contratar(v);

        emp.evaluarTodos(0.9);      // dispara bonos altos
        emp.promoverElegibles();    // gerente y dev pueden promover
        emp.imprimirResumen();
    }
}