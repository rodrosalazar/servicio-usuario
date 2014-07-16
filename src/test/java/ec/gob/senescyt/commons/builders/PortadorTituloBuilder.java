package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.titulos.core.Direccion;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

public class PortadorTituloBuilder {
    private static PortadorTituloBuilder portadorTituloBuilder;
    private  String nombresCompletos = "Nombres_completos";
    private  String numeroIdentificacion = "1111111116";
    private  String codigoPais = "999999";
    private  String email = "email@email.com";
    private  String codigoEtnia = "1";
    private  String telefonoConvencional = "022787345";
    private  String extensionValida = "123";
    private  String telefonoCelular = "0912345678";
    private  boolean aceptaCondiciones = true;
    private  String callePrincipal = "Calle_principal";
    private  String numeroCasa = "Numero_casa_123";
    private  String calleSecundaria = "Calle_secundaria";
    private  String codigoProvincia = "99";
    private  String codigoCanton = "9999";
    private  String codigoParroquia = "999999";
    private Date fechaNacimiento =
            new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC).toDate();


    public PortadorTitulo generar() {
        PortadorTitulo portadorTitulo = new PortadorTitulo(nombresCompletos,
                new Identificacion(TipoDocumentoEnum.CEDULA, numeroIdentificacion),
                codigoPais,
                email,
                SexoEnum.MASCULINO,
                codigoEtnia, fechaNacimiento,
                telefonoConvencional, extensionValida,
                telefonoCelular, aceptaCondiciones,
                new Direccion(callePrincipal, numeroCasa, calleSecundaria, codigoProvincia,
                        codigoCanton, codigoParroquia));

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

    public PortadorTituloBuilder conFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return portadorTituloBuilder;
    }
}