package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@JsonPropertyOrder({"id", "areaIdParaCsv", "nombre"})
@Entity
@Table(name = "cine_subareas")
public class Subarea {

    @Id
    private String id;
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "area_id")
    @JsonBackReference
    private Area area;

    private Subarea() {}

    public Subarea(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAreaIdParaCsv() {
        return areaIdParaCsv;
    }

    public Area getArea() {
        return area;
    }
}
