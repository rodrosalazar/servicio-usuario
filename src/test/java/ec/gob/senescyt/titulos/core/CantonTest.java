package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CantonTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Canton canton;

    @Before
    public void setup() {
        String idCanton = "9999";
        String nombreCanton = "CANTON_DE_PRUEBA";

        canton = new Canton(null, idCanton, nombreCanton);
    }

    @Test
    public void debeDeserializarUnCantonDesdeJSON() throws IOException {
        Canton cantonDeserializado = MAPPER.readValue(fixture("fixtures/canton_con_id.json"), Canton.class);

        assertThat(cantonDeserializado.getId(),is(canton.getId()));
        assertThat(cantonDeserializado.getNombre(),is(canton.getNombre()));
    }

    @Test
    public void debeSerializarUnCantonAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(canton);
        assertThat(actual, is(fixture("fixtures/canton_con_id.json")));
    }
}