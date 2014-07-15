package ec.gob.senescyt.usuario.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name="tipos_de_visa")
public class TipoVisa {

    @Id
    private String id;
    private String nombre;

    @OneToMany(mappedBy = "tipoVisa")
    @JsonManagedReference
    @JsonIgnore
    private List<CategoriaVisa> categoriasVisa;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
