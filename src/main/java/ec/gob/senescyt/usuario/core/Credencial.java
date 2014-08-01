package ec.gob.senescyt.usuario.core;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Credencial {

    @Id
    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    private String contrasenia;

    @OneToOne
    @JoinColumn(name = "nombreUsuario")
    private Usuario usuario;

    private Credencial() {
    }

    public Credencial(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
