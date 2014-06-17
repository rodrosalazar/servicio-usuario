package ec.gob.senescyt.instituciones.carga;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InstitucionesCsvTest {

    @Test
    public void debeConvertirUnaFileDeCsvASql() throws IOException {
        InstitucionesCsv institucionesCsv = new InstitucionesCsv();

        String resultado = institucionesCsv.filaASql("1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A");

        assertThat(resultado, is("INSERT INTO instituciones VALUES (1001, 'ESCUELA POLITECNICA NACIONAL', 1, 'PUBLICA', 1, 'VIGENTE', 1, 'A');"));
    }

    @Test
    public void debeConvertirOtraFilaDeCsvASql() throws IOException {
        InstitucionesCsv institucionesCsv = new InstitucionesCsv();

        String resultado = institucionesCsv.filaASql("1007,UNIVERSIDAD DE CUENCA,01,PUBLICA,01,VIGENTE,02,B");

        assertThat(resultado, is("INSERT INTO instituciones VALUES (1007, 'UNIVERSIDAD DE CUENCA', 1, 'PUBLICA', 1, 'VIGENTE', 2, 'B');"));

    }
}
