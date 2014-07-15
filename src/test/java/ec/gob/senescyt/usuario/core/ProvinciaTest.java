package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProvinciaTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Provincia provincia;

    @Before
    public void setup() {
        String idProvincia = "1";
        String nombreProvincia = "Azuay";

        provincia = new Provincia(idProvincia, nombreProvincia);
    }

    @Test
    public void debeDeserializarUnaProvinciaDesdeJSON() throws Exception {
        Provincia provinciaDeserializada = MAPPER.readValue(fixture("fixtures/provincia_con_id.json"), Provincia.class);

        assertThat(provinciaDeserializada.getId(),is(provincia.getId()));
        assertThat(provinciaDeserializada.getNombre(),is(provincia.getNombre()));
    }

    @Test
    public void debeSerializarUnaProvinciaAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(provincia);
        assertThat(actual, is(fixture("fixtures/provincia_con_id.json")));
    }
}