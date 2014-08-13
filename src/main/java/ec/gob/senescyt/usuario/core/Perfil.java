package ec.gob.senescyt.usuario.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil extends Entidad {

    @NotEmpty
    @Length(max = 100, message = "El campo debe estar entre 1 y 100")
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "perfil_permiso_relaciones", joinColumns = { @JoinColumn(name = "perfil_id") }, inverseJoinColumns = { @JoinColumn(name = "permiso_id") })
    @NotEmpty
    private List<Permiso> permisos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Perfil() {
        // Por Jackson
    }

    public Perfil(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public Long getId() {
        return id;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
