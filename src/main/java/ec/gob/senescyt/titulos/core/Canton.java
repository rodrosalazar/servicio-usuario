package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@JsonPropertyOrder({"provinciaIdParaCsv", "id", "nombre"})
@Entity
@Table(name = "cantones")
public class Canton {

    @Id
    private String id;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String provinciaIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    @JsonBackReference
    private Provincia provincia;

    @OneToMany(mappedBy = "canton")
    @JsonManagedReference
    @JsonIgnore
    private List<Parroquia> parroquias;

    private String nombre;

    public Canton(){

    }

    public Canton(Provincia provincia, String idCanton, String nombreCanton) {
        this.provincia = provincia;
        this.id = idCanton;
        this.nombre = nombreCanton;
    }

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public String getProvinciaIdParaCsv() {
        return provinciaIdParaCsv;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public List<Parroquia> getParroquias() {
        return parroquias;
    }
}
