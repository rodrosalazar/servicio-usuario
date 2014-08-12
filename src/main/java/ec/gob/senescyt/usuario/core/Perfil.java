package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Length(max = 100)
    private String nombre;

    // Ir a https://hibernate.atlassian.net/browse/HHH-1718 para mas informacion sobre el FetchMode.SUBSELECT
    @OneToMany(mappedBy = "perfil", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JsonManagedReference
    @NotEmpty
    @Valid
    private List<Permiso> permisos;

    private Perfil() {}

    public Perfil(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
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
}
