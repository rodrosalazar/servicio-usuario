package ec.gob.senescyt.usuario.core.cine;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre", "subareaIdParaCsv"})
@Table(name = "cine_detalles")
public class Detalle {
    private String id;
    private String nombre;
    private String subareaIdParaCsv;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSubareaIdParaCsv() {
        return subareaIdParaCsv;
    }
}
