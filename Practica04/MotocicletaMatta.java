package practica04;

public class MotocicletaMatta extends VehiculoBaseLEMG {
    private boolean dobleProposito;

    public MotocicletaMatta(String marca, String modelo, int anio, double capKg, boolean dobleProposito) {
        super(marca, modelo, anio, capKg);
        this.dobleProposito = dobleProposito;
    }

    @Override
    public double calcularRendimientoKmPorLitro() {
        return 28.0;
    }

    @Override
    public String tipoVehiculo() { return "Motocicleta"; }

    @Override
    public String descripcion() {
        return super.descripcion() + " | dobleProposito=" + dobleProposito;
    }
}