package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@JsonPropertyOrder({"id", "nombre", "clasificacionIdParaCsv"})
@Entity
@Table(name = "cine_areas")
public class Area {

    @Id
    private String id;
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clasificacionIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    @JsonBackReference
    private Clasificacion clasificacion;

    @OneToMany(mappedBy = "area")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonManagedReference
    private List<Subarea> subareas;

    private Area() {}

    public Area(String id, String nombre, List<Subarea> subareas) {
        this.id = id;
        this.nombre = nombre;
        this.subareas = subareas;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClasificacionIdParaCsv() {
        return clasificacionIdParaCsv;
    }

    public List<Subarea> getSubareas() {
        return subareas;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }
}