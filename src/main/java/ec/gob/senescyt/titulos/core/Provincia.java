package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.List;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "provincias")
public class Provincia {

    @Id
    private String id;
    private String nombre;

    @Column(name = "nombre_registro_civil")
    @JsonIgnore
    private String nombreRegistroCivil;

    @OneToMany(mappedBy = "provincia")
    @JsonManagedReference
    @JsonIgnore
    private List<Canton> cantones;

    private Provincia() {
    }

    public Provincia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }


    public List<Canton> getCantones() {
        return cantones;
    }

    public String getNombreRegistroCivil() {
        return nombreRegistroCivil;
    }
}
