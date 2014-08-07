package ec.gob.senescyt.usuario.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CredencialLoginTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private CredencialLogin credencialLogin;

    @Before
    public void setUp() {
        String nombreUsuario = "nombreUsuario";
        String contrasenia = "contrasenia";
        credencialLogin = new CredencialLogin(nombreUsuario, contrasenia);
    }

    @Test
    public void debeDeserializarUnaCredencialLoginDesdeJSON() throws IOException {
        CredencialLogin credencialDeserializada = MAPPER.readValue(fixture("fixtures/credencial_login.json"), CredencialLogin.class);

        assertThat(credencialDeserializada.getNombreUsuario(), is(credencialLogin.getNombreUsuario()));
        assertThat(credencialDeserializada.getContrasenia(), is(credencialLogin.getContrasenia()));
    }

    @Test
    public void debeSerializarUnaCredencialLoginAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(credencialLogin);
        assertThat(actual, is(fixture("fixtures/credencial_login.json")));
    }
}