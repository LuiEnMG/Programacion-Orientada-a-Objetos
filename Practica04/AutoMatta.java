package practica04;

public class AutoMatta extends VehiculoBaseLEMG {
    private int puertas;

    public AutoMatta(String marca, String modelo, int anio, double capKg, int puertas) {
        super(marca, modelo, anio, capKg);
        this.puertas = puertas;
    }

    @Override
    public double calcularRendimientoKmPorLitro() {
        return 14.5;
    }

    @Override
    public String tipoVehiculo() { return "Auto"; }

    @Override
    public String descripcion() {
        return super.descripcion() + " | puertas=" + puertas;
    }
}