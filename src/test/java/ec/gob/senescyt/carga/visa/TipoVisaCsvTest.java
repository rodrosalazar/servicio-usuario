package ec.gob.senescyt.carga.visa;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TipoVisaCsvTest {
    TipoVisaCsv tipoVisaCsv;

    @Before
    public void setUp() throws Exception {
        tipoVisaCsv = new TipoVisaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = tipoVisaCsv.lineaASql("1    ,\"Visa 12 - NO INMIGRANTE\"");
        assertThat(resultado, is("INSERT INTO tipos_de_visa VALUES ('1', 'Visa 12 - NO INMIGRANTE');"));
    }
}
