package practica08;

import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaLEMG4428 {
    private final List<Libro0604> disponibles = new ArrayList<>();
    private final LinkedList<String> colaReservas = new LinkedList<>();
    private final Map<String, String> usuarios = new HashMap<>();
    private final Set<String> categorias = new HashSet<>();

    public boolean altaUsuario(String matricula, String nombre) {
        if (matricula == null || matricula.isBlank()) return false;
        return usuarios.putIfAbsent(matricula, nombre) == null;
    }
    public boolean bajaUsuario(String matricula) {
        return usuarios.remove(matricula) != null;
    }
    public boolean altaLibro(Libro0604 l) {
        if (l == null) return false;
        categorias.add(l.getCategoria());
        return !disponibles.contains(l) && disponibles.add(l);
    }
    public boolean bajaLibroPorIsbn(String isbn) {
        return disponibles.removeIf(l -> l.getIsbn().equals(isbn));
    }
    public Optional<Libro0604> buscarPorIsbn(String isbn) {
        return disponibles.stream().filter(l -> l.getIsbn().equals(isbn)).findFirst();
    }

    public void encolarReserva(String matricula) { colaReservas.addLast(matricula); }
    public Optional<String> atenderReserva() {
        return colaReservas.isEmpty() ? Optional.empty() : Optional.of(colaReservas.removeFirst());
    }

    public List<Libro0604> filtrarPorCategoriaOrdenTitulo(String categoria) {
        return disponibles.stream()
                .filter(l -> l.getCategoria().equalsIgnoreCase(categoria))
                .sorted(ComparadoresMatta.POR_TITULO_ASC)
                .collect(Collectors.toList());
    }

    public List<String> listarTitulosConIterador() {
        List<String> r = new ArrayList<>();
        Iterator<Libro0604> it = disponibles.iterator();
        while (it.hasNext()) r.add(it.next().getTitulo());
        return r;
    }

    public List<Libro0604> getDisponibles() { return disponibles; }
    public LinkedList<String> getColaReservas() { return colaReservas; }
    public Map<String, String> getUsuarios() { return usuarios; }
    public Set<String> getCategorias() { return categorias; }
}