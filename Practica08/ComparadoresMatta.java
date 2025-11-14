package practica08;

import java.util.Comparator;

public final class ComparadoresMatta {
    private ComparadoresMatta() {}

    public static final Comparator<Libro0604> POR_TITULO_ASC =
        Comparator.comparing(Libro0604::getTitulo, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Libro0604> POR_AUTOR_ASC =
        Comparator.comparing(Libro0604::getAutor, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Libro0604> POR_CATEGORIA_LUEGO_TITULO =
        Comparator.comparing(Libro0604::getCategoria, String.CASE_INSENSITIVE_ORDER)
                  .thenComparing(Libro0604::getTitulo, String.CASE_INSENSITIVE_ORDER);
}