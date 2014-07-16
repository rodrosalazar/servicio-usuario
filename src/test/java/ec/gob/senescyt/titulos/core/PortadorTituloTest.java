package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.titulos.enums.SexoEnum;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PortadorTituloTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private PortadorTitulo portadorTituloConCedula;

    private static final String NOMBRES_COMPLETOS = "Nombres_completos";
    private static final String NUMERO_IDENTIFICACION = "1111111116";
    private static final String CODIGO_PAIS = "999999";
    private static final String EMAIL = "email@email.com";
    private static final String CODIGO_ETNIA = "1";
    private static final String TELEFONO_CONVENCIONAL = "022787345";
    private static final String EXTENSION_VALIDA = "123";
    private static final String TELEFONO_CELULAR = "0912345678";
    private static final boolean ACEPTA_CONDICIONES = true;
    private static final String CALLE_PRINCIPAL = "Calle_principal";
    private static final String NUMERO_CASA = "Numero_casa_123";
    private static final String CALLE_SECUNDARIA = "Calle_secundaria";
    private static final String CODIGO_PROVINCIA = "99";
    private static final String CODIGO_CANTON = "9999";
    private static final String CODIGO_PARROQUIA = "999999";
    private static final DateTime FECHA_NACIMIENTO_VALIDA =
            new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);


    @Before
    public void setUp() throws Exception {
        portadorTituloConCedula = new PortadorTitulo(NOMBRES_COMPLETOS,
                new Identificacion(TipoDocumentoEnum.CEDULA, NUMERO_IDENTIFICACION),
                CODIGO_PAIS,
                EMAIL,
                SexoEnum.MASCULINO,
                CODIGO_ETNIA, FECHA_NACIMIENTO_VALIDA,
                TELEFONO_CONVENCIONAL, EXTENSION_VALIDA,
                TELEFONO_CELULAR, ACEPTA_CONDICIONES,
                new Direccion(CALLE_PRINCIPAL, NUMERO_CASA, CALLE_SECUNDARIA, CODIGO_PROVINCIA,
                        CODIGO_CANTON, CODIGO_PARROQUIA));
    }

    @Test
    public void debeDeserializarUnPortadorTituloDesdeJSON() throws Exception {
        PortadorTitulo portadorTituloDeserializado = MAPPER.readValue(fixture("fixtures/portador_titulo_con_cedula.json"),
                PortadorTitulo.class);

        assertThat(portadorTituloDeserializado.getNombresCompletos(), is(portadorTituloConCedula.getNombresCompletos()));
        assertThat(portadorTituloDeserializado.getIdentificacion(),is(portadorTituloConCedula.getIdentificacion()));
        assertThat(portadorTituloDeserializado.getCodigoPais(),is(portadorTituloConCedula.getCodigoPais()));
        assertThat(portadorTituloDeserializado.getEmail(),is(portadorTituloConCedula.getEmail()));
        assertThat(portadorTituloDeserializado.getSexo(),is(portadorTituloConCedula.getSexo()));
        assertThat(portadorTituloDeserializado.getCodigoEtnia(),is(portadorTituloConCedula.getCodigoEtnia()));
        assertThat(portadorTituloDeserializado.getFechaNacimiento(),is(portadorTituloConCedula.getFechaNacimiento()));
        assertThat(portadorTituloDeserializado.getTelefonoConvencional(),is(portadorTituloConCedula.getTelefonoConvencional()));
        assertThat(portadorTituloDeserializado.getExtension(),is(portadorTituloConCedula.getExtension()));
        assertThat(portadorTituloDeserializado.getTelefonoCelular(),is(portadorTituloConCedula.getTelefonoCelular()));
        assertThat(portadorTituloDeserializado.isAceptaCondiciones(),is(portadorTituloConCedula.isAceptaCondiciones()));
        assertThat(portadorTituloDeserializado.getDireccion(),is(portadorTituloConCedula.getDireccion()));
    }

    @Test
    public void debeSerializarUnPortadorTituloAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(portadorTituloConCedula);
        assertThat(actual, is(fixture("fixtures/portador_titulo_con_cedula.json")));
    }
}
