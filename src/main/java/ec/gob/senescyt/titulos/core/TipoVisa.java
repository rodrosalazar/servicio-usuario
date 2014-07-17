package ec.gob.senescyt.titulos.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.List;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name="tipos_de_visa")
public class TipoVisa {

    @Id
    private String id;
    private String nombre;

    @OneToMany(mappedBy = "tipoVisa", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CategoriaVisa> categoriasVisa;

    public TipoVisa() {
    }

    public TipoVisa(String idTipoVisa, String nombreTipoVisa) {
        this.id = idTipoVisa;
        this.nombre = nombreTipoVisa;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<CategoriaVisa> getCategoriasVisa() {
        return categoriasVisa;
    }
}
