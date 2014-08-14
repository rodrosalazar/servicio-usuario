package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
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

public class TokenTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Token token;

    @Before
    public void setUp() {
        Institucion institucion = new Institucion(1L, "PUCE", 1L, "R", 1L, "A", 1L, "ACADEMICO");
        Usuario usuario = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.numeroIdentificacion = "1718642174")
                .con(u -> u.primerNombre = "Nelson")
                .con(u -> u.segundoNombre = "Alberto")
                .con(u -> u.primerApellido = "Jumbo")
                .con(u -> u.segundoApellido = "Hidalgo")
                .con(u -> u.emailInstitucional = "testEmail@senescyt.gob.ec")
                .con(u -> u.numeroAutorizacionQuipux = "SENESCYT-DFAPO-2014-65946-MI")
                .con(u -> u.fechaDeVigencia = new DateTime(2016, 7, 29, 0, 0, DateTimeZone.UTC))
                .con(u -> u.institucion = institucion)
                .con(u -> u.nombreUsuario = "nombreUsuario")
                .con(u -> u.perfiles = asList(1234l, 5678l, 9630l))
                .generar();

        token = new Token("32d88be3-2233-4b58-bf3c-99c35b162805", usuario);
    }

    @Test
    public void debeDeserializarUnTokenDesdeJSON() throws IOException {
        Token actual = MAPPER.readValue(fixture("fixtures/token.json"), Token.class);

        assertThat(actual.getId(), is(token.getId()));
        assertThat(actual.getUsuario().getNombreUsuario(), is(token.getUsuario().getNombreUsuario()));
        assertThat(actual.getUsuario().getId(), is(token.getUsuario().getId()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws IOException {
        String actual = MAPPER.writeValueAsString(token);
        assertThat(actual, is(fixture("fixtures/token.json")));
    }
}
