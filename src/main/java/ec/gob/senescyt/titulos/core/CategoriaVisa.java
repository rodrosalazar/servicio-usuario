package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    private CategoriaVisa() {
    }

    public CategoriaVisa(TipoVisa tipoVisa, String idCategoriaVisa, String nombreCategoriaVisa) {
        this.tipoVisa = tipoVisa;
        this.id = idCategoriaVisa;
        this.nombre = nombreCategoriaVisa;
    }

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
