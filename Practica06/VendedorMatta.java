package practica06;

public class VendedorMatta extends EmpleadoLEMG implements Bonificable06, Evaluable04 {
    private double ultimoScore = 0.0;
    private double comisionMensual = 0.0;

    public VendedorMatta(String nombre, String matricula, double sueldoBase) {
        super(nombre, matricula, sueldoBase);
    }

    public void registrarVentas(double montoVentas) {
        if (montoVentas < 0) throw new IllegalArgumentException("ventas invalidas");
        // comision basica 3% de ventas
        comisionMensual += montoVentas * 0.03;
    }

    @Override
    public double calcularSueldo() {
        return sueldoBase + calcularBono() + comisionMensual;
    }

    @Override
    public double calcularBono() {
        double factor = 0.05 + 0.07 * ultimoScore;
        return sueldoBase * factor;
    }

    @Override
    public double evaluar(double score) {
        if (score < 0 || score > 1) throw new IllegalArgumentException("score invalido");
        ultimoScore = score;
        return ultimoScore;
    }
}
