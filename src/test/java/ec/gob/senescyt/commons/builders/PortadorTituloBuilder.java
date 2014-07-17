package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.titulos.core.Direccion;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class PortadorTituloBuilder {
    private static PortadorTituloBuilder portadorTituloBuilder;

    private String nombresCompletos = "Nombres_completos";
    private String numeroIdentificacion = "1111111116";
    private String idPais = "999999";
    private String email = "email@email.com";
    private String codigoEtnia = "1";
    private String telefonoConvencional = "012345678";
    private String extension = "123";
    private String telefonoCelular = "0912345678";
    private boolean aceptaCondiciones = true;
    private String callePrincipal = "Calle_principal";
    private String numeroCasa = "Numero_casa_123";
    private String calleSecundaria = "Calle_secundaria";
    private String idProvincia = "99";
    private String idCanton = "9999";
    private String idParroquia = "999999";
    private DateTime fechaNacimiento = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
    private SexoEnum sexo = SexoEnum.FEMENINO;
    private Direccion direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
            idCanton, idParroquia, idPais);


    public PortadorTitulo generar() {
        PortadorTitulo portadorTitulo = new PortadorTitulo(nombresCompletos,
                new Identificacion(TipoDocumentoEnum.CEDULA, numeroIdentificacion),
                email,
                sexo,
                codigoEtnia, fechaNacimiento,
                telefonoConvencional, extension,
                telefonoCelular, aceptaCondiciones,
                direccion);

        return portadorTitulo;
    }

    public static PortadorTituloBuilder nuevoPortadorTitulo() {
        portadorTituloBuilder = new PortadorTituloBuilder();
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conEmail(String email) {
        this.email = email;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conFechaNacimiento(DateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conTelefonoConvencional(String telefononConvencional) {
        this.telefonoConvencional = telefononConvencional;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conExtension(String extension) {
        this.extension = extension;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conSexo(SexoEnum sexo) {
        this.sexo = sexo;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conNombreCompleto(String nombreCompletos) {
        this.nombresCompletos = nombreCompletos;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conAceptaCondiciones(boolean aceptaCondiciones) {
        this.aceptaCondiciones = aceptaCondiciones;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conIdEtnia(String idEtnia) {
        this.codigoEtnia = idEtnia;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conDireccion(Direccion direccion) {
        this.direccion = direccion;
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conCallePrincipal(String callePrincipal) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conCalleSecundaria(String calleSecundaria) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conNumeroDeCasa(String numeroCasa) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conIdProvincia(String idProvincia) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conIdCanton(String idCanton) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conIdParroquia(String idParroquia) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder conIdPais(String idPais) {
        this.direccion = new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                idCanton, idParroquia, idPais);
        return portadorTituloBuilder;
    }

}