package practica09;

import java.util.Objects;

public class Producto0604 {
    private final String sku;
    private String nombre;
    private String categoria;
    private double precio;
    private int    existencias;

    public Producto0604(String sku, String nombre, String categoria, double precio, int existencias) {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("sku invalido");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre invalido");
        if (categoria == null || categoria.isBlank()) throw new IllegalArgumentException("categoria invalida");
        if (precio < 0) throw new IllegalArgumentException("precio invalido");
        if (existencias < 0) throw new IllegalArgumentException("existencias invalidas");
        this.sku = sku;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
    }

    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getExistencias() { return existencias; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre invalido");
        this.nombre = nombre;
    }
    public void setCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) throw new IllegalArgumentException("categoria invalida");
        this.categoria = categoria;
    }
    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("precio invalido");
        this.precio = precio;
    }
    public void setExistencias(int existencias) {
        if (existencias < 0) throw new IllegalArgumentException("existencias invalidas");
        this.existencias = existencias;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto0604)) return false;
        return sku.equals(((Producto0604) o).sku);
    }
    @Override public int hashCode() { return Objects.hash(sku); }
    @Override public String toString() {
        return sku + " | " + nombre + " | " + categoria + " | $" + precio + " | x" + existencias;
    }
}