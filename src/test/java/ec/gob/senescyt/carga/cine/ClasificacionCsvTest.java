package ec.gob.senescyt.carga.cine;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClasificacionCsvTest {

    ClasificacionCsv clasificacionCsv;
    
    @Before
    public void setUp() {
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
        Writer escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), "UTF-8"));
        escritor.write("Cod_Clasificacion,Nombre_Clasificacion\n" +
                "001  ,CINE-UNESCO 1997\n" +
                "002  ,CINE-UNESCO 2013\n");
        escritor.close();

        String resultado = clasificacionCsv.archivoASql(archivo);

        assertThat(resultado, is("INSERT INTO cine_clasificaciones VALUES ('001', 'CINE-UNESCO 1997');\n" +
                "INSERT INTO cine_clasificaciones VALUES ('002', 'CINE-UNESCO 2013');\n"));
    }

}
