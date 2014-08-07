package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EtniaTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Etnia etnia;

    @Before
    public void setUp() {
        String idEtnia = "99";
        String nombreEtnia = "ETNIA_DE_PRUEBA";

        etnia = new Etnia(idEtnia, nombreEtnia);
    }

    @Test
    public void debeDeserializarUnaEtniaDesdeJSON() throws IOException {
        Etnia etniaDeserializada = MAPPER.readValue(fixture("fixtures/etnia_con_id.json"), Etnia.class);

        assertThat(etniaDeserializada.getId(),is(etnia.getId()));
        assertThat(etniaDeserializada.getNombre(),is(etnia.getNombre()));
    }

    @Test
    public void debeSerializarUnaEtniaAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(etnia);
        assertThat(actual, is(fixture("fixtures/etnia_con_id.json")));
    }
}