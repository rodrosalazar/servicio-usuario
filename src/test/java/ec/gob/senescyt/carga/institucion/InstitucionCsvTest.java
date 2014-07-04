package ec.gob.senescyt.carga.institucion;

import ec.gob.senescyt.carga.ConversorCsv;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class InstitucionCsvTest {

    public static final String ARCHIVO_SALIDA_SQL = "prueba-consola.sql";
    public static final String ARCHIVO_SALIDA_TXT = "mi-archivo-de-salida.txt";
    public static final String ARCHIVO_ENTRADA_CSV = File.separator + "prueba-consola.csv";
    private InstitucionCsv institucionCsv;

    @Before
    public void setUp() throws Exception {
        institucionCsv = new InstitucionCsv();
    }

    @Test
    public void debeConvertirUnaLineaDeCsvASql() throws IOException {
        String resultado = institucionCsv.lineaASql("1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (1001, 'ESCUELA POLITECNICA NACIONAL', 1, 'PUBLICA', 1, 'VIGENTE', 1, 'A');"));
    }

    @Test
    public void debeConvertirOtraLineaDeCsvASql() throws IOException {
        String resultado = institucionCsv.lineaASql("1007,UNIVERSIDAD DE CUENCA,01,PUBLICA,01,VIGENTE,02,B");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (1007, 'UNIVERSIDAD DE CUENCA', 1, 'PUBLICA', 1, 'VIGENTE', 2, 'B');"));
    }

    @Test
    public void debeLlenarConNullsLosCamposFaltantes() throws IOException {
        String resultado = institucionCsv.lineaASql("2345,INSTITUTO SUPERIOR PEDAGÓGICO LOS RIOS,01,PUBLICA,01,VIGENTE");
        assertThat(resultado, is("INSERT INTO instituciones VALUES (2345, 'INSTITUTO SUPERIOR PEDAGÓGICO LOS RIOS', 1, 'PUBLICA', 1, 'VIGENTE', NULL, NULL);"));
    }

    @Test
    public void debeRemoverEspaciosEnNombres() throws IOException {
        String resultado = institucionCsv.lineaASql("1004,  ESCUELA POLITECNICA DEL EJERCITO   ,01,PUBLICA,03,\"CERRADA MEDIANTE RESOLUCION RPC-SO-09-NO.098-2014, DE 12 DE MARZO DE 2014, POR FUSIÓN EN LA UFA-ESPE\",01,A");
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

        String resultado = institucionCsv.archivoASql(archivo);

        assertThat(resultado, is("INSERT INTO instituciones VALUES (1001, 'ESCUELA POLITECNICA NACIONAL', 1, 'PUBLICA', 1, 'VIGENTE', 1, 'A');\n" +
                "INSERT INTO instituciones VALUES (1002, 'ESCUELA SUPERIOR POLITECNICA DE CHIMBORAZO', 1, 'PUBLICA', 1, 'VIGENTE', 2, 'B');\n"));
    }

    @Test
    public void debeGenerarArchivosSqlAPartirDeArchivosCsvDesdeConsola() throws IOException {
        ConversorCsv.main(this.getClass().getResource(ARCHIVO_ENTRADA_CSV).getPath());
        File archivoDestino = new File(ARCHIVO_SALIDA_SQL);
        assertThat(archivoDestino.isFile(), is(true));
    }

    @Test
    public void debeUsarElSegundoParametroComoElArchivoDeDestino() throws IOException {
        String rutaOrigen = this.getClass().getResource(ARCHIVO_ENTRADA_CSV).getPath();
        String rutaDestino = ARCHIVO_SALIDA_TXT;
        ConversorCsv.main(rutaOrigen, rutaDestino);
        File archivoDestino = new File(rutaDestino);
        assertThat(archivoDestino.isFile(), is(true));
    }

    @Test
    public void debeUsarElSegundoParametroComoDirectorioDeDestino() throws IOException {
        String rutaOrigen = this.getClass().getResource(ARCHIVO_ENTRADA_CSV).getPath();
        String rutaDestino = rutaOrigen.replace(ARCHIVO_ENTRADA_CSV, "");
        ConversorCsv.main(rutaOrigen, rutaDestino);
        File archivoDestino = new File(rutaDestino + File.separator + ARCHIVO_SALIDA_SQL);
        assertThat(archivoDestino.isFile(), is(true));
    }

    @Test
    public void debeImprimirMensajeDeAyudaCuandoMainEsLLamadoSinArgumentos() throws IOException {
        PrintStream old = System.out;
        ByteArrayOutputStream flujoSalida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(flujoSalida));

        ConversorCsv.main();

        System.out.flush();
        System.setOut(old);

        assertThat(flujoSalida.toString(), containsString("Uso correcto"));
    }

    @After
    public void tearDown() throws Exception {
        new File(ARCHIVO_SALIDA_SQL).delete();
        new File(ARCHIVO_SALIDA_TXT).delete();
    }
}