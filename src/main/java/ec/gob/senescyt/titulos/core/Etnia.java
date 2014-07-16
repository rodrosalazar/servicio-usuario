package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "etnias")
public class Etnia {

    @Id
    private String id;
    private String nombre;

    private Etnia() {
    }

    public Etnia(String idEtnia, String nombreEtnia) {
        this.id = idEtnia;
        this.nombre = nombreEtnia;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
