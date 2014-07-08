package ec.gob.senescyt.carga.cine;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DetalleCsvTest {
    DetalleCsv detalleCsv;

    @Before
    public void setUp() throws Exception {
        detalleCsv = new DetalleCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = detalleCsv.lineaASql("0212 ,\"DISEÑO INDUSTRIAL, DE MODAS E INTERIORES\",021  ");
        assertThat(resultado, is("INSERT INTO cine_detalles VALUES ('0212', 'DISEÑO INDUSTRIAL, DE MODAS E INTERIORES', '021');"));
    }
}
