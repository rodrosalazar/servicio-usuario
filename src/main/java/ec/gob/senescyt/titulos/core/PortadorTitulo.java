package ec.gob.senescyt.titulos.core;

import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
public class PortadorTitulo {

    private static final String REGEX_EMAIL = "^[a-z](\\.?[_-]*[a-z0-9]+)*@\\w+(\\.\\w+)*(\\.[a-z]{2,})$";

    private long id;
    private String nombresCompletos;
    private Identificacion identificacion;
    private String codigoPais;

    @Pattern(regexp = REGEX_EMAIL, message = "{ec.gob.senescyt.error.email}")
    @NotEmpty
    private String email;

    @NotNull
    private SexoEnum sexo;
    private String codigoEtnia;

    @Past(message = "{ec.gob.senescyt.error.fechaNacimiento}")
    private DateTime fechaNacimiento;

    @Length(min = 8, message = "{ec.gob.senescyt.error.telefonoConvencional}")
    @Digits(integer = 8, fraction = 0, message = "{ec.gob.senescyt.error.telefonoConvencional}")
    private String telefonoConvencional;

    @Digits(integer = 5, fraction = 0, message = "{ec.gob.senescyt.error.extension}")
    private String extension;

    @Length(min = 10, message = "{ec.gob.senescyt.error.telefonoCelular}")
    @Digits(integer = 10, fraction = 0, message = "{ec.gob.senescyt.error.telefonoCelular}")
    private String telefonoCelular;
    private boolean aceptaCondiciones;
    private Direccion direccion;

    private PortadorTitulo() {}
    public PortadorTitulo(String nombresCompletos, Identificacion identificacion,
                          String codigoPais, String email, SexoEnum sexo,
                          String codigoEtnia, DateTime fechaNacimiento, String telefonoConvencional,
                          String extension, String telefonoCelular, boolean aceptaCondiciones,
                          Direccion direccion) {

        this.nombresCompletos = nombresCompletos;
        this.identificacion = identificacion;
        this.codigoPais = codigoPais;
        this.email = email;
        this.sexo = sexo;
        this.codigoEtnia = codigoEtnia;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoConvencional = telefonoConvencional;
        this.extension = extension;
        this.telefonoCelular = telefonoCelular;
        this.aceptaCondiciones = aceptaCondiciones;
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

    public String getCodigoPais() {
        return codigoPais;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public String getCodigoEtnia() {
        return codigoEtnia;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public boolean isAceptaCondiciones() {
        return aceptaCondiciones;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public long getId() {
        return id;
    }
}
