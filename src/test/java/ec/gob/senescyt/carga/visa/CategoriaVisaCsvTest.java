package ec.gob.senescyt.carga.visa;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoriaVisaCsvTest {
    CategoriaVisaCsv categoriaVisaCsv;

    @Before
    public void setUp() throws Exception {
        categoriaVisaCsv = new CategoriaVisaCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = categoriaVisaCsv.lineaASql("1    , 1, \"I-II     FUNCIONARIO DE MISIONES DIPLOMATICAS\"");
        assertThat(resultado, is("INSERT INTO categorias_de_visa VALUES ('1', '1', 'I-II     FUNCIONARIO DE MISIONES DIPLOMATICAS');"));
    }
}
