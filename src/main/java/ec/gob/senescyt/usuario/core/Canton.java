package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

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
}
