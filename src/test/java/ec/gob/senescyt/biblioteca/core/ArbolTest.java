package ec.gob.senescyt.biblioteca.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArbolTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Arbol arbolBiblioteca;

    @Before
    public void setup() {
        Integer idArbol = 1;
        String nombreArbol = "normativas";

        Integer idNivelGenerales = 1;
        String nombreNivelGenerales = "Generales";
        Integer idNivelPadreDeGenerales = null;

        Integer idSubnivelLeyes = 2;
        String nombreSubnivelLeyes = "Leyes";
        Integer idNivelPadreDeLeyes = 1;

        Integer idSubnivelReglamentos = 3;
        String nombreSubnivelReglamentos = "Reglamentos";
        Integer idNivelPadreDeReglamentos = 1;

        List<NivelArbol> nivelesArbolNormativas = new ArrayList<>();
        nivelesArbolNormativas.add(new NivelArbol(idNivelGenerales, nombreNivelGenerales, idNivelPadreDeGenerales));
        nivelesArbolNormativas.add(new NivelArbol(idSubnivelLeyes, nombreSubnivelLeyes, idNivelPadreDeLeyes));
        nivelesArbolNormativas.add(new NivelArbol(idSubnivelReglamentos, nombreSubnivelReglamentos, idNivelPadreDeReglamentos));

        arbolBiblioteca = new Arbol(idArbol, nombreArbol, nivelesArbolNormativas);
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
