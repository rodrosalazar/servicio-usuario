package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "areaId", "nombre"})
@Entity
@Table(name = "cine_subareas")
public class Subarea {

    private String id;
    private String nombre;
    private String areaId;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAreaId() {
        return areaId;
    }
}
