package ec.gob.senescyt.carga.cine;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AreaCsvTest {
    AreaCsv areaCsv;

    @Before
    public void setUp() {
        areaCsv = new AreaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = areaCsv.lineaASql("3    ,\"CIENCIAS SOCIALES, EDUCACION COMERCIAL Y DERECHO\",001  ");
        assertThat(resultado, is("INSERT INTO cine_areas VALUES ('3', 'CIENCIAS SOCIALES, EDUCACION COMERCIAL Y DERECHO', '001');"));
    }
}
