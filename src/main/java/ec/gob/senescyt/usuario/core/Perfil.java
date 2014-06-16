package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Perfil {

    private String nombre;

    @JsonProperty
    public String getNombre() {
        return nombre;
    }

    @JsonProperty
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
