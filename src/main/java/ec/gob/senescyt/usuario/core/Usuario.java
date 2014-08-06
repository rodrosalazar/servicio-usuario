package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.commons.serializers.JSONFechaDeserializer;
import ec.gob.senescyt.commons.serializers.JSONFechaSerializer;
import ec.gob.senescyt.usuario.validators.annotations.FechaVigenciaValida;
import ec.gob.senescyt.usuario.validators.annotations.IdentificacionValida;
import ec.gob.senescyt.usuario.validators.annotations.QuipuxValido;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    private static final String REGEX_EMAIL = "^[a-z](\\.?[_-]*[a-z0-9]+)*@\\w+(\\.\\w+)*(\\.[a-z]{2,})$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @Valid
    @IdentificacionValida
    private Identificacion identificacion;

    @Embedded
    @Valid
    private Nombre nombre;

    @Pattern(regexp = REGEX_EMAIL, message = "{ec.gob.senescyt.error.email}")
    @NotEmpty
    private String emailInstitucional;

    @NotEmpty
    @QuipuxValido
    private String numeroAutorizacionQuipux;

    @Temporal(TemporalType.DATE)
    @Type(type = "org.joda.time.DateTime")
    @JsonSerialize(using = JSONFechaSerializer.class)
    @JsonDeserialize(using = JSONFechaDeserializer.class)
    @FechaVigenciaValida
    @NotNull
    private DateTime finDeVigencia;

    @NotNull
    @Valid
    private Long idInstitucion;

    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    @ElementCollection
    @Cascade(CascadeType.ALL)
    @CollectionTable(name = "perfiles_usuarios")
    @Column(name = "perfil_id")
    private List<Long> perfiles;

    private Usuario() {}

    public Usuario(Identificacion identificacion, Nombre nombre, String emailInstitucional, String numeroAutorizacionQuipux,
                   DateTime fechaDeVigencia, Long idInstitucion, String nombreUsuario, List<Long> perfiles) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.emailInstitucional = emailInstitucional;
        this.numeroAutorizacionQuipux = numeroAutorizacionQuipux;
        this.finDeVigencia = fechaDeVigencia;
        this.idInstitucion = idInstitucion;
        this.nombreUsuario = nombreUsuario;
        this.perfiles = perfiles;
    }

    public long getId() {
        return id;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public Nombre getNombre() {
        return nombre;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public String getNumeroAutorizacionQuipux() {
        return numeroAutorizacionQuipux;
    }

    public DateTime getFinDeVigencia() {
        return finDeVigencia;
    }

    public Long getIdInstitucion() {
        return idInstitucion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public List<Long> getPerfiles() {
        return perfiles;
    }
}
