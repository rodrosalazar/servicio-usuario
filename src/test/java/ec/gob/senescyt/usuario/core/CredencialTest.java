package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CredencialTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Credencial credencial;
    private Credencial credencialConToken;

    @Before
    public void setup() {
        String contrasenia = "contrasenia hasheada";
        String username = "username";
        String idToken = "tokenValido";

        credencial = new Credencial(username, contrasenia);
        credencialConToken = new Credencial(username, contrasenia, idToken);
    }

    @Test
    public void debeDeserializarUnaCredencialDesdeJSON() throws Exception {
        Credencial credencialDeserializada = MAPPER.readValue(fixture("fixtures/credencial.json"), Credencial.class);

        assertThat(credencialDeserializada.getNombreUsuario(), is(credencial.getNombreUsuario()));
        assertThat(credencialDeserializada.getContrasenia(), is(credencial.getContrasenia()));
    }

    @Test
    public void debeSerializarUnaCredencialAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(credencial);
        assertThat(actual, is(fixture("fixtures/credencial.json")));
    }

    @Test
    public void debeDeserializarUnaCredencialConTokenDesdeJSON() throws Exception {
        Credencial credencialDeserializada = MAPPER.readValue(fixture("fixtures/credencial_con_token.json"), Credencial.class);

        assertThat(credencialDeserializada.getNombreUsuario(), is(credencialConToken.getNombreUsuario()));
        assertThat(credencialDeserializada.getContrasenia(), is(credencialConToken.getContrasenia()));
        assertThat(credencialDeserializada.getIdToken(), is(credencialConToken.getIdToken()));
    }

    @Test
    public void debeSerializarUnaCredencialConTokenAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(credencialConToken);
        assertThat(actual, is(fixture("fixtures/credencial_con_token.json")));
    }
}
