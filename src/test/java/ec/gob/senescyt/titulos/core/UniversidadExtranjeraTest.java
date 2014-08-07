package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UniversidadExtranjeraTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private UniversidadExtranjera universidadExtranjera;

    @Before
    public void setup() {
        String id = "4001";
        String nombre = "AALTO UNIVERSIDAD";
        String codigoTipo = "00008";
        String codigoPais = "FI";

        universidadExtranjera = new UniversidadExtranjera(id, nombre, codigoTipo, codigoPais);
    }

    @Test
    public void debeDeserializarUnPaisDesdeJSON() throws IOException {
        UniversidadExtranjera universidadExtranjeraDeserializada =
                MAPPER.readValue(fixture("fixtures/universidad_extranjera_con_id.json"), UniversidadExtranjera.class);

        assertThat(universidadExtranjeraDeserializada.getId(),is(universidadExtranjera.getId()));
        assertThat(universidadExtranjeraDeserializada.getNombre(),is(universidadExtranjera.getNombre()));
        assertThat(universidadExtranjeraDeserializada.getCodigoTipo(),is(universidadExtranjera.getCodigoTipo()));
        assertThat(universidadExtranjeraDeserializada.getCodigoPais(),is(universidadExtranjera.getCodigoPais()));
    }

    @Test
    public void debeSerializarUnPaisAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(universidadExtranjera);
        assertThat(actual, is(fixture("fixtures/universidad_extranjera_con_id.json")));
    }
}