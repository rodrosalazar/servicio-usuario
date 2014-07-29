package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Token {

    @Id
    private String id;

    @OneToOne
    @JsonManagedReference
    private Usuario usuario;

    private Token() {
    }

    public Token(String id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
