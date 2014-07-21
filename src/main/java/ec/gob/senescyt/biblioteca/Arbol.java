package ec.gob.senescyt.biblioteca;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class Arbol {
    private Integer id;
    private String nombre;
    private List<NivelArbol> nivelesArbol;

    public Arbol() {
    }

    public Arbol(Integer id, String nombre, List<NivelArbol> nivelesArbol) {

        this.id = id;
        this.nombre = nombre;
        this.nivelesArbol = nivelesArbol;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    @JsonManagedReference
    public List<NivelArbol> getNivelesArbol() {
        return nivelesArbol;
    }
}
