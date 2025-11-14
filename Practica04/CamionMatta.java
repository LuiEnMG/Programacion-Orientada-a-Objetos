package practica04;

public class CamionMatta extends VehiculoBaseLEMG {

    private int ejes;

    public CamionMatta(String marca, String modelo, int anio, double capKg, int ejes) {
        super(marca, modelo, anio, capKg);
        this.ejes = ejes;
    }

    @Override
    public double calcularRendimientoKmPorLitro() {
        return 5.8;
    }

    @Override
    public String tipoVehiculo() {
        return "Camion";
    }

    @Override
    public String descripcion() {
        return super.descripcion() + " | ejes=" + ejes;
    }
}
