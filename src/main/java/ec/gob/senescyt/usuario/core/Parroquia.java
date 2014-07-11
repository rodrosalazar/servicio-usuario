package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@JsonPropertyOrder({"cantonIdParaCsv", "id", "nombre"})
@Entity
@Table(name = "parroquias")
public class Parroquia {
    @Id
    private String id;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cantonIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "canton_id")
    @JsonBackReference
    private Canton canton;

    private String nombre;

    public Parroquia() {
    }
    public Parroquia(Canton canton, String id, String nombre) {
        this.canton = canton;
        this.id = id;
        this.nombre = nombre;
    }

    public String getCantonIdParaCsv() {
        return cantonIdParaCsv;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Canton getCanton() {
        return canton;
    }
}
