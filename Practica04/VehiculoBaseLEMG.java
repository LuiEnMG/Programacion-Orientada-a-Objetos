package practica04;

public abstract class VehiculoBaseLEMG {
    protected String marca;
    protected String modelo;
    protected int    anio;
    protected double capacidadCargaKg;

    public VehiculoBaseLEMG(String marca, String modelo, int anio, double capacidadCargaKg) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.capacidadCargaKg = capacidadCargaKg;
    }

    public abstract double calcularRendimientoKmPorLitro();
    public abstract String tipoVehiculo();

    public String descripcion() {
        return String.format("%s %s %d (cap=%.1f kg)",
                marca, modelo, anio, capacidadCargaKg);
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public double getCapacidadCargaKg() { return capacidadCargaKg; }
}