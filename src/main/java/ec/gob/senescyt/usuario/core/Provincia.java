package ec.gob.senescyt.usuario.core;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "provincia")
    @JsonManagedReference
    private List<Canton> cantones;

    public Provincia() {
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
}
