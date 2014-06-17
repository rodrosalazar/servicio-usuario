package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Perfil {

    @JsonProperty
    @Id
    @GeneratedValue
    private long id;
    @JsonProperty
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
