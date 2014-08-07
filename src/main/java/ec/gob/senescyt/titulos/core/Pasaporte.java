package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.commons.serializers.JSONFechaDeserializer;
import ec.gob.senescyt.commons.serializers.JSONFechaSerializer;
import ec.gob.senescyt.titulos.validators.anotaciones.VigenciaVisaValida;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import ec.gob.senescyt.usuario.validators.annotations.FechaVigenciaValida;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("PASAPORTE")
@VigenciaVisaValida
public class Pasaporte extends Identificacion {

    @Temporal(TemporalType.DATE)
    @Type(type = "org.joda.time.DateTime")
    @JsonSerialize(using = JSONFechaSerializer.class)
    @JsonDeserialize(using = JSONFechaDeserializer.class)
    @FechaVigenciaValida
    @NotNull
    private DateTime finVigenciaPasaporte;

    @Temporal(TemporalType.DATE)
    @Type(type = "org.joda.time.DateTime")
    @JsonSerialize(using = JSONFechaSerializer.class)
    @JsonDeserialize(using = JSONFechaDeserializer.class)
    private DateTime finVigenciaVisa;

    @ManyToOne
    @JoinColumn(name = "id_categoria_visa", insertable = false, updatable = false)
    @JsonIgnore
    private CategoriaVisa categoriaVisa;

    @Column(name = "id_categoria_visa", insertable = true, updatable = true)
    private String idCategoriaVisa;

    @Column(name = "visaIndefinida")
    private boolean visaIndefinida;

    private Pasaporte() {
        super();
    }

    public Pasaporte(String numeroIdentificacion, DateTime finVigenciaPasaporte, DateTime finVigenciaVisa, String idCategoriaVisa, boolean visaIndefinida) {
        super(numeroIdentificacion.trim(), TipoDocumento.PASAPORTE);
        this.finVigenciaPasaporte = finVigenciaPasaporte;
        this.finVigenciaVisa = finVigenciaVisa;
        this.idCategoriaVisa = idCategoriaVisa;
        this.visaIndefinida = visaIndefinida;
    }

    public DateTime getFinVigenciaPasaporte() {
        return finVigenciaPasaporte;
    }

    public DateTime getFinVigenciaVisa() {
        return finVigenciaVisa;
    }

    public CategoriaVisa getCategoriaVisa() {
        return categoriaVisa;
    }

    public String getIdCategoriaVisa() {
        return idCategoriaVisa;
    }

    public boolean isVisaIndefinida() {
        return visaIndefinida;
    }
}
