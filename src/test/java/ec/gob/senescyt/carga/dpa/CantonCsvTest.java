package ec.gob.senescyt.carga.dpa;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CantonCsvTest {
    CantonCsv cantonCsv;

    @Before
    public void setUp() throws Exception {
        cantonCsv = new CantonCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = cantonCsv.lineaASql("\"1\",\"101\",\"Cuenca\"");
        assertThat(resultado, is("INSERT INTO cantones VALUES ('1', '101', 'Cuenca');"));
    }
}
