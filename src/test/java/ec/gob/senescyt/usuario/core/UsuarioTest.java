package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsuarioTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Usuario usuario;

    @Before
    public void setUp() {
        Institucion institucion = new Institucion(1L, "PUCE", 1L, "R", 1L, "A", 1L, "ACADEMICO");

        usuario = new Usuario(new Identificacion(TipoDocumento.CEDULA, "1718642174"),
                new Nombre("Nelson", "Alberto", "Jumbo", "Hidalgo"),
                "testEmail@senescyt.gob.ec", "SENESCYT-DFAPO-2014-65946-MI",
                new DateTime(2016, 7, 29,0, 0, DateTimeZone.UTC),
                institucion, "nombreUsuario", asList(1234l, 5678l, 9630l));
    }

    @Test
    public void debeDeserializarUnUsuarioDesdeJSON() throws IOException {
        Usuario actual = MAPPER.readValue(fixture("fixtures/usuario.json"), Usuario.class);

        assertThat(actual.getIdentificacion(), is(usuario.getIdentificacion()));
        assertThat(actual.getNombre(), is(usuario.getNombre()));
        assertThat(actual.getEmailInstitucional(), is(usuario.getEmailInstitucional()));
        assertThat(actual.getNumeroAutorizacionQuipux(), is(usuario.getNumeroAutorizacionQuipux()));
        assertThat(actual.getFinDeVigencia(), is(usuario.getFinDeVigencia()));
        assertThat(actual.getPerfiles().get(0), is(usuario.getPerfiles().get(0)));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws IOException {
        String actual = MAPPER.writeValueAsString(usuario);
        assertThat(actual, is(fixture("fixtures/usuario_con_id.json")));
    }

}
