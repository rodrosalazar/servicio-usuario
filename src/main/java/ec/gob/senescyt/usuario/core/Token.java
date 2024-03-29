package ec.gob.senescyt.usuario.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "tokens")
public class Token {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
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
