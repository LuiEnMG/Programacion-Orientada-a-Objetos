package practica08;

import java.util.Objects;

public class Libro0604 implements Comparable<Libro0604> {
    private final String isbn;
    private String titulo;
    private String autor;
    private String categoria;

    public Libro0604(String isbn, String titulo, String autor, String categoria) {
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("isbn invalido");
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public void setTitulo(String t) { this.titulo = t; }
    public void setAutor(String a) { this.autor = a; }
    public void setCategoria(String c) { this.categoria = c; }

    @Override
    public int compareTo(Libro0604 o) {
        return this.titulo.compareToIgnoreCase(o.titulo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro0604)) return false;
        return isbn.equals(((Libro0604) o).isbn);
    }

    @Override
    public int hashCode() { return Objects.hash(isbn); }

    @Override
    public String toString() {
        return "[" + isbn + "] " + titulo + " - " + autor + " (" + categoria + ")";
    }
}