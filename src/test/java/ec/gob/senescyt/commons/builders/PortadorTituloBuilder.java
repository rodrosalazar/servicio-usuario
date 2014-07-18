package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.titulos.core.Direccion;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.function.Consumer;

public class PortadorTituloBuilder {
    private static PortadorTituloBuilder portadorTituloBuilder;

    public String nombresCompletos = "Nombres_completos";
    public Identificacion identificacion = new Identificacion(TipoDocumentoEnum.CEDULA, "1111111116");
    public String idPais = "888209";
    public String email = "email@email.com";
    public String idEtnia = "1";
    public String telefonoConvencional = "012345678";
    public String extension = "123";
    public String telefonoCelular = "0912345678";
    public boolean aceptaCondiciones = true;
    public String callePrincipal = "Calle_principal";
    public String numeroCasa = "Numero_casa_123";
    public String calleSecundaria = "Calle_secundaria";
    public String idProvincia = "1";
    public String idCanton = "405";
    public String idParroquia = "10250";
    public DateTime fechaNacimiento = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
    public SexoEnum sexo = SexoEnum.FEMENINO;

    public PortadorTitulo generar() {
        PortadorTitulo portadorTitulo = new PortadorTitulo(nombresCompletos,
                identificacion,
                email,
                sexo,
                idEtnia, fechaNacimiento,
                telefonoConvencional, extension,
                telefonoCelular, aceptaCondiciones,
                new Direccion(callePrincipal, numeroCasa, calleSecundaria, idProvincia,
                        idCanton, idParroquia, idPais));

        return portadorTitulo;
    }

    public static PortadorTituloBuilder nuevoPortadorTitulo() {
        portadorTituloBuilder = new PortadorTituloBuilder();
        return portadorTituloBuilder;
    }

    public PortadorTituloBuilder con(Consumer<PortadorTituloBuilder> consumidor) {
        consumidor.accept(portadorTituloBuilder);
        return portadorTituloBuilder;
    }
}