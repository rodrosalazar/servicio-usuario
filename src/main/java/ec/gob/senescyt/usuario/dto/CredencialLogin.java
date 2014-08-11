package ec.gob.senescyt.usuario.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class CredencialLogin {

    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
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
