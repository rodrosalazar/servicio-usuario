package ec.gob.senescyt.biblioteca;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class NivelArbol {
    private Integer id;
    private String nombre;
    private Integer idNivelPadre;
    private Arbol arbol;

    public NivelArbol() {
    }

    public NivelArbol(Integer id, String nombre, Integer idNivelPadre) {

        this.id = id;
        this.nombre = nombre;
        this.idNivelPadre = idNivelPadre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdNivelPadre() {
        return idNivelPadre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NivelArbol that = (NivelArbol) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @JsonBackReference
    public Arbol getArbol() {
        return arbol;
    }
}
