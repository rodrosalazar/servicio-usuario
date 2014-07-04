package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.cine.ClasificacionCsv;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClasificacionCsvTest {

    ClasificacionCsv clasificacionCsv;
    
    @Before
    public void setUp() throws Exception {
        clasificacionCsv = new ClasificacionCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = clasificacionCsv.lineaASql("001  ,CINE-UNESCO 1997");
        assertThat(resultado, is("INSERT INTO cine_clasificaciones VALUES ('001', 'CINE-UNESCO 1997');"));
    }

    @Test
    public void debeConvertirArchivosCompletos() throws IOException {
        File archivo = File.createTempFile("prueba", "csv");
        archivo.deleteOnExit();
        Writer escritor = new BufferedWriter(new FileWriter(archivo, true));
        escritor.write("Cod_Clasificacion,Nombre_Clasificacion\n" +
                "001  ,CINE-UNESCO 1997\n" +
                "002  ,CINE-UNESCO 2013\n");
        escritor.close();

        String resultado = clasificacionCsv.archivoASql(archivo);

        assertThat(resultado, is("INSERT INTO cine_clasificaciones VALUES ('001', 'CINE-UNESCO 1997');\n" +
                "INSERT INTO cine_clasificaciones VALUES ('002', 'CINE-UNESCO 2013');\n"));
    }

}
