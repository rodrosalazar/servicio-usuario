package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoriaVisaTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private CategoriaVisa categoriaVisa;

    @Before
    public void setup() {
        String idCategoriaVisa = "99";
        String nombreCategoriaVisa = "CATEGORIA_VISA_DE_PRUEBA";

        categoriaVisa = new CategoriaVisa(null, idCategoriaVisa, nombreCategoriaVisa);
    }

    @Test
    public void debeDeserializarUnaCategoriaVisaDesdeJSON() throws Exception {
        CategoriaVisa categoriaVisaDeserializada = MAPPER.readValue(fixture("fixtures/categoria_visa_con_id.json"), CategoriaVisa.class);

        assertThat(categoriaVisaDeserializada.getId(),is(categoriaVisa.getId()));
        assertThat(categoriaVisaDeserializada.getNombre(),is(categoriaVisa.getNombre()));
    }

    @Test
    public void debeSerializarUnaCategoriaVisaAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(categoriaVisa);
        assertThat(actual, is(fixture("fixtures/categoria_visa_con_id.json")));
    }
}