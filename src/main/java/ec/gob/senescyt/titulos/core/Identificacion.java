package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "identificaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoDocumento", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cedula.class, name = "cedula"),
        @JsonSubTypes.Type(value = Pasaporte.class, name = "pasaporte"),

})
public class Identificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tipoDocumento", insertable = false, updatable = false)
    private TipoDocumento tipoDocumento;

    @NotNull
    @NotEmpty
    @Length(max = 20)
    @Pattern(regexp = "^[^_\\W]+$", message = "{ec.gob.senescyt.error.numeroIdentificacion}")
    private String numeroIdentificacion;

    protected Identificacion() {
        // Do nothing.
    }

    protected Identificacion(String numeroIdentificacion, TipoDocumento tipoDocumento) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Long getId() {
        return id;
    }
}
