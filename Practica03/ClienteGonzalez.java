package practica03;

public class ClienteGonzalez {
    // Uso de protected para permitir acceso controlado en subclases del mismo paquete
    protected String idCliente; // p. ej. RFC, CURP o un codigo interno
    private CuentaBancaria0604 cuenta; // composicion

    public ClienteGonzalez(String idCliente, CuentaBancaria0604 cuenta) {
        if (idCliente == null || idCliente.isBlank()) {
            throw new IllegalArgumentException("idCliente invalido");
        }
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta requerida");
        }
        this.idCliente = idCliente;
        this.cuenta = cuenta;
    }

    public String getIdCliente() { return idCliente; }
    public CuentaBancaria0604 getCuenta() { return cuenta; }

    // ejemplo de metodo que usa el miembro protected
    protected String maskId() {
        return idCliente.length() > 4
            ? "****" + idCliente.substring(idCliente.length() - 4)
            : idCliente;
    }

    public String resumenCliente() {
        return "Cliente{id=" + maskId() + ", " + cuenta.toString() + "}";
    }
}