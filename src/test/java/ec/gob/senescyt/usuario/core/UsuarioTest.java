package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsuarioTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Usuario usuario;

    @Before
    public void setup() {
        long idInstitucion = 1l;

        usuario = new Usuario(new Identificacion(TipoDocumentoEnum.CEDULA, "1718642174"),
                new Nombre("Nelson", "Alberto", "Jumbo", "Hidalgo"),
                "testEmail@senescyt.gob.ec", "SENESCYT-DFAPO-2014-65946-MI",
                new DateTime(2016, 7, 29,0, 0, DateTimeZone.UTC),
                idInstitucion, "nombreUsuario", asList(1234l, 5678l, 9630l), null);
    }

    @Test
    public void debeDeserializarUnUsuarioDesdeJSON() throws Exception {
        Usuario actual = MAPPER.readValue(fixture("fixtures/usuario.json"), Usuario.class);

        assertThat(actual.getIdentificacion(), is(usuario.getIdentificacion()));
        assertThat(actual.getNombre(), is(usuario.getNombre()));
        assertThat(actual.getEmailInstitucional(), is(usuario.getEmailInstitucional()));
        assertThat(actual.getNumeroAutorizacionQuipux(), is(usuario.getNumeroAutorizacionQuipux()));
        assertThat(actual.getFinDeVigencia(), is(usuario.getFinDeVigencia()));
        assertThat(actual.getPerfiles().get(0), is(usuario.getPerfiles().get(0)));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws Exception {
        String actual = MAPPER.writeValueAsString(usuario);
        assertThat(actual, is(fixture("fixtures/usuario_con_id.json")));
    }

}
