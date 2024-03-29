package ec.gob.senescyt.carga.institucion;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UniversidadExtranjeraCsvTest {
    UniversidadExtranjeraCsv universidadExtranjeraCsv;

    @Before
    public void setUp() {
        universidadExtranjeraCsv = new UniversidadExtranjeraCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = universidadExtranjeraCsv.lineaASql("\"4001\"    ,\"AALTO UNIVERSIDAD\", \"00008\", \"FI\"");
        assertThat(resultado, is("INSERT INTO universidades_extranjeras VALUES ('4001', 'AALTO UNIVERSIDAD', '00008', 'FI');"));
    }
}
