package ec.gob.senescyt.carga.dpa;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProvinciaCsvTest {
    ProvinciaCsv provinciaCsv;

    @Before
    public void setUp() {
        provinciaCsv = new ProvinciaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = provinciaCsv.lineaASql("\"1\"    ,\"Azuay\"");
        assertThat(resultado, is("INSERT INTO provincias VALUES ('1', 'Azuay');"));
    }
}
