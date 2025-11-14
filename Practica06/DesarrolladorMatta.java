package practica06;

public class DesarrolladorMatta extends EmpleadoLEMG implements Bonificable06, Evaluable04, Promovible4428 {
    private double ultimoScore = 0.0;
    private int nivel = 1;

    public DesarrolladorMatta(String nombre, String matricula, double sueldoBase) {
        super(nombre, matricula, sueldoBase);
    }

    @Override
    public double calcularSueldo() {
        return sueldoBase + calcularBono();
    }

    @Override
    public double calcularBono() {
        double factor = 0.10 + 0.10 * ultimoScore;
        return sueldoBase * factor;
    }

    @Override
    public double evaluar(double score) {
        if (score < 0 || score > 1) throw new IllegalArgumentException("score invalido");
        ultimoScore = score;
        return ultimoScore;
    }

    @Override
    public boolean esPromovible() {
        return (ultimoScore >= 0.80 && nivel < 3);
    }

    @Override
    public void promover() {
        if (esPromovible()) {
            nivel++;
            sueldoBase *= 1.15; // 15% por promocion de nivel
        }
    }
}