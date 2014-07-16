package ec.gob.senescyt.titulos.core;

import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import org.joda.time.DateTime;

import javax.persistence.Entity;

@Entity
public class PortadorTitulo {

    private PortadorTitulo() {}

    private String nombresCompletos;
    private Identificacion identificacion;
    private String codigoPais;
    private String email;
    private SexoEnum sexo;
    private String codigoEtnia;
    private DateTime fechaNacimiento;
    private String telefonoConvencional;
    private String extension;
    private String telefonoCelular;
    private boolean aceptaCondiciones;
    private Direccion direccion;

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

    public DateTime getFechaNacimiento() {
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

}
