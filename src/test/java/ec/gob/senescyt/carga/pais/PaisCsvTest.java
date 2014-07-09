package ec.gob.senescyt.carga.pais;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PaisCsvTest {
    PaisCsv paisCsv;

    @Before
    public void setUp() throws Exception {
        paisCsv = new PaisCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = paisCsv.lineaASql("888330    ,\"Corea Del Sur, República De\"");
        assertThat(resultado, is("INSERT INTO paises VALUES ('888330', 'Corea Del Sur, República De');"));
    }
}
