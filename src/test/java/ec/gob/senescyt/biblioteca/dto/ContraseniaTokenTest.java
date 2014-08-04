package ec.gob.senescyt.biblioteca.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ContraseniaTokenTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private ContraseniaToken credencialConToken;

    @Before
    public void setUp() {
        String contrasenia = "contrasenia hasheada";
        String idToken = "tokenValido";
        credencialConToken = new ContraseniaToken(contrasenia, idToken);
    }


    @Test
    public void debeDeserializarUnaCredencialConTokenDesdeJSON() throws IOException {
        ContraseniaToken credencialDeserializada = MAPPER.readValue(fixture("fixtures/contrasenia_token.json"), ContraseniaToken.class);

        assertThat(credencialDeserializada.getContrasenia(), is(credencialConToken.getContrasenia()));
        assertThat(credencialDeserializada.getIdToken(), is(credencialConToken.getIdToken()));
    }

    @Test
    public void debeSerializarUnaCredencialConTokenAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(credencialConToken);
        assertThat(actual, is(fixture("fixtures/contrasenia_token.json")));
    }
}