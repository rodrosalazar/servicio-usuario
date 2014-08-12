package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "usuario_perfiles")
public class UsuarioPerfil extends Rol {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "usuario_perfil_modulo_relaciones", joinColumns = { @JoinColumn(name = "usuario_perfil_id") }, inverseJoinColumns = { @JoinColumn(name = "modulo_id") })

    private Set<Modulo> modulos;

    public UsuarioPerfil(String nombre) {
        super(nombre);
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }
}
