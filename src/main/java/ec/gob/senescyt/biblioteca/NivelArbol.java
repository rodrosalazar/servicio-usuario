package ec.gob.senescyt.biblioteca;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "niveles_arbol")
@JsonPropertyOrder({"id", "nombre", "arbolIdParaCsv", "nivelPadreIdParaCsv"})
public class NivelArbol {

    @Id
    private Integer id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_nivel_padre")
    @JsonBackReference("nivelesHijos")
    private NivelArbol nivelPadre;

    @ManyToOne
    @JoinColumn(name = "id_arbol")
    @JsonIgnore
    private Arbol arbol;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer arbolIdParaCsv;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer nivelPadreIdParaCsv;

    @OneToMany(mappedBy = "nivelPadre")
    @JsonManagedReference("nivelesHijos")
    private List<NivelArbol> nivelesHijos = new ArrayList<>();

    public NivelArbol() {
    }

    public NivelArbol(Integer id, String nombre, NivelArbol nivelPadre, Arbol arbol) {

        this.id = id;
        this.nombre = nombre;
        this.nivelPadre = nivelPadre;
        this.arbol = arbol;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NivelArbol that = (NivelArbol) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    public NivelArbol getNivelPadre() {
        return nivelPadre;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public Integer getArbolIdParaCsv() {
        return arbolIdParaCsv;
    }

    public Integer getNivelPadreIdParaCsv() {
        return nivelPadreIdParaCsv;
    }

    public List<NivelArbol> getNivelesHijos() {
        return nivelesHijos;
    }
}
