package practica07;

public class ExceptionMattaBase extends Exception {
    public ExceptionMattaBase(String message) { super(message); }
    public ExceptionMattaBase(String message, Throwable cause) { super(message, cause); }
}