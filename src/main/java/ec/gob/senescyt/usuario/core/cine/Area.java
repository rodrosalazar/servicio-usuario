package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@JsonPropertyOrder({"id", "nombre", "clasificacionIdParaCsv"})
@Entity
@Table(name = "cine_areas")
public class Area {

    @Id
    private String id;
    private String nombre;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clasificacionIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    @JsonBackReference
    private Clasificacion clasificacion;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "area")
    @Cascade(CascadeType.ALL)
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