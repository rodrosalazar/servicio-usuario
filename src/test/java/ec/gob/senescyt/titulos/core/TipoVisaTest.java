package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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

        tipoVisa.getCategoriasVisa().add(new CategoriaVisa(tipoVisa, "1", "CATEGORIA_PRUEBA"));
    }

    @Test
    public void debeDeserializarUnTipoVisaDesdeJSON() throws IOException {
        TipoVisa tipoVisaDeserializado = MAPPER.readValue(fixture("fixtures/tipo_visa_con_id.json"), TipoVisa.class);

        assertThat(tipoVisaDeserializado.getId(),is(tipoVisa.getId()));
        assertThat(tipoVisaDeserializado.getNombre(),is(tipoVisa.getNombre()));
    }

    @Test
    public void debeSerializarUnTipoVisaAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(tipoVisa);
        assertThat(actual, is(fixture("fixtures/tipo_visa_con_id.json")));
    }
}