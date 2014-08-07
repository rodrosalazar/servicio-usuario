package ec.gob.senescyt.usuario.dto;

public class CredencialLogin {

    private String nombreUsuario;
    private String contrasenia;

    private CredencialLogin () {}

    public CredencialLogin(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
