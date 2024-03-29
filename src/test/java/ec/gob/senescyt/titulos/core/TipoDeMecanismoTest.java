package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TipoDeMecanismoTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void debeSerializarTiposDeMecanismoAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(TipoDeMecanismo.APOSTILLA);
        assertThat(actual, is(fixture("fixtures/tipos_mecanismo_apostilla.json")));
    }
}
