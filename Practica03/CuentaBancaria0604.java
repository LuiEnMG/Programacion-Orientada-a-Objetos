package practica03;

public class CuentaBancaria0604 {
    // ==== atributos privados ====
    private String titular;     // nombre completo
    private String matricula;   // ej: 2114428
    private double saldo;

    // Limite minimo basado en ultimos 4 digitos de la matricula
    // Aqui tomamos "4428" de 2114428, ajusta si tu regla cambia.
    private static final int ULTIMOS4 = 4428;
    private static final double LIMITE_MINIMO = ULTIMOS4 * 0.01; // 44.28 como ejemplo

    // ==== constructores ====
    public CuentaBancaria0604(String titular, String matricula, double saldoInicial) {
        this.setTitular(titular);
        this.setMatricula(matricula);
        this.setSaldo(saldoInicial);
    }

    public CuentaBancaria0604() {
        this("Luis Enrique Matta Gonzalez", "2114428", LIMITE_MINIMO);
    }

    // ==== getters ====
    public String getTitular() { return titular; }
    public String getMatricula() { return matricula; }
    public double getSaldo() { return saldo; }

    // ==== setters con validaciones basadas en la matricula ====
    public void setTitular(String titular) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("Titular invalido");
        }
        this.titular = titular;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matricula invalida");
        }
        this.matricula = matricula;
    }

    public void setSaldo(double saldo) {
        // regla de negocio: el saldo no puede ser menor al limite minimo
        if (saldo < LIMITE_MINIMO) {
            throw new IllegalArgumentException(
                "Saldo minimo requerido: " + LIMITE_MINIMO
            );
        }
        this.saldo = saldo;
    }

    // ==== operaciones de negocio ====
    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto invalido");
        this.saldo += monto;
    }

    public void retirar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto invalido");
        double nuevo = this.saldo - monto;
        if (nuevo < LIMITE_MINIMO) {
            throw new IllegalStateException(
                "No puedes bajar del limite minimo " + LIMITE_MINIMO
            );
        }
        this.saldo = nuevo;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{titular='" + titular
            + "', matricula='" + matricula
            + "', saldo=" + saldo + "}";
    }
}
