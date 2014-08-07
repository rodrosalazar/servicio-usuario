package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@JsonPropertyOrder({"id", "nombre", "subareaIdParaCsv"})
@Table(name = "cine_detalles")
@Entity
public class Detalle {

    @Id
    private String id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "subarea_id")
    @JsonBackReference
    private Subarea subarea;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subareaIdParaCsv;

    private Detalle() {}

    public Detalle(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Subarea getSubarea() {
        return subarea;
    }

    public String getSubareaIdParaCsv() {
        return subareaIdParaCsv;
    }
}
