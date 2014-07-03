package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.usuario.serializers.JSONFechaVigenciaDeserializer;
import ec.gob.senescyt.usuario.serializers.JSONFechaVigenciaSerializer;
import ec.gob.senescyt.usuario.validators.annotations.FechaVigenciaValida;
import ec.gob.senescyt.usuario.validators.annotations.IdentificacionValida;
import ec.gob.senescyt.usuario.validators.annotations.QuipuxValido;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario {

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

    @Email(message = "{ec.gob.senescyt.error.email}")
    @NotEmpty
    private String emailInstitucional;

    @NotEmpty
    @QuipuxValido
    private String numeroAutorizacionQuipux;

    @Temporal(TemporalType.DATE)
    @Type(type = "org.joda.time.DateTime")
    @JsonSerialize(using = JSONFechaVigenciaSerializer.class)
    @JsonDeserialize(using = JSONFechaVigenciaDeserializer.class)
    @FechaVigenciaValida
    private DateTime finDeVigencia;

    @NotNull
    @Valid
    private Long idInstitucion;

    @NotEmpty
    private String nombreUsuario;

    private Usuario() {}

    public Usuario(Identificacion identificacion, Nombre nombre, String emailInstitucional, String numeroAutorizacionQuipux,
                   DateTime fechaDeVigencia, Long idInstitucion, String nombreUsuario) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.emailInstitucional = emailInstitucional;
        this.numeroAutorizacionQuipux = numeroAutorizacionQuipux;
        this.finDeVigencia = fechaDeVigencia;
        this.idInstitucion = idInstitucion;
        this.nombreUsuario = nombreUsuario;
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

}