package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre", "clasificacionId"})
@Entity
@Table(name = "cine_areas")
public class Area {

    private String id;
    private String nombre;
    private String clasificacionId;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClasificacionId() {
        return clasificacionId;
    }
}
