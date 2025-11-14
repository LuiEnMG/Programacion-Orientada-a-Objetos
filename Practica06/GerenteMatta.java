package practica06;

public class GerenteMatta extends EmpleadoLEMG implements Bonificable06, Evaluable04, Promovible4428 {
    private double factorBono = 0.25;   // 25% del sueldo base
    private double ultimoScore = 0.0;
    private int nivel = 1;

    public GerenteMatta(String nombre, String matricula, double sueldoBase) {
        super(nombre, matricula, sueldoBase);
    }

    @Override
    public double calcularSueldo() {
        return sueldoBase + calcularBono();
    }

    @Override
    public double calcularBono() {
        return sueldoBase * factorBono * Math.max(0.5, ultimoScore);
    }

    @Override
    public double evaluar(double score) {
        if (score < 0 || score > 1) throw new IllegalArgumentException("score invalido");
        ultimoScore = score;
        return ultimoScore;
    }

    @Override
    public boolean esPromovible() {
        return ultimoScore >= 0.85 && nivel < 3;
    }

    @Override
    public void promover() {
        if (esPromovible()) {
            nivel++;
            sueldoBase *= 1.12; // 12% aumento por promocion
        }
    }
}