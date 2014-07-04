package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "cine_clasificaciones")
public class Clasificacion {

    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
