package practica05;

public abstract class FiguraLEMG implements CalculableLEMG {
    protected String nombre;

    public FiguraLEMG(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }

    public String infoBasica() {
        return getClass().getSimpleName() + "(" + (nombre != null ? nombre : "-") + ")";
    }
}
