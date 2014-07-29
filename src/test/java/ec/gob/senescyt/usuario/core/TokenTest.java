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

public class TokenTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Token token;

    @Before
    public void setup() {
        long idInstitucion = 1l;
        Usuario usuario = new Usuario(new Identificacion(TipoDocumentoEnum.CEDULA, "1718642174"),
                new Nombre("Nelson", "Alberto", "Jumbo", "Hidalgo"),
                "testEmail@senescyt.gob.ec", "SENESCYT-DFAPO-2014-65946-MI",
                new DateTime(2016, 7, 29, 0, 0, DateTimeZone.UTC),
                idInstitucion, "nombreUsuario", asList(1234l, 5678l, 9630l));

        token = new Token("32d88be3-2233-4b58-bf3c-99c35b162805", usuario);
    }

    @Test
    public void debeDeserializarUnTokenDesdeJSON() throws Exception {
        Token actual = MAPPER.readValue(fixture("fixtures/token.json"), Token.class);

        assertThat(actual.getId(), is(token.getId()));
        assertThat(actual.getUsuario().getNombreUsuario(), is(token.getUsuario().getNombreUsuario()));
        assertThat(actual.getUsuario().getId(), is(token.getUsuario().getId()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws Exception {
        String actual = MAPPER.writeValueAsString(token);
        assertThat(actual, is(fixture("fixtures/token.json")));
    }
}
