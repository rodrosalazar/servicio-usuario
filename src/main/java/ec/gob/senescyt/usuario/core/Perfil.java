package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq_gen")
    @SequenceGenerator(name = "perfil_seq_gen", sequenceName = "perfiles_id_seq")
    private long id;

    @Column
    private String nombre;

//    private List<Permiso> permisos;

    @JsonCreator
    public Perfil(@JsonProperty String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
    }

    @JsonProperty
    public String getNombre() {
        return nombre;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

//    @JsonProperty
//    public List<Permiso> getPermisos() {
//        return permisos;
//    }
}
