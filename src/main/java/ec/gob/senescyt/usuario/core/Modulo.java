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
@Table(name = "modulos")
public class Modulo extends Rol {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "modulo_funcion_relaciones", joinColumns = { @JoinColumn(name = "modulo_id") }, inverseJoinColumns = { @JoinColumn(name = "funcion_id") })
    private Set<Funcion> funciones;

    public Modulo(String nombre) {
        super(nombre);
    }

    public void setFunciones(Set<Funcion> funciones) {
        this.funciones = funciones;
    }

    public Set<Funcion> getFunciones() {
        return funciones;
    }
}
