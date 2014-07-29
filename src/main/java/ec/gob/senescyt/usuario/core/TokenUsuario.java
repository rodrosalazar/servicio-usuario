package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TokenUsuario {

    @Id
    private String token;

    @OneToOne
    @JsonManagedReference
    private Usuario usuario;

    private TokenUsuario() {
    }

    public TokenUsuario(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }
}
