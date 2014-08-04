package ec.gob.senescyt.usuario.core;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Credencial {
    @Id
    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    private String contrasenia;

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
