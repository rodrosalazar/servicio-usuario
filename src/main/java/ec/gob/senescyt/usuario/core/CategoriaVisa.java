package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;

@JsonPropertyOrder({"id", "tipoVisaIdParaCsv", "nombre"})
@Entity
@Table(name="categorias_de_visa")
public class CategoriaVisa {

    @Id
    private String id;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tipoVisaIdParaCsv;

    @ManyToOne
    @JoinColumn(name = "tipo_visa_id")
    @JsonBackReference
    private TipoVisa tipoVisa;

    private String nombre;

    public String getTipoVisaIdParaCsv() {
        return tipoVisaIdParaCsv;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoVisa getTipoVisa() {
        return tipoVisa;
    }
}
