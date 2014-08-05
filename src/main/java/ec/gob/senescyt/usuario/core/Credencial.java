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
    private String hash;

    private Credencial() {
    }

    public Credencial(String nombreUsuario, String hash) {
        this.nombreUsuario = nombreUsuario;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
