package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaisTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Pais pais;

    @Before
    public void setup() {
        String idPais = "888101";
        String nombrePais = "Argentina";

        pais = new Pais(idPais, nombrePais);
    }

    @Test
    public void debeDeserializarUnPaisDesdeJSON() throws IOException {
        Pais paisDeserializado = MAPPER.readValue(fixture("fixtures/pais_con_id.json"), Pais.class);

        assertThat(paisDeserializado.getId(), is(pais.getId()));
        assertThat(paisDeserializado.getNombre(), is(pais.getNombre()));
    }

    @Test
    public void debeSerializarUnPaisAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(pais);
        assertThat(actual, is(fixture("fixtures/pais_con_id.json")));
    }
}