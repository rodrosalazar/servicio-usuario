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

    @Before
    public void setup() {
        long idUsuario = 1;
        String contrasenia = "contrasenia hasheada";

        credencial = new Credencial(idUsuario, contrasenia);
    }

    @Test
    public void debeDeserializarUnPaisDesdeJSON() throws Exception {
        Credencial credencialDeserializada = MAPPER.readValue(fixture("fixtures/credencial_con_id.json"), Credencial.class);

        assertThat(credencialDeserializada.getId(),is(credencial.getId()));
        assertThat(credencialDeserializada.getIdUsuario(),is(credencial.getIdUsuario()));
        assertThat(credencialDeserializada.getContrasenia(),is(credencial.getContrasenia()));
    }

    @Test
    public void debeSerializarUnPaisAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(credencial);
        assertThat(actual, is(fixture("fixtures/credencial_con_id.json")));
    }
}
