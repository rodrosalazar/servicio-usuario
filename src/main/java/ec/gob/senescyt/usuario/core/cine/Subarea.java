package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@JsonPropertyOrder({"id", "areaIdParaCsv", "nombre"})
@Entity
@Table(name = "cine_subareas")
public class Subarea {

    @Id
    private String id;
    private String nombre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subarea")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Detalle> detalles;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String areaIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "area_id")
    @JsonBackReference
    private Area area;

    private Subarea() {}

    public Subarea(String id, String nombre, List<Detalle> detalles) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = detalles;
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

    public List<Detalle> getDetalles() {
        return detalles;
    }
}
