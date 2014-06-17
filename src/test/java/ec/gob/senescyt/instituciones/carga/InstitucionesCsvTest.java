package ec.gob.senescyt.instituciones.carga;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InstitucionesCsvTest {

    private InstitucionesCsv institucionesCsv;

    @Before
    public void setUp() throws Exception {
        institucionesCsv = new InstitucionesCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = institucionesCsv.lineaASql("1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (1001, 'ESCUELA POLITECNICA NACIONAL', 1, 'PUBLICA', 1, 'VIGENTE', 1, 'A');"));
    }

    @Test
    public void debeConvertirOtraLineaDeCsvASql() throws IOException {
        String resultado = institucionesCsv.lineaASql("1007,UNIVERSIDAD DE CUENCA,01,PUBLICA,01,VIGENTE,02,B");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (1007, 'UNIVERSIDAD DE CUENCA', 1, 'PUBLICA', 1, 'VIGENTE', 2, 'B');"));
    }

    @Test
    public void debeLlenarConNullsLosCamposFaltantes() throws IOException {
        String resultado = institucionesCsv.lineaASql("2345,INSTITUTO SUPERIOR PEDAGÓGICO LOS RIOS,01,PUBLICA,01,VIGENTE");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (2345, 'INSTITUTO SUPERIOR PEDAGÓGICO LOS RIOS', 1, 'PUBLICA', 1, 'VIGENTE', NULL, NULL);"));
    }

    @Test
    public void debeRemoverEspaciosEnNombres() throws IOException {
        String resultado = institucionesCsv.lineaASql("1004,  ESCUELA POLITECNICA DEL EJERCITO   ,01,PUBLICA,03,\"CERRADA MEDIANTE RESOLUCION RPC-SO-09-NO.098-2014, DE 12 DE MARZO DE 2014, POR FUSIÓN EN LA UFA-ESPE\",01,A");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (1004, 'ESCUELA POLITECNICA DEL EJERCITO', 1, 'PUBLICA', 3, 'CERRADA MEDIANTE RESOLUCION RPC-SO-09-NO.098-2014, DE 12 DE MARZO DE 2014, POR FUSIÓN EN LA UFA-ESPE', 1, 'A');"));
    }

    @Test
    public void debeConvertirArchivosCompletos() throws IOException {
        File archivo = File.createTempFile("prueba", "csv");
        archivo.deleteOnExit();
        Writer escritor = new BufferedWriter(new FileWriter(archivo, true));
        escritor.write("CODIGO,NOMBRE,CODIGO_REGIMEN,REGIMEN,CODIGO_ESTADO,ESTADO,CODIGO_CATEGORIA,CATEGORIA\n" +
                "1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A\n" +
                "1002,ESCUELA SUPERIOR POLITECNICA DE CHIMBORAZO,01,PUBLICA,01,VIGENTE,02,B\n");
        escritor.close();

        String resultado = institucionesCsv.archivoASql(archivo);

        assertThat(resultado, is("INSERT INTO instituciones VALUES (1001, 'ESCUELA POLITECNICA NACIONAL', 1, 'PUBLICA', 1, 'VIGENTE', 1, 'A');\n" +
                "INSERT INTO instituciones VALUES (1002, 'ESCUELA SUPERIOR POLITECNICA DE CHIMBORAZO', 1, 'PUBLICA', 1, 'VIGENTE', 2, 'B');\n"));
    }
}
