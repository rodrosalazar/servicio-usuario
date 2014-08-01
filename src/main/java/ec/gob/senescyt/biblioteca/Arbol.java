package ec.gob.senescyt.biblioteca;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="arboles")
public class Arbol {

    @Id
    private Integer id;
    private String nombre;

    @OneToMany(mappedBy = "arbol", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NivelArbol> nivelesArbol = new ArrayList<>();

    public Arbol() {
    }

    public Arbol(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    public List<NivelArbol> getNivelesArbol() {
        return nivelesArbol;
    }
}
