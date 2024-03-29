package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.titulos.core.Direccion;
import ec.gob.senescyt.titulos.core.Identificacion;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.enums.GeneroEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.function.Consumer;

public class PortadorTituloBuilder {
    private static PortadorTituloBuilder portadorTituloBuilder;

    public String nombresCompletos = "Nombres_completos";
    public Identificacion identificacion;
    public String idPaisNacionalidad = "888209";
    public String email = "email@email.com";
    public String idEtnia = "1";
    public String telefonoConvencional = "012345678";
    public String extension = "123";
    public String telefonoCelular = "0912345678";
    public String direccionCompleta = "Calle_principal";
    public String idProvincia = "01";
    public String idCanton = "0101";
    public String idParroquia = "010151";
    public DateTime fechaNacimiento = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
    public GeneroEnum genero = GeneroEnum.FEMENINO;

    public PortadorTitulo generar() {
        PortadorTitulo portadorTitulo = new PortadorTitulo(nombresCompletos,
                identificacion,
                idPaisNacionalidad,
                email,
                genero,
                idEtnia, fechaNacimiento,
                telefonoConvencional, extension,
                telefonoCelular,
                new Direccion(direccionCompleta, idProvincia, idCanton, idParroquia));

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