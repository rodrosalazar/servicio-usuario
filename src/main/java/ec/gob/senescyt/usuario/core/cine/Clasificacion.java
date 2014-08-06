package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "cine_clasificaciones")
public class Clasificacion {

    @Id
    private String id;
    private String nombre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clasificacion")
    @Cascade(CascadeType.ALL)
    @JsonManagedReference
    private List<Area> areas;

    private Clasificacion() {}

    public Clasificacion(String id, String nombre, List<Area> areas) {

        this.id = id;
        this.nombre = nombre;
        this.areas = areas;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
