package ec.gob.senescyt.usuario.exceptions;

public class CifradoErroneoException extends Exception {
    public CifradoErroneoException(String mensaje, Exception exception) {
        super(mensaje, exception);
    }
}
