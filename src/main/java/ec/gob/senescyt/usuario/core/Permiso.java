package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permisos_seq_gen")
    @SequenceGenerator(name = "permisos_seq_gen", sequenceName = "permisos_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false, updatable = false, insertable = true)
    private Perfil perfil;

//    private List<Funcion> funciones;

    @JsonCreator
    public Permiso(@JsonProperty("nombre") String nombre, @JsonProperty("funciones") List<Funcion> funciones) {
        this.nombre = nombre;
//        this.funciones = funciones;
    }

    @JsonProperty
    public String getNombre() {
        return nombre;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonIgnore
    public Perfil getPerfil() {
        return perfil;
    }

//    public List<Funcion> getFunciones() {
//        return funciones;
//    }
}
