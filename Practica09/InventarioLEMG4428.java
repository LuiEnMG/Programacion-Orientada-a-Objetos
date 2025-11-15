package practica09;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class InventarioLEMG4428 {

    private final Map<String, Producto0604> porSku = new HashMap<>();

    public boolean alta(Producto0604 p) {
        if (p == null) return false;
        return porSku.putIfAbsent(p.getSku(), p) == null;
    }
    public boolean baja(String sku) { return porSku.remove(sku) != null; }
    public Optional<Producto0604> buscar(String sku) { return Optional.ofNullable(porSku.get(sku)); }
    public List<Producto0604> listar() { return new ArrayList<>(porSku.values()); }

    public List<Producto0604> porCategoriaOrdenPrecio(String categoria) {
        return porSku.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .sorted(Comparator.comparingDouble(Producto0604::getPrecio))
                .collect(Collectors.toList());
    }

    public int cargarDesdeCsvEnMismoPaquete() throws IOException {

        try (InputStream in = InventarioLEMG4428.class.getResourceAsStream("productos.csv")) {
            if (in == null) {
                throw new FileNotFoundException("No se encontro productos.csv en el mismo paquete (practica09)");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                int linea = 0, cargados = 0;
                while ((line = br.readLine()) != null) {
                    linea++;
                    String l = line.trim();
                    if (l.isEmpty()) continue;

                    String[] parts = l.split("[;,]");
                    if (parts.length < 5) {
                        System.err.println("Linea " + linea + " invalida: " + l);
                        continue;
                    }
                    String sku = parts[0].trim();
                    String nombre = parts[1].trim();
                    String categoria = parts[2].trim();
                    String precioRaw = parts[3].trim();
                    String exisRaw = parts[4].trim();

                    if (precioRaw.contains(",") && !precioRaw.contains(".")) {
                        precioRaw = precioRaw.replace(',', '.');
                    }
                    double precio = Double.parseDouble(precioRaw);
                    int exis = Integer.parseInt(exisRaw);

                    alta(new Producto0604(sku, nombre, categoria, precio, exis));
                    cargados++;
                }
                return cargados;
            }
        }
    }
}