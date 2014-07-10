package ec.gob.senescyt.carga.dpa;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParroquiaCsvTest {
    ParroquiaCsv parroquiaCsv;

    @Before
    public void setUp() throws Exception {
        parroquiaCsv = new ParroquiaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = parroquiaCsv.lineaASql("\"101\",\"10150\",\"Cuenca\"");
        assertThat(resultado, is("INSERT INTO parroquias VALUES ('101', '10150', 'Cuenca');"));
    }
}
