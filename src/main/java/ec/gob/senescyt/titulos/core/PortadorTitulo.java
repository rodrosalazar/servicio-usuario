package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ec.gob.senescyt.commons.serializers.JSONFechaDeserializer;
import ec.gob.senescyt.commons.serializers.JSONFechaSerializer;
import ec.gob.senescyt.titulos.enums.GeneroEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.omg.CORBA.portable.IDLEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
@Table(name = "portadores_titulo")
public class PortadorTitulo {

    private static final String REGEX_EMAIL = "^[a-z](\\.?[_-]*[a-z0-9]+)*@\\w+(\\.\\w+)*(\\.[a-z]{2,})$";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portadores_titulo_id_gen")
    @SequenceGenerator(name = "portadores_titulo_id_gen", sequenceName = "portadores_titulo_id_seq", allocationSize = 1)
    private long id;

    @NotEmpty
    private String nombresCompletos;

    @Embedded
    @Valid
    @NotNull
    private Identificacion identificacion;

    @NotEmpty
    @Length(max = 6, message = "{ec.gob.senescyt.error.direccion.idPais}")
    private String idPaisNacionalidad;

    @Pattern(regexp = REGEX_EMAIL, message = "{ec.gob.senescyt.error.email}")
    @NotEmpty
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @NotEmpty
    @Length(max = 2, message = "{ec.gob.senescyt.error.idEtnia}")
    @Column(name = "etnia_id")
    private String idEtnia;

    @JsonSerialize(using = JSONFechaSerializer.class)
    @JsonDeserialize(using = JSONFechaDeserializer.class)
    @Past(message = "{ec.gob.senescyt.error.fechaNacimiento}")
    private DateTime fechaNacimiento;

    @Length(min = 9, message = "{ec.gob.senescyt.error.telefonoConvencional}")
    @Digits(integer = 9, fraction = 0, message = "{ec.gob.senescyt.error.telefonoConvencional}")
    private String telefonoConvencional;

    @Digits(integer = 5, fraction = 0, message = "{ec.gob.senescyt.error.extension}")
    private String extension;

    @Length(min = 10, message = "{ec.gob.senescyt.error.telefonoCelular}")
    @Digits(integer = 10, fraction = 0, message = "{ec.gob.senescyt.error.telefonoCelular}")
    private String telefonoCelular;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    @NotNull
    @Valid
    private Direccion direccion;

    private PortadorTitulo() {}

    public PortadorTitulo(String nombresCompletos, Identificacion identificacion,
                          String idPaisNacionalidad, String email, GeneroEnum genero,
                          String idEtnia, DateTime fechaNacimiento, String telefonoConvencional,
                          String extension, String telefonoCelular,
                          Direccion direccion) {

        this.nombresCompletos = nombresCompletos;
        this.identificacion = identificacion;
        this.idPaisNacionalidad = idPaisNacionalidad;
        this.email = email;
        this.genero = genero;
        this.idEtnia = idEtnia;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoConvencional = telefonoConvencional;
        this.extension = extension;
        this.telefonoCelular = telefonoCelular;
        this.direccion = direccion;
    }

    public String getExtension() {
        return extension;
    }

    public ReadableInstant getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombresCompletos() {
        return nombresCompletos;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public String getEmail() {
        return email;
    }

    public GeneroEnum getGenero() {
        return genero;
    }

    public String getIdEtnia() {
        return idEtnia;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public long getId() {
        return id;
    }

    public String getIdPaisNacionalidad() {
        return idPaisNacionalidad;
    }
}
