package ec.gob.senescyt.usuario.exceptions;

public class ServicioNoDisponibleException extends Throwable {
    public ServicioNoDisponibleException(String mensaje, Exception exception) {
        super(mensaje, exception);
    }
}
