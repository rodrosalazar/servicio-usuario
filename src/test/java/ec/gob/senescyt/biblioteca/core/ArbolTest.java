package ec.gob.senescyt.biblioteca.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.commons.builders.ArbolBuilder;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArbolTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Arbol arbolBiblioteca;

    @Before
    public void setup() {
        arbolBiblioteca = ArbolBuilder.nuevoArbol().generar();
    }

    @Test
    public void debeDeserializarUnArbolDesdeJSON() throws Exception {
        Arbol arbolDeserializado = MAPPER.readValue(fixture("fixtures/arbol_con_id.json"), Arbol.class);

        assertThat(arbolDeserializado.getId(), is(arbolBiblioteca.getId()));
        assertThat(arbolDeserializado.getNombre(), is(arbolBiblioteca.getNombre()));

        arbolDeserializado.getNivelesArbol().forEach(nivelDeserializado ->
                assertThat(arbolBiblioteca.getNivelesArbol().contains(nivelDeserializado), is(true))
        );
    }

    @Test
    public void debeSerializarUnCantonAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(arbolBiblioteca);
        assertThat(actual, is(fixture("fixtures/arbol_con_id.json")));
    }
}
