package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq_gen")
    @SequenceGenerator(name = "perfil_seq_gen", sequenceName = "perfiles_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "perfil")
//    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Permiso> permisos;

    @JsonCreator
    public Perfil(@JsonProperty("nombre") String nombre, @JsonProperty("permisos") List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    @JsonProperty
    public String getNombre() {
        return nombre;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public List<Permiso> getPermisos() {
        return permisos;
    }
}
