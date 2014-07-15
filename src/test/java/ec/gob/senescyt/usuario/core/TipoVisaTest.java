package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TipoVisaTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private TipoVisa tipoVisa;

    @Before
    public void setup() {
        String idTipoVisa = "9";
        String nombreTipoVisa = "TIPO_VISA_DE_PRUEBA";

        tipoVisa = new TipoVisa(idTipoVisa, nombreTipoVisa);
    }

    @Test
    public void debeDeserializarUnTipoVisaDesdeJSON() throws Exception {
        TipoVisa tipoVisaDeserializado = MAPPER.readValue(fixture("fixtures/tipo_visa_con_id.json"), TipoVisa.class);

        assertThat(tipoVisaDeserializado.getId(),is(tipoVisa.getId()));
        assertThat(tipoVisaDeserializado.getNombre(),is(tipoVisa.getNombre()));
    }

    @Test
    public void debeSerializarUnTipoVisaAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(tipoVisa);
        assertThat(actual, is(fixture("fixtures/tipo_visa_con_id.json")));
    }
}