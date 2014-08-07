package ec.gob.senescyt.carga.cine;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SubareaCsvTest {

    SubareaCsv subareaCsv;

    @Before
    public void setUp() {
        subareaCsv = new SubareaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = subareaCsv.lineaASql("62   ,6    ,\"AGRICULTURA, SILVICULTURA Y PESCA\"");
        assertThat(resultado, is("INSERT INTO cine_subareas VALUES ('62', 'AGRICULTURA, SILVICULTURA Y PESCA', '6');"));
    }
}
