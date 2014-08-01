package ec.gob.senescyt.usuario.core;

public class Credencial {
    private long id;
    private long idUsuario;
    private String contrasenia;

    private Credencial() {
    }

    public Credencial(long idUsuario, String contrasenia) {
        this.idUsuario = idUsuario;
        this.contrasenia = contrasenia;
    }

    public long getId() {
        return id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
