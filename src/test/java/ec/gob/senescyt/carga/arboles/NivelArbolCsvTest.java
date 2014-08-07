package ec.gob.senescyt.carga.arboles;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NivelArbolCsvTest {
    NivelArbolCsv nivelArbolCsv;

    @Before
    public void setUp() {
        nivelArbolCsv = new NivelArbolCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = nivelArbolCsv.lineaASql("3,Reglamentos,1,1");
        assertThat(resultado, is("INSERT INTO niveles_arbol VALUES (3, 'Reglamentos', 1, 1);"));
    }
}
