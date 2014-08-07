package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ModalidadesDeEducacionTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void debeSerializarModalidadesDeEducacionAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(ModalidadEducacion.PRESENCIAL);
        assertThat(actual, is(fixture("fixtures/modalidad_educacion.json")));
    }

}