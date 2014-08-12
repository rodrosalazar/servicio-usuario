package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "funciones")
public class Funcion extends Rol {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "funcion_nivel_de_acceso_relaciones", joinColumns = { @JoinColumn(name = "funcion_id") }, inverseJoinColumns = { @JoinColumn(name = "nivel_de_acceso_id") })
    private Set<NivelDeAcceso> nivelesDeAcceso = new HashSet<>();

    public Funcion(String nombre) {
        super(nombre);
    }

    public void setNivelesDeAcceso(Set<NivelDeAcceso> nivelesDeAcceso) {
        this.nivelesDeAcceso = nivelesDeAcceso;
    }

    public Set<NivelDeAcceso> getNivelesDeAcceso() {
        return nivelesDeAcceso;
    }
}
