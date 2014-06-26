package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.usuario.serializers.JSONFechaVigenciaDeserializer;
import ec.gob.senescyt.usuario.serializers.JSONFechaVigenciaSerializer;
import ec.gob.senescyt.usuario.validators.QuipuxValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @JsonProperty("identificacion")
    private Identificacion identificacion;

    @Embedded
    private Nombre nombre;

    @Column
    private String emailInstitucional;

    @Column
    private String numeroAutorizacionQuipux;

    @Column
    @Temporal(TemporalType.DATE)
    @Type(type="org.joda.time.DateTime")
    @JsonSerialize(using = JSONFechaVigenciaSerializer.class)
    @JsonDeserialize(using = JSONFechaVigenciaDeserializer.class)
    private DateTime finDeVigencia;

    @Column
    private Long idInstitucion;

    @Column
    private String nombreUsuario;

    private Usuario() {
    }

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

    @JsonIgnore
    public boolean isValido() {
        if(!EmailValidator.getInstance().isValid(this.emailInstitucional)){
            return false;
        }

        if(this.getFinDeVigencia().isBefore(new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay())){
            return false;
        }

        if(!QuipuxValidator.isValidoNumeroAutorizacionQuipux(this.numeroAutorizacionQuipux)){
            return false;
        }

        return true;
    }
}
