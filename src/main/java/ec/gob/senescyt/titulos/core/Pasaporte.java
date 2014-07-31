package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.commons.serializers.JSONFechaDeserializer;
import ec.gob.senescyt.commons.serializers.JSONFechaSerializer;
import ec.gob.senescyt.titulos.validators.anotaciones.VigenciaVisaValida;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.validators.annotations.FechaVigenciaValida;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
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
    @JoinColumn(name = "id_tipo_visa", insertable = false, updatable = false)
    @JsonIgnore
    private TipoVisa tipoVisa;

    @Column(name = "id_tipo_visa", insertable = true, updatable = true)
    private String idTipoVisa;

    @Column(name = "visaIndefinida")
    private boolean visaIndefinida;

    private Pasaporte() {
        super();
    }

    public Pasaporte(String numeroIdentificacion, DateTime finVigenciaPasaporte, DateTime finVigenciaVisa, String idTipoVisa, boolean visaIndefinida) {
        super(numeroIdentificacion.trim(), TipoDocumentoEnum.PASAPORTE);
        this.finVigenciaPasaporte = finVigenciaPasaporte;
        this.finVigenciaVisa = finVigenciaVisa;
        this.idTipoVisa = idTipoVisa;
        this.visaIndefinida = visaIndefinida;
    }

    public DateTime getFinVigenciaPasaporte() {
        return finVigenciaPasaporte;
    }

    public DateTime getFinVigenciaVisa() {
        return finVigenciaVisa;
    }

    public TipoVisa getTipoVisa() {
        return tipoVisa;
    }

    public String getIdTipoVisa() {
        return idTipoVisa;
    }

    public boolean isVisaIndefinida() {
        return visaIndefinida;
    }
}
