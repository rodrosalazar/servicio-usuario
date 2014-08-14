package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.commons.deserializers.JSONFechaDeserializer;
import ec.gob.senescyt.commons.serializers.JSONFechaSerializer;
import ec.gob.senescyt.usuario.dto.EdicionBasicaUsuario;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@javax.persistence.Entity
@javax.persistence.Table(name = "usuarios")
public class Usuario {

    private static final String REGEX_EMAIL = "^[a-z](\\.?[_-]*[a-z0-9]+)*@\\w+(\\.\\w+)*(\\.[a-z]{2,})$";

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @javax.persistence.Embedded
    @Valid
    @IdentificacionValida
    private Identificacion identificacion;

    @javax.persistence.Embedded
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idinstitucion")
    private Institucion institucion;

    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @CollectionTable(name = "perfiles_usuarios")
    @Column(name = "perfil_id")
    private List<Long> perfiles;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;

    private Usuario() {}

    public Usuario(Identificacion identificacion, Nombre nombre, String emailInstitucional, String numeroAutorizacionQuipux,
                   DateTime fechaDeVigencia, Institucion institucion, String nombreUsuario, List<Long> perfiles, EstadoUsuario estado) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.emailInstitucional = emailInstitucional;
        this.numeroAutorizacionQuipux = numeroAutorizacionQuipux;
        this.finDeVigencia = fechaDeVigencia;
        this.institucion = institucion;
        this.nombreUsuario = nombreUsuario;
        this.perfiles = perfiles;
        this.estado = estado;
    }

    public void completarCon(EdicionBasicaUsuario usuarioBasico) {
        this.estado = usuarioBasico.getEstado();
        this.perfiles = usuarioBasico.getPerfiles();
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public List<Long> getPerfiles() {
        return perfiles;
    }

    public Institucion getInstitucion(){
        return institucion;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

}
