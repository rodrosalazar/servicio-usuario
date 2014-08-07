package ec.gob.senescyt.usuario.exceptions;

public class ServicioNoDisponibleException extends Exception {
    public ServicioNoDisponibleException(String mensaje, Exception exception) {
        super(mensaje, exception);
    }
}
