package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "perfil")
    @Cascade(CascadeType.ALL)
    @JsonManagedReference
    private List<Permiso> permisos;

    private Perfil() {}

    public Perfil(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Perfil(long id, String nombre, List<Permiso> permisos) {
        this(nombre, permisos);
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    @JsonIgnore
    public boolean isValido() {
        if (this.getNombre() == null || this.getNombre().isEmpty()) {
            return false;
        }

        if (this.getPermisos() == null || this.getPermisos().isEmpty()) {
            return false;
        }

        for (Permiso permiso : permisos) {
            if (!permiso.isValido()) {
                return false;
            }
        }

        return true;
    }

}
