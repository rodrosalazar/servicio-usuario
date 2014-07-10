package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "provincias")
public class Provincia {

    @Id
    private String id;
    private String nombre;

    public Provincia() {
    }

    public Provincia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }
}
