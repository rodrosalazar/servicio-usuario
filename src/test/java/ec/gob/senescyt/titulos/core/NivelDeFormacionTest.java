package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeFormacionTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void debeSerializarNivelesDeFormacionAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(NivelDeFormacion.TECNOLOGICO);
        assertThat(actual, is(fixture("fixtures/nivel_de_formacion_tecnologico.json")));
    }

}