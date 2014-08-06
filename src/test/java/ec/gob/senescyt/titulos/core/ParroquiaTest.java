package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParroquiaTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Parroquia parroquia;

    @Before
    public void setup() {
        String idParroquia = "999999";
        String nombreParroquia = "PARROQUIA_DE_PRUEBA";

        parroquia = new Parroquia(null, idParroquia, nombreParroquia);
    }

    @Test
    public void debeDeserializarUnaParroquiaDesdeJSON() throws Exception {
        Parroquia parroquiaDeserializada = MAPPER.readValue(fixture("fixtures/parroquia_con_id.json"), Parroquia.class);

        assertThat(parroquiaDeserializada.getId(),is(parroquia.getId()));
        assertThat(parroquiaDeserializada.getNombre(),is(parroquia.getNombre()));
    }

    @Test
    public void debeSerializarUnaParroquiaAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(parroquia);
        assertThat(actual, is(fixture("fixtures/parroquia_con_id.json")));
    }
}