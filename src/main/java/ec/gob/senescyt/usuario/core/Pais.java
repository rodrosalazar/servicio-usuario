package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name="paises")
public class Pais {

    @Id
    private String id;
    private String nombre;

    public Pais(String idPais, String nombrePais) {
        this.id = idPais;
        this.nombre = nombrePais;
    }

    public Pais() {
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
