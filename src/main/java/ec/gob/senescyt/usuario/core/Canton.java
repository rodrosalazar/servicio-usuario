package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
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
